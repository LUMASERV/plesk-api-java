package com.lumaserv.plesk.request;

import com.lumaserv.plesk.util.XMLElement;
import com.lumaserv.plesk.model.PleskWebspace;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class WebspaceRequest implements Request<PleskWebspace> {

    String name;
    Integer ownerId;
    String ownerLogin;
    UUID ownerGuid;
    String ownerExternalId;
    String hostType;
    String ipAddress;
    Integer status;
    String externalId;
    Integer planId;
    String planName;
    UUID planGuid;
    String planExternalId;
    Map<String, String> virtualHostProperties;
    String virtualHostIpAddress;
    String limitOveruse;
    Map<String, Long> limits;

    public Class<PleskWebspace> getModel() {
        return PleskWebspace.class;
    }

    public void toXml(XMLElement parent) {
        parent.add("gen_setup", setup -> {
            if(this.getName() != null)
                setup.add("name", this.getName());
            if(this.getOwnerId() != null)
                setup.add("owner-id", String.valueOf(this.getOwnerId()));
            if(this.getOwnerLogin() != null)
                setup.add("owner-login", this.getOwnerLogin());
            if(this.getOwnerGuid() != null)
                setup.add("owner-guid", this.getOwnerGuid().toString());
            if(this.getOwnerExternalId() != null)
                setup.add("owner-external-id", this.getOwnerExternalId());
            if(this.getHostType() != null)
                setup.add("htype", this.getHostType());
            if(this.getIpAddress() != null)
                setup.add("ip_address", this.getIpAddress());
            if(this.getStatus() != null)
                setup.add("status", String.valueOf(this.getStatus()));
            if(this.getExternalId() != null)
                setup.add("external-id", this.getExternalId());
        });
        if(this.getVirtualHostProperties() != null || this.getVirtualHostIpAddress() != null) {
            parent.add("hosting", hosting -> {
                if(this.getVirtualHostProperties() != null || this.getVirtualHostIpAddress() != null) {
                    hosting.add("vrt_hst", virtualHost -> {
                        if(this.getVirtualHostProperties() != null)
                            this.getVirtualHostProperties().forEach((k, v) -> virtualHost.add("property", prop -> prop.add("name", k).add("value", v)));
                        if(this.getVirtualHostIpAddress() != null)
                            virtualHost.add("ip_address", this.getVirtualHostIpAddress());
                    });
                }
            });
        }
        if(this.getLimitOveruse() != null || this.getLimits() != null) {
            parent.add("limits", limits -> {
                if(this.getLimits() != null)
                    this.getLimits().forEach((k, v) -> limits.add("limit", prop -> prop.add("name", k).add("value", String.valueOf(v))));
                if(this.getLimitOveruse() != null)
                    limits.add("overuse", this.getLimitOveruse());
            });
        }
        if(this.getPlanId() != null)
            parent.add("plan-id", String.valueOf(this.getPlanId()));
        if(this.getPlanName() != null)
            parent.add("plan-name", this.getPlanName());
        if(this.getPlanGuid() != null)
            parent.add("plan-guid", this.getPlanGuid().toString());
        if(this.getPlanExternalId() != null)
            parent.add("plan-external-id", this.getPlanExternalId());
    }

}
