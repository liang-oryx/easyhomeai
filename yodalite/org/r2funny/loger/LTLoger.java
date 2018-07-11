package org.r2funny.loger;
 
import java.util.logging.FileHandler; 
import java.util.logging.Level; 
import java.util.logging.Logger;

/**
 * 日志记录类
 * @author liang
 *
 */

public class LTLoger {
	private static Logger	sloger = null;
	
	public static void init(){ 
		// 初始化
		try {
			if (null == sloger){
				 sloger = Logger.getLogger("niosvr"); 
				 //sloger.setLevel(Level.WARNING);  
		       
		         FileHandler fileHandler = new FileHandler("./nio_log_" + System.currentTimeMillis() / 1000 / 60 + ".log");
		         //FileHandler fileHandler = new FileHandler(Thread.class.getResource("/").getPath()+"java%u.log");
		         fileHandler.setLevel(Level.INFO);  
		         fileHandler.setFormatter(new LTLogHander()); 
		         sloger.addHandler(fileHandler);
			}
		}catch (Exception e){
			System.out.println("LTLoger init exception:" + e.toString());
		}
	}
	
	public static void log(String strMsg){
		try {
			sloger.log(Level.INFO, strMsg); 
		}
		catch (Exception e){
			System.out.println("LTLoger:" + e.toString());
		}
	}
	
	public static void log(Level level, String strMsg){
		try {
			sloger.log(level, strMsg);
		}
		catch (Exception e){
			System.out.println("LTLoger:" + e.toString());
		}
	} 
}
