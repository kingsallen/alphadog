package com.moseeker.rpccenter.common;

import com.google.common.base.Objects;

/**
 * 机器节点
 * <p>
 * @author : Mid Hua
 * @date : 16/3/28.
 */
public class ServerNode {

    /** 服务ip地址 */
    private String ip;

    /** 服务端口 */
    private int port;

    /**
     * @param ip
     * @param port
     */
    public ServerNode(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    /**
     * 生成服务地址<br>
     * Server端：ip:port <br>
     * Client端：ip:port:i_节点序列号 <br>
     * <p>
     *
     * @return ip:port
     */
    public String genAddress() {
        return ip + ":" + port;
    }

    /**
     * getter method
     *
     * @see ServerNode#ip
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * setter method
     *
     * @see ServerNode#ip
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getter method
     *
     * @see ServerNode#port
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * setter method
     *
     * @see ServerNode#port
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ServerNode) {
            ServerNode that = (ServerNode) other;
            return Objects.equal(this.ip, that.ip) && this.port == that.port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ip, port);
    }

    @Override
    public String toString() {
        return "ServerNode{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

}
