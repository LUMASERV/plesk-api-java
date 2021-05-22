package com.lumaserv.plesk.model;

import com.lumaserv.plesk.PleskAPI;
import com.lumaserv.plesk.util.XMLElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class PleskCustomer {

    int id;
    String login;
    String name;
    String company;
    int status;
    String phone;
    String fax;
    String email;
    String address;
    String city;
    String state;
    String postalCode;
    String country;
    String locale;
    UUID guid;
    String ownerLogin;
    UUID vendorGuid;
    String externalId;
    String description;
    String password;
    String passwordType;
    Date createdAt;

    public PleskCustomer(XMLElement element) {
        this.id = element.findIntOf("id");
        XMLElement genInfo = element.findFirst("data").findFirst("gen_info");
        this.createdAt = genInfo.findDateOf("cr_date");
        this.name = genInfo.findTextOf("pname");
        this.company = genInfo.findTextOf("cname");
        this.login = genInfo.findTextOf("login");
        this.status = genInfo.findIntOf("status");
        this.phone = genInfo.findTextOf("phone");
        this.fax = genInfo.findTextOf("fax");
        this.email = genInfo.findTextOf("email");
        this.address = genInfo.findTextOf("address");
        this.city = genInfo.findTextOf("city");
        this.state = genInfo.findTextOf("state");
        this.postalCode = genInfo.findTextOf("pcode");
        this.country = genInfo.findTextOf("country");
        this.locale = genInfo.findTextOf("locale");
        this.guid = genInfo.findUUIDOf("guid");
        this.ownerLogin = genInfo.findTextOf("owner-login");
        this.vendorGuid = genInfo.findUUIDOf("vendor-guid");
        this.externalId = genInfo.findTextOf("locale");
        this.description = genInfo.findTextOf("description");
        this.password = genInfo.findTextOf("password");
        this.passwordType = genInfo.findTextOf("password_type");
    }

}
