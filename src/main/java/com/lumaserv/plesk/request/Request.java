package com.lumaserv.plesk.request;

import com.lumaserv.plesk.util.XMLElement;

public interface Request<T> {

    void toXml(XMLElement parent);
    Class<T> getModel();

}
