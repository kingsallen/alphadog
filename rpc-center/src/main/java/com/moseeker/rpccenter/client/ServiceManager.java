package com.moseeker.rpccenter.client;

import com.moseeker.rpccenter.config.ThriftServerConfig;
import com.moseeker.rpccenter.registry.IRegistry;
import com.moseeker.thrift.gen.profile.service.AttachmentServices;

public enum ServiceManager {
	
	SERVICEMANAGER;
	
	private ThriftServerConfig config = new ThriftServerConfig();
	private IfaceFactory<AttachmentServices.Iface> ifaceFactory = null;
	
	public AttachmentServices.Iface getAttchmenetService() {
		if(ifaceFactory == null) {
			ifaceFactory = new IfaceFactory<>(config);
		}
		return ifaceFactory.createIface(AttachmentServices.Iface.class);
	}
	
    /**
     * 添加关闭钩子 -  无心跳
     * <p>
     *
     * @param registry
     */
    protected void addShutdownHook(final IRegistry registry) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifaceFactory != null) {
                	ifaceFactory.clear();
                }
            }
        }));
    }
}
