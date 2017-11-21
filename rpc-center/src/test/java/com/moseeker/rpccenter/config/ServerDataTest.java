package com.moseeker.rpccenter.config;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 14/11/2017.
 */
public class ServerDataTest {
    @Test
    public void copy() throws Exception {

        ServerData serverData = new ServerData();
        serverData.setService("service");
        serverData.setWorker(8);
        serverData.setSelector(4);
        serverData.setPort(8080);
        serverData.setInterval(1000);
        serverData.setIp("192.168.31.66");
        serverData.setLanguage("zh");
        serverData.setMulti(1);
        serverData.setOwner("owner");
        serverData.setProtocol("thrift");
        serverData.setServer_type("type");
        serverData.setWeight(8);

        ServerData data = serverData.copy();

        assertEquals(data.getInterval(), serverData.getInterval());
        assertEquals(data.getIp(), serverData.getIp());
        assertEquals(data.getLanguage(), serverData.getLanguage());
        assertEquals(data.getMulti(), serverData.getMulti());
        assertEquals(data.getOwner(), serverData.getOwner());
        assertEquals(data.getPort(), serverData.getPort());
        assertEquals(data.getProtocol(), serverData.getProtocol());
        assertEquals(data.getSelector(), serverData.getSelector());
        assertEquals(data.getService(), serverData.getService());
        assertEquals(data.getServer_type(), serverData.getServer_type());
        assertEquals(data.getWeight(), serverData.getWeight());
        assertEquals(data.getWorker(), serverData.getWorker());

    }

}