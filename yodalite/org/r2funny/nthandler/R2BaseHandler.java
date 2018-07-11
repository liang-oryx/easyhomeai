package org.r2funny.nthandler;
 

import org.r2funny.litesvr.NettyChannelMap; 
import org.r2funny.loger.LTLoger;
import org.r2funny.yodalite.GlobalData;

import io.netty.buffer.ByteBuf; 
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
import io.netty.channel.socket.SocketChannel;

/**
 * IOT 服务器硬件连接服务类
 * @author bird
 *
 */
public class R2BaseHandler  extends ChannelInboundHandlerAdapter {
	
	private String   mCurMac = "";		// 当前连接对应的Mac地址
	private String	 mCurToken = "";	// 当前连接对应的Token
	private String   mCurCmd = "";		// 当前指令 
	
	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {    
		String  strData = msg.toString();  
         // 处理上传的指令
        handleUpData(strData, ctx );  
        
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + strData); 
        
        ByteBuf	mSendBuf = ctx.alloc().buffer(28);  
        
        if (0 < this.mCurCmd.length()){
        	mSendBuf.writeBytes(this.mCurCmd.getBytes()); 
            ctx.writeAndFlush(mSendBuf); 
            System.out.println("Rep to Client:" + this.mCurCmd);
        	this.mCurCmd = "";
        }
        else {  
	        mSendBuf.writeBytes("OK\r\n".getBytes());  
	        ctx.writeAndFlush(mSendBuf);  
        }
        
        // 记录日志
        LTLoger.log(strData);
        
        strData = null;
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        
        if ((null != this.mCurMac)
        		&& (0 < this.mCurMac.length()) ){
        	NettyChannelMap.remove(this.mCurMac);
        	 
        }
        else {
        	NettyChannelMap.remove((SocketChannel)ctx.channel());
        }
        
        System.out.println("channel in exceptionCaught."); 
        
