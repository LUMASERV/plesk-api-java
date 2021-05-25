package com.lumaserv.plesk;

import com.lumaserv.plesk.apis.CustomerAPI;
import com.lumaserv.plesk.apis.ServerAPI;
import com.lumaserv.plesk.apis.WebspaceAPI;
import com.lumaserv.plesk.filter.Filter;
import com.lumaserv.plesk.request.Request;
import com.lumaserv.plesk.util.XMLElement;
import org.javawebstack.httpclient.HTTPClient;
import org.javawebstack.httpclient.HTTPRequest;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PleskAPI {

    final HTTPClient client = new HTTPClient().header("content-type", "text/xml");
    final String host;
    final CustomerAPI customer = new CustomerAPI(this);
    final WebspaceAPI webspace = new WebspaceAPI(this);
    final ServerAPI server = new ServerAPI(this);

    public PleskAPI(String host, String username, String password) {
        this.host = host;
        client.header("HTTP_AUTH_LOGIN", username).header("HTTP_AUTH_PASSWD", password);
        client.timeout(15000);
    }

    public PleskAPI(String host, String key) {
        this.host = host;
        client.header("KEY", key);
    }

    public CustomerAPI customer() {
        return customer;
    }

    public WebspaceAPI webspace() {
        return webspace;
    }

    public ServerAPI server() {
        return server;
    }

    public <T> List<T> get(Class<T> model, Function<XMLElement, T> constructor, Filter<T> filter, String... dataset) throws PleskAPIException {
        return errorCheck(request(toKebabCase(model.getSimpleName().substring(5)), "get", e -> {
            e.add("filter", filter::apply);
            if(dataset.length > 0)
                e.add("dataset", e2 -> {
                    for(String f : dataset) e2.add(f);
                });
        })).stream().map(constructor).collect(Collectors.toList());
    }

    public <T> int add(Request<T> request) throws PleskAPIException {
        XMLElement res = errorCheck(request(toKebabCase(request.getModel().getSimpleName().substring(5)), "add", request::toXml)).stream().findFirst().orElse(null);
        if(res == null)
            return 0;
        return res.findIntOf("id");
    }

    public <T> void set(Filter<T> filter, Request<T> request) throws PleskAPIException {
        errorCheck(request(toKebabCase(request.getModel().getSimpleName().substring(5)), "set", set -> set.add("filter", filter::apply).add("values", request::toXml)));
    }

    public <T> void del(Class<T> model, Filter<T> filter) throws PleskAPIException {
        errorCheck(request(toKebabCase(model.getSimpleName().substring(5)), "del", e -> e.add("filter", filter::apply)));
    }

    private List<XMLElement> errorCheck(List<XMLElement> results) throws PleskAPIException {
        for(XMLElement r : results)
            errorCheck(r);
        return results;
    }

    private XMLElement errorCheck(XMLElement result) throws PleskAPIException {
        if(result == null)
            throw new PleskAPIException(0, "bad response");
        if("ok".equals(result.findTextOf("status")))
            return result;
        throw new PleskAPIException(result.findIntOf("errcode"), result.findTextOf("errtext"));
    }

    public List<XMLElement> request(String operator, String operation, Consumer<XMLElement> body) throws PleskAPIException {
        String rs = new XMLElement("packet").add(operator, e -> e.add(operation, body)).toString(true);
        HTTPRequest r = client.post("https://"+host+":8443/enterprise/control/agent.php").body(rs);
        XMLElement packet = XMLElement.fromString(r.string());
        XMLElement c = packet.children().get(0);
        if(c.tag().equalsIgnoreCase("system")) {
            if(c.findFirst("status").text().equalsIgnoreCase("error"))
                throw new PleskAPIException(Integer.parseInt(c.findFirst("errcode").text()), c.findFirst("errtext").text());
            return null;
        }
        return errorCheck(c.children().get(0).children());
    }

    public static String toKebabCase(String source) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(source.charAt(0)));
        for (int i = 1; i < source.length(); i++) {
            if (Character.isUpperCase(source.charAt(i))) {
                if (!Character.isUpperCase(source.charAt(i - 1)))
                    sb.append("-");
                sb.append(Character.toLowerCase(source.charAt(i)));
            } else {
                sb.append(source.charAt(i));
            }
        }
        return sb.toString();
    }

}
