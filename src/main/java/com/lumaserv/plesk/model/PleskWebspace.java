package com.lumaserv.plesk.model;

import com.lumaserv.plesk.PleskAPI;
import com.lumaserv.plesk.util.XMLElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PleskWebspace {

    int id;
    String name;
    String asciiName;
    int status;
    Long realSize;
    Integer ownerId;
    String dnsIpAddress;
    String hostType;
    UUID guid;
    UUID vendorGuid;
    String externalId;
    UUID sbSiteUuid;
    String description;
    String adminDescription;
    Date createdAt;
    String overuse;
    VirtualHost virtualHost;
    Map<String, Integer> limits;

    public PleskWebspace(XMLElement element) {
        this.id = element.findIntOf("id");
        XMLElement genInfo = element.findFirst("data").findFirst("gen_info");
        this.createdAt = genInfo.findDateOf("cr_date");
        this.name = genInfo.findTextOf("name");
        this.asciiName = genInfo.findTextOf("ascii-name");
        this.status = genInfo.findIntOf("status");
        this.realSize = genInfo.findLongOf("real_size");
        this.ownerId = genInfo.findIntOf("owner-id");
        this.dnsIpAddress = genInfo.findTextOf("dns_ip_address");
        this.hostType = genInfo.findTextOf("htype");
        this.guid = genInfo.findUUIDOf("guid");
        this.vendorGuid = genInfo.findUUIDOf("vendor-guid");
        this.externalId = genInfo.findTextOf("external-id");
        this.sbSiteUuid = genInfo.findUUIDOf("sb-site-uuid");
        this.description = genInfo.findFirst("description").text();
        this.adminDescription = genInfo.findFirst("admin-description").text();
        XMLElement limits = element.findFirst("data").findFirst("limits");
        if(limits != null) {
            this.overuse = limits.findTextOf("overuse");
            this.limits = limits.find("limit").stream().collect(Collectors.toMap(l -> l.findTextOf("name"), l -> l.findIntOf("value")));
        }
        XMLElement hosting = element.findFirst("data").findFirst("hosting");
        if(hosting != null) {
            XMLElement virtualHost = hosting.findFirst("vrt_hst");
            if(virtualHost != null)
                this.virtualHost = new VirtualHost().setIpAddress(virtualHost.findTextOf("ip_address")).setProperties(virtualHost.find("property").stream().collect(Collectors.toMap(p -> p.findTextOf("name"), p -> p.findTextOf("value"))));
        }
    }

    @Getter
    @Setter
    public static class VirtualHost {
        Map<String, String> properties;
        String ipAddress;
    }

}