        ctx.close();  
    }  
    
    @Override  
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
        //System.err.println("in channelInactive."); 
		System.out.println("channel in channelInactive."); 
        if ((null != this.mCurMac)
        		&& (0 < this.mCurMac.length()) ){
        	NettyChannelMap.remove(this.mCurMac);
        	 
        }
        else {
            NettyChannelMap.remove((SocketChannel)ctx.channel());  
        }
    }
    
    /**
     * 处理上传的数据
     * @param strUpData
     */
    private void handleUpData(String strUpData, ChannelHandlerContext ctx){
    	// 访问参数列表
    	if (0 <= strUpData.indexOf("r.x?")){
    		String strMac = getParameter(strUpData, "m=");
    		String strDatas = getParameter(strUpData, "d=");
    		
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// 注册
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			}
    			
    			// 处理数据
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("q.x?")){ 
    		// 心跳包2
    		String strDatas = getParameter(strUpData, "t="); 
    				 
    		if (null != strDatas){ 
    			System.out.println("handle q.x cmd!" + strDatas); 
    			
    			if (12 <= strDatas.length()){
    				// 注册
    				String  strMac = strDatas.substring(0, 12);
    				
    				if (0 == this.mCurMac.length()){
        				// 注册
            			this.mCurMac = strMac;  
            			regChannel(strMac, (SocketChannel) ctx.channel());
        			} 
        			
        			// 处理数据
        			handleUpData(strMac, strDatas.substring(12));
    			}
    		}
    		else {
    			System.out.println("handle q.x cmd error!"); 
    		}
    		this.mCurCmd = "nil";
    	}
    	else if (0 <= strUpData.indexOf("dataupload?")){
    		// 数据上报处理
    		String strMac = getParameter(strUpData, "mac="); 
    		
    		int nStart = strUpData.indexOf("?mac=");
			nStart = (0 > nStart) ? 0 : (nStart + 18); 
			String  strDatas =  strUpData.substring(nStart + 1);
			
    		System.out.println("handle dev_opr_nio cmd!" + strMac); 
    				
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// 注册
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			} 
    			// 处理数据
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("dev_opr_nio")){
    		// 心跳包1
    		String strMac = getParameter(strUpData, "mac="); 
    		
    		int nStart = strUpData.indexOf("?mac=");
			nStart = (0 > nStart) ? 0 : (nStart + 18); 
			String  strDatas =  strUpData.substring(nStart + 1);
			
    		System.out.println("handle dev_opr_nio cmd!" + strMac); 
    				
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// 注册
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			} 
    			// 处理数据
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("devreg.jsp?")){
    		// 说明是设备注册指令
    		String strMac = getParameter(strUpData, "m=");
    		String strDatas = getParameter(strUpData, "d=");
    		
    		if (null != strMac){ 
    			// 注册
    			this.mCurMac = strMac;  
    			regChannel(strMac, (SocketChannel) ctx.channel());
    			
    			// 处理数据
    			handleUpData(strMac, strDatas);
    		}
    	} 
    	else if (0 <= strUpData.indexOf("qgroup?")){
    		callQueryGroupDev(strUpData, ctx); 
    	}
    	else {
    		System.out.println("undef cmd!" + strUpData); 
    	}
    } 
    
    /**
     * 调用查询组中设备的功能
     * @param strCmd
     * @param ctx
     */
    private void callQueryGroupDev(String strCmd, ChannelHandlerContext ctx){
    	String  strMac 		= getParameter(strCmd, "mac=");
		String  strToken 	= getParameter(strCmd, "tk="); 
		
		//StringBuilder strb = new StringBuilder();  
		// 注册设备
		if (12 <= strMac.length()) {
			// 返回0个
			// to do.返回默认数据, 一个蓝牙， 一个灯... 
			this.mCurCmd = "&rc=00";  
		}
    }
    /**
     * 处理上传的数据
     * @param strMac
     * @param strData
     */
    private void handleUpData(String strMac, String strDatas){ 
		// 防止非法字符
    	String hexStr = string2HexString(strDatas);
    	// 注：现在是使用url方法把数据传递给yodalite, 高负载系统可以用数据或socket直接通讯的方式    	
    	// 实现自己的高负载实时物联网系统，可以自己重写该部分代码
    	String  strUrl = "http://127.0.0.1:8080/yodalite/svrent/netty_up.jsp?mac=" + strMac
    			+ "&tk=" + GlobalData.WEB_ACCESS_TOKEN 
				+ "&ud=" + hexStr;
    	
    	NIOCallbackHandler.handleCallback(strUrl, false); 
    }

    /** 
     * @Title:string2HexString 
     * @Description:字符串转16进制字符串 
     * @param strPart 
     *            字符串 
     * @return 16进制字符串 
     * @throws 
     */  
    public String string2HexString(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString();  
    }
    
	public String getCurMac() {
		return mCurMac;
	}

	public void setCurMac(String strCurMac) {
		this.mCurMac = strCurMac;
	}

	public String getCurToken() {
		return mCurToken;
	}

	public void setmCurToken(String strToken) {
		this.mCurToken = strToken;
	}   
    
	public String getCurCmd() {
		return mCurCmd;
	}

	public void setCurCmd(String strCurCmd) {
		this.mCurCmd = strCurCmd;
	} 
	
	/**
     * 参数解析
     * @param strRequest
     * @param strParam
     * @return
     */
    private String getParameter(String  strRequest, String strParam){
    	
    	if ((null != strRequest)
    			&& (null != strParam)){
    		
    		int nStart = strRequest.indexOf(strParam);
    		
    		if (0 <= nStart){
    			int  nEnd = strRequest.indexOf("&", nStart + 2); 
        		if (0 > nEnd){
        			nEnd = strRequest.indexOf("\r\n", nStart + 2); 
        			if (0 > nEnd)
        				nEnd = strRequest.length();
        		}
        		
        		return strRequest.substring(nStart + strParam.length(), nEnd);
    		}
    	}
    	
    	return null;
    }
    
    /**
     * 注册通讯对象
     * @param strMac
     * @param socketChannel
     */
    private void regChannel(String  strMac, SocketChannel socketChannel){
    	NettyChannelMap.add(this.mCurMac, socketChannel);  
    }
}
