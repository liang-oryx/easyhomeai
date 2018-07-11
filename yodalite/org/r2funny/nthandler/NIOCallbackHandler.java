package org.r2funny.nthandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import java.util.concurrent.locks.ReentrantLock;

//import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * 使用异步方式处理回调请求
 * @author bird
 *
 */

public class NIOCallbackHandler {
	private static int							nMissionCount = 0;
	private static CloseableHttpAsyncClient 	httpClient = null;
	private static Lock 						lock = null;
	/**
	 * 实始化设备
	 */
	public static void init(){
		try {
			if (null == httpClient){
				lock = new ReentrantLock();
				
				httpClient = HttpAsyncClients.createDefault();
				// 启动服务
				httpClient.start();
			}

		}
		catch (Exception e){
			System.out.println("NIOCallbackHandler exception stop:" + e.toString());
		}
	}
	
	public static void stop(){
		try {
			if (null != httpClient){
				httpClient.close();
				httpClient = null;
				lock = null;
			}
		}
		catch (Exception e){
			System.out.println("NIOCallbackHandler exception stop:" + e.toString());
		}
	}
	
	/**
	 * 处理回调, 不管反馈
	 * @param strUrl
	 * @return
	 */
	public static boolean handleCallback(String strUrl){
		try {
			if (null != httpClient){
				// Execute request
	            final HttpGet request1 = new HttpGet(strUrl);
	            httpClient.execute(request1, null); 
	            //Future<HttpResponse> future = httpClient.execute(request1, null);
				
	            nMissionCount++;  
				return true;
			} 
		}
		catch (Exception e){
			System.out.println("NIOCallbackHandler exception stop:" + e.toString());
		}
		
		return false;
	}
	
	/**
	 * 处理回调, 不管反馈
	 * @param strUrl
	 * @return
	 */
	public static boolean handleCallback(String strUrl, boolean bHandReturn){
		if (null != httpClient){
			lock.lock(); 
			try {
					
		            nMissionCount++; 
		            
		            System.out.println("call url:" + strUrl);
		            
					if (bHandReturn){
						// Execute request
						final CountDownLatch latch1 = new CountDownLatch(1);
			            //final HttpGet request2 = new HttpGet("http://www.apache.org/");
			            final HttpGet request2 = new HttpGet(strUrl);
			            httpClient.execute(request2, new FutureCallback<HttpResponse>() {
		
			                @Override
			                public void completed(final HttpResponse response2) {
			                    latch1.countDown();
			                    System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine());
			                    nMissionCount--;
			                }
		
			                @Override
			                public void failed(final Exception ex) {
			                    latch1.countDown();
			                    System.out.println(request2.getRequestLine() + "->" + ex);
			                    nMissionCount--;
			                }
		
			                @Override
			                public void cancelled() {
			                    latch1.countDown();
			                    System.out.println(request2.getRequestLine() + " cancelled");
			                    nMissionCount--;
			                }
		
			            });
					}else {
						// Execute request
			            final HttpGet request1 = new HttpGet(strUrl);
			            httpClient.execute(request1, null);
			            //Future<HttpResponse> future = httpClient.execute(request1, null);
					}
					
		            
					return true;
			}
			catch (Exception e){
				System.out.println("NIOCallbackHandler exception stop:" + e.toString());
			}
			lock.unlock();
		}
		else {
			System.out.println("NIO httpClient is null!");
		}

		
		return false;
	}
	
	public static int getMissionCount(){
		return nMissionCount;
	}
	 
}
