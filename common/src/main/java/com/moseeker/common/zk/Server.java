package com.moseeker.common.zk;

import java.io.Serializable;

public class Server implements Serializable {

	private String host;
	private int port;
	private Znode node;
}
