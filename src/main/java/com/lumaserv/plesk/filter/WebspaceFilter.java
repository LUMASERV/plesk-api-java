package com.lumaserv.plesk.filter;

import com.lumaserv.plesk.util.XMLElement;
import com.lumaserv.plesk.model.PleskWebspace;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class WebspaceFilter implements Filter<PleskWebspace> {

    public static final WebspaceFilter NONE = new WebspaceFilter();

    int[] ids;

    public static WebspaceFilter ids(int... ids) {
        return new WebspaceFilter().setIds(ids);
    }

    public void apply(XMLElement filter) {
        if(ids != null)
            IntStream.of(ids).forEach(id -> filter.add("id", String.valueOf(id)));
    }

}
