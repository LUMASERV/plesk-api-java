package com.lumaserv.plesk.filter;

import com.lumaserv.plesk.util.XMLElement;
import com.lumaserv.plesk.model.PleskCustomer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class CustomerFilter implements Filter<PleskCustomer> {

    public static final CustomerFilter NONE = new CustomerFilter();

    int[] ids;

    public static CustomerFilter ids(int... ids) {
        return new CustomerFilter().setIds(ids);
    }

    public void apply(XMLElement filter) {
        if(ids != null)
            IntStream.of(ids).forEach(id -> filter.add("id", String.valueOf(id)));
    }

}
