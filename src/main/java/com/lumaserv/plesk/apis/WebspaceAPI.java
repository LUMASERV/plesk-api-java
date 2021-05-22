package com.lumaserv.plesk.apis;

import com.lumaserv.plesk.PleskAPI;
import com.lumaserv.plesk.PleskAPIException;
import com.lumaserv.plesk.filter.WebspaceFilter;
import com.lumaserv.plesk.model.PleskWebspace;
import com.lumaserv.plesk.request.WebspaceRequest;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WebspaceAPI {

    final PleskAPI api;

    public List<PleskWebspace> get() throws PleskAPIException {
        return get(WebspaceFilter.NONE);
    }

    public List<PleskWebspace> get(WebspaceFilter filter) throws PleskAPIException {
        return api.get(PleskWebspace.class, PleskWebspace::new, filter, "gen_info", "hosting", "limits");
    }

    public PleskWebspace get(int id) throws PleskAPIException {
        return get(WebspaceFilter.ids(id)).stream().findFirst().orElse(null);
    }

    public int create(WebspaceRequest request) throws PleskAPIException {
        return api.add(request);
    }

    public void update(int id, WebspaceRequest request) throws PleskAPIException {
        api.set(WebspaceFilter.ids(id), request);
    }

    public void delete(int id) throws PleskAPIException {
        api.del(PleskWebspace.class, WebspaceFilter.ids(id));
    }

}
