package com.lumaserv.plesk.apis;

import com.lumaserv.plesk.PleskAPI;
import com.lumaserv.plesk.PleskAPIException;
import com.lumaserv.plesk.request.CreateSessionRequest;
import com.lumaserv.plesk.util.XMLElement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerAPI {

    final PleskAPI api;

    public String createSession(CreateSessionRequest request) throws PleskAPIException {
        XMLElement e = api.request("server", "create_session", request::toXml).stream().findFirst().get();
        return e.findTextOf("id");
    }

}
