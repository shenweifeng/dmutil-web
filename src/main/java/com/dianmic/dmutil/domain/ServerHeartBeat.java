package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2019年3月7日
 * 
 * @author swf
 *
 * @Description 心跳表
 *
 */
public class ServerHeartBeat extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2326495417518506320L;

    private Integer           serverId;
    private String            serverName;
    private String            serverIp;

    private String            clientIp;
    private String            clientTime;
    private Date              serverTime;

    public ServerHeartBeat() {
    }

    public ServerHeartBeat(Integer serverId, String clientIp, String clientTime, Date serverTime) {
        super();
        this.serverId = serverId;
        this.clientIp = clientIp;
        this.clientTime = clientTime;
        this.serverTime = serverTime;
    }

    public boolean check() {
        return true;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientTime() {
        return clientTime;
    }

    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

}
