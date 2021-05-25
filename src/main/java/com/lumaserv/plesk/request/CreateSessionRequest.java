package com.lumaserv.plesk.request;

import com.lumaserv.plesk.util.XMLElement;
import lombok.Setter;

@Setter
public class CreateSessionRequest implements Request<Object> {

    String login;
    String userIp;
    String sourceServer;

    public void toXml(XMLElement parent) {
        if(login != null)
            parent.add("login", login);
        if(userIp != null || sourceServer != null) {
            parent.add("data", e -> {
                if(userIp != null)
                    e.add("user_ip", userIp);
                if(sourceServer != null)
                    e.add("source_server", sourceServer);
            });
        }
    }

    public Class<Object> getModel() {
        return null;
    }
}
