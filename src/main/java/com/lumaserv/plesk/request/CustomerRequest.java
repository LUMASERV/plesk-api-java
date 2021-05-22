package com.lumaserv.plesk.request;

import com.lumaserv.plesk.util.XMLElement;
import com.lumaserv.plesk.model.PleskCustomer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRequest implements Request<PleskCustomer> {

    String name;
    String company;
    String login;
    String email;
    String password;
    Integer status;
    String phone;
    String fax;
    String address;
    String city;
    String state;
    String postalCode;
    String country;
    String locale;
    Integer ownerId;
    String ownerLogin;
    String externalId;

    public Class<PleskCustomer> getModel() {
        return PleskCustomer.class;
    }

    public void toXml(XMLElement parent) {
        parent.add("gen_info", info -> {
            if(this.getName() != null)
                info.add("pname", this.getName());
            if(this.getCompany() != null)
                info.add("cname", this.getCompany());
            if(this.getLogin() != null)
                info.add("login", this.getLogin());
            if(this.getEmail() != null)
                info.add("email", this.getEmail());
            if(this.getPassword() != null)
                info.add("passwd", this.getPassword());
            if(this.getStatus() != null)
                info.add("status", String.valueOf(this.getStatus()));
            if(this.getPhone() != null)
                info.add("phone", this.getPhone());
            if(this.getFax() != null)
                info.add("fax", this.getFax());
            if(this.getAddress() != null)
                info.add("address", this.getAddress());
            if(this.getCity() != null)
                info.add("city", this.getCity());
            if(this.getState() != null)
                info.add("state", this.getState());
            if(this.getPostalCode() != null)
                info.add("pcode", this.getPostalCode());
            if(this.getCountry() != null)
                info.add("country", this.getCountry());
            if(this.getLocale() != null)
                info.add("locale", this.getLocale());
            if(this.getOwnerId() != null)
                info.add("owner-id", String.valueOf(this.getOwnerId()));
            if(this.getOwnerLogin() != null)
                info.add("owner-login", this.getOwnerLogin());
            if(this.getExternalId() != null)
                info.add("external-id", this.getExternalId());
        });
    }

}
