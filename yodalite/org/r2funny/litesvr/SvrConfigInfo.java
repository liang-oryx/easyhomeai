package org.r2funny.litesvr;

/**
 * 服务器配置信息类， 每一个新的服务器都需要创建相关的信息
 * @author bird
 *
 */

public class SvrConfigInfo { 
	   public static final String 	SID = "R2NIO01";
	   public static final int    	PORT_NIO = 8989;
	   public static final int    	PORT_IO  = 8080; 
	    
	   public static final boolean  OUT_PUT_TIPS_DATA = true; 		// 是否输出提示信息
}
