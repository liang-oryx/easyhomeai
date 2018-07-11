package org.r2funny.yodalite;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap; 
 
public class GlobalData {
	public static long		TOKEN_MAKE_TIME = 0;				// 最新一次token生成的时间
	public static String	SVR_CUR_TOKEN	= "";				// 当前的token
	public static String 	WEB_ACCESS_TOKEN = "r2funny1234";	// 默认的网络请求token
	
	public static ConcurrentHashMap<String, R2Dev> 				mDevMap 		= new ConcurrentHashMap<String, R2Dev>();
	public static ConcurrentHashMap<String, List<UploadData>> 	mDevUploadMap 	= new ConcurrentHashMap<String, List<UploadData> >();
	 
}
