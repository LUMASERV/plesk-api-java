package com.lumaserv.plesk.apis;

import com.lumaserv.plesk.PleskAPI;
import com.lumaserv.plesk.PleskAPIException;
import com.lumaserv.plesk.filter.CustomerFilter;
import com.lumaserv.plesk.model.PleskCustomer;
import com.lumaserv.plesk.request.CustomerRequest;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerAPI {

    final PleskAPI api;

    public List<PleskCustomer> get() throws PleskAPIException {
        return get(CustomerFilter.NONE);
    }

    public List<PleskCustomer> get(CustomerFilter filter) throws PleskAPIException {
        return api.get(PleskCustomer.class, PleskCustomer::new, filter, "gen_info");
    }

    public PleskCustomer get(int id) throws PleskAPIException {
        return get(CustomerFilter.ids(id)).stream().findFirst().orElse(null);
    }

    public int create(CustomerRequest request) throws PleskAPIException {
        return api.add(request);
    }

    public void update(int id, CustomerRequest request) throws PleskAPIException {
        api.set(CustomerFilter.ids(id), request);
    }

    public void delete(int id) throws PleskAPIException {
        api.del(PleskCustomer.class, CustomerFilter.ids(id));
    }

}
