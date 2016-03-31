package com.moseeker.servicemanager.web.controller;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Iface;

/**
 * Created by zzh on 16/3/30.
 */
@Controller
public class DemoController<T> {

    public static final String DEMO_SERVICE_NAME = "com.moseeker.user.demo$EchoService";

    String iface = Iface.class.getName();
    ClientConfig<Iface> clientConfig = new ClientConfig<Iface>();

    private Iface getProxy(){
        clientConfig.setService(DEMO_SERVICE_NAME);
        clientConfig.setIface(iface);
        try{
            return clientConfig.createProxy(null);
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value = "/companyfollowers", method = RequestMethod.GET)
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response) {

    }

}
