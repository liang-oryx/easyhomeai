package org.r2funny.litesvr;

import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioServerSocketChannel;   
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;  
import io.netty.handler.logging.LogLevel;  
import io.netty.handler.logging.LoggingHandler;  
import io.netty.handler.timeout.IdleStateHandler;    

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;  
import java.util.concurrent.CountDownLatch; 
import java.util.concurrent.TimeUnit;  
  




import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.r2funny.nthandler.NIOCallbackHandler;
//import org.r2funny.nthandler.UTF8StringDecoder;
import org.r2funny.nthandler.R2BaseHandler;
import org.r2funny.loger.LTLoger; 
 
 

/**
 * 101应用的数据服务器
 * @author bird
 *
 */

 
public class R2IOTServer {
	private final 	AcceptorIdleStateTrigger 	idleStateTrigger = new AcceptorIdleStateTrigger();    
    private int 	port;   
    private String	mCurToken = "";
    private static 	NIOCallbackHandler		mNetCallbackHandler = new NIOCallbackHandler();		// 回调类
    
    public R2IOTServer(int port) {  
        this.port = port;  
    }  
  
    public void start() throws InterruptedException  {  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup)  
                    .channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))  
                    .localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<SocketChannel>() {  
                        protected void initChannel(SocketChannel ch) throws Exception {   
                        	ch.pipeline().addLast(new IdleStateHandler(60, 60, 0, TimeUnit.SECONDS));  
                            ch.pipeline().addLast(idleStateTrigger);  
                            ch.pipeline().addLast("decoder", new StringDecoder());  
                            //ch.pipeline().addLast("decoder", new UTF8StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());  
                            ch.pipeline().addLast(new R2BaseHandler());  
                        };  
  
                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);  
            // 绑定端口，开始接收进来的连接  
            ChannelFuture future = sbs.bind(port).sync();  
  
            System.out.println("Server start listen at " + port);  
            future.channel().closeFuture().sync();  
        } catch (Exception e) { 
        	System.out.println("Main:" + e.toString());
        }
        finally {  
        	//bossGroup.shutdownGracefully().sync();//关闭EventLoopGroup，释放掉所有资源包括创建的线程  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        } 
    }  
    
    class UdpSvrThread extends Thread {
		
	    private static final int PORT = 8889;
	    private DatagramSocket 	dataSocket;
	    private DatagramPacket 	dataPacket;		// udp通讯包
	    private byte 			receiveByte[];
	    private String 			receiveStr;
	    
	    public  boolean			bRuning = false;
		@Override 
		public void run() {
			try {
				
				dataSocket 	= new DatagramSocket(PORT);
	            receiveByte = new byte[512];	// 指令最多512 
	            dataPacket 	= new DatagramPacket(receiveByte, receiveByte.length); 
	            bRuning		= true;
	            
				while (true)
				{ 
					// 接收数据
					dataSocket.receive(dataPacket);
	                int lRecvLen = dataPacket.getLength();
	                
	                if (lRecvLen > 0) {
	                	
	                	// 转发指令
	                	receiveStr = new String(receiveByte, 0, dataPacket.getLength());
	                	
	                	// to do. 
	                	// check token 
	                	
	                	if (14 < receiveStr.length()) {
		   				 	String  strMac = receiveStr.substring(0, 12);
		   				 	String  strCmd = receiveStr.substring(14);
		   				 	
		   				 	if ("R2".equals(receiveStr.substring(12, 14))) {
		   				 		pushMsg(strMac, strCmd);
		   				 	} 
		                		                		
	                	} 
	                }
				}
			} catch (Exception e){
				System.out.println("Exception in UdpSvrThread:" + e.toString());
				LTLoger.log("Exception in UdpSvrThread:" + e.toString());
				
				dataSocket.close();
				bRuning = false;
				
			} 
		}
	}

    /**
     * 启动Udp服务器线程 
     */
    public void startUdpSvrThread(){
    	try {
    		System.out.println("Start udp thread svrid:" + SvrConfigInfo.SID);
    		
    		UdpSvrThread udpSvrThread = new UdpSvrThread();                
    		udpSvrThread.start();
    	}catch (Exception e){
    		LTLoger.log("startUdpSvrThread exception:" + e.toString());
    	} 
    }
    
	/**
	 * 发送推送消息
	 * @param strTargetMac
	 * @param strMsg
	 * @return
	 */
	public boolean pushMsg(String  strTargetMac, String strMsg){
		//SocketChannel	socket;
		try {
			Channel  socketChannel = NettyChannelMap.get(strTargetMac);
			
			if (null != socketChannel){
				if (SvrConfigInfo.OUT_PUT_TIPS_DATA){
					System.out.println("post data to:" + strTargetMac + " cmd:" + strMsg);
				}
				
				LTLoger.log("post data to:" + strTargetMac + " cmd:" + strMsg);
				
				socketChannel.writeAndFlush(strMsg);
				return true;
			}
			else {
				return false;
			} 
		}
		catch (Exception e){
			System.out.println("pushMsg error:" + e.toString());
			LTLoger.log("pushMsg error:" + e.toString());
		}
		return false;
	}
	
	// 通过nio方式访问token
	public void getSvrToken(){
		// to do. 可以改成自己的url 
		String  					strLocalHostUrl = "http://127.0.0.1:8080/yodalite/get_token.jsp";
		CloseableHttpAsyncClient 	httpClient = HttpAsyncClients.createDefault();
		
		System.out.println("call url->" + strLocalHostUrl); 
		
		try { 
			try {
				// 启动服务
				httpClient.start();
				
				// Execute request
				final CountDownLatch latch1 = new CountDownLatch(1); 
	            final HttpGet request2 = new HttpGet(strLocalHostUrl);
	            httpClient.execute(request2, new FutureCallback<HttpResponse>() {
	
	                @Override
	                public void completed(final HttpResponse response2) {
	                    latch1.countDown();
	                    
	                    String  strRepData = response2.getStatusLine().toString();
	                    
	                    int nIndex = strRepData.indexOf("R2TK_");
	                    
	                    if (0 <= nIndex){
	                    	R2IOTServer.this.mCurToken = strRepData.substring(nIndex);
	                    	System.out.println("获取token:" + R2IOTServer.this.mCurToken); 
	                    }
	                    else {
	                    	System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine()); 
	                    }
	                    //System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine()); 
	                }
	
	                @Override
	                public void failed(final Exception ex) {
	                    latch1.countDown();
	                    System.out.println(request2.getRequestLine() + "->" + ex); 
	                }
	
	                @Override
	                public void cancelled() {
	                    latch1.countDown();
	                    System.out.println(request2.getRequestLine() + " cancelled"); 
	                } 
	            });
	            
	            latch1.await();
			}
            finally {
    			httpClient.close();
    	    }
			 
		}
		catch (Exception e){
			// 
		} 
	}
	
  
    @SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {  
        try {  
	        int port;  
	        if (args.length > 0) {  
	            port = Integer.parseInt(args[0]);  
	        } else {  
	        	// 新的服务器... 
	            port = 8989;  
	        }  
	        
	        // 初始化日志
	        LTLoger.init();
	        
	        R2IOTServer  iotSvr = new R2IOTServer(port); 
	        
	     // 访问token
	        iotSvr.getSvrToken();

	        // 启动Udp服务器
	        iotSvr.startUdpSvrThread(); 
	        
	        // 访问token
	        iotSvr.mNetCallbackHandler.init();
	        
	        // 启动服务
	        iotSvr.start();   
	        
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
    }  
}
