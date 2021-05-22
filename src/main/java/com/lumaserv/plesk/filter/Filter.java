package com.lumaserv.plesk.filter;

import com.lumaserv.plesk.util.XMLElement;

public interface Filter<T> {

    void apply(XMLElement filter);

}
