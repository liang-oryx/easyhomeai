package org.r2funny.nthandler;
 

import org.r2funny.litesvr.NettyChannelMap; 
import org.r2funny.loger.LTLoger;
import org.r2funny.yodalite.GlobalData;

import io.netty.buffer.ByteBuf; 
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
import io.netty.channel.socket.SocketChannel;

/**
 * IOT ������Ӳ�����ӷ�����
 * @author bird
 *
 */
public class R2BaseHandler  extends ChannelInboundHandlerAdapter {
	
	private String   mCurMac = "";		// ��ǰ���Ӷ�Ӧ��Mac��ַ
	private String	 mCurToken = "";	// ��ǰ���Ӷ�Ӧ��Token
	private String   mCurCmd = "";		// ��ǰָ�� 
	
	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {    
		String  strData = msg.toString();  
         // �����ϴ���ָ��
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
        
        // ��¼��־
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
     * �����ϴ�������
     * @param strUpData
     */
    private void handleUpData(String strUpData, ChannelHandlerContext ctx){
    	// ���ʲ����б�
    	if (0 <= strUpData.indexOf("r.x?")){
    		String strMac = getParameter(strUpData, "m=");
    		String strDatas = getParameter(strUpData, "d=");
    		
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// ע��
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			}
    			
    			// ��������
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("q.x?")){ 
    		// ������2
    		String strDatas = getParameter(strUpData, "t="); 
    				 
    		if (null != strDatas){ 
    			System.out.println("handle q.x cmd!" + strDatas); 
    			
    			if (12 <= strDatas.length()){
    				// ע��
    				String  strMac = strDatas.substring(0, 12);
    				
    				if (0 == this.mCurMac.length()){
        				// ע��
            			this.mCurMac = strMac;  
            			regChannel(strMac, (SocketChannel) ctx.channel());
        			} 
        			
        			// ��������
        			handleUpData(strMac, strDatas.substring(12));
    			}
    		}
    		else {
    			System.out.println("handle q.x cmd error!"); 
    		}
    		this.mCurCmd = "nil";
    	}
    	else if (0 <= strUpData.indexOf("dataupload?")){
    		// �����ϱ�����
    		String strMac = getParameter(strUpData, "mac="); 
    		
    		int nStart = strUpData.indexOf("?mac=");
			nStart = (0 > nStart) ? 0 : (nStart + 18); 
			String  strDatas =  strUpData.substring(nStart + 1);
			
    		System.out.println("handle dev_opr_nio cmd!" + strMac); 
    				
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// ע��
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			} 
    			// ��������
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("dev_opr_nio")){
    		// ������1
    		String strMac = getParameter(strUpData, "mac="); 
    		
    		int nStart = strUpData.indexOf("?mac=");
			nStart = (0 > nStart) ? 0 : (nStart + 18); 
			String  strDatas =  strUpData.substring(nStart + 1);
			
    		System.out.println("handle dev_opr_nio cmd!" + strMac); 
    				
    		if (null != strMac){ 
    			if (0 == this.mCurMac.length()){
    				// ע��
        			this.mCurMac = strMac;  
        			regChannel(strMac, (SocketChannel) ctx.channel());
    			} 
    			// ��������
    			handleUpData(strMac, strDatas);
    		}
    	}
    	else if (0 <= strUpData.indexOf("devreg.jsp?")){
    		// ˵�����豸ע��ָ��
    		String strMac = getParameter(strUpData, "m=");
    		String strDatas = getParameter(strUpData, "d=");
    		
    		if (null != strMac){ 
    			// ע��
    			this.mCurMac = strMac;  
    			regChannel(strMac, (SocketChannel) ctx.channel());
    			
    			// ��������
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
     * ���ò�ѯ�����豸�Ĺ���
     * @param strCmd
     * @param ctx
     */
    private void callQueryGroupDev(String strCmd, ChannelHandlerContext ctx){
    	String  strMac 		= getParameter(strCmd, "mac=");
		String  strToken 	= getParameter(strCmd, "tk="); 
		
		//StringBuilder strb = new StringBuilder();  
		// ע���豸
		if (12 <= strMac.length()) {
			// ����0��
			// to do.����Ĭ������, һ�������� һ����... 
			this.mCurCmd = "&rc=00";  
		}
    }
    /**
     * �����ϴ�������
     * @param strMac
     * @param strData
     */
    private void handleUpData(String strMac, String strDatas){ 
		// ��ֹ�Ƿ��ַ�
    	String hexStr = string2HexString(strDatas);
    	// ע��������ʹ��url���������ݴ��ݸ�yodalite, �߸���ϵͳ���������ݻ�socketֱ��ͨѶ�ķ�ʽ    	
    	// ʵ���Լ��ĸ߸���ʵʱ������ϵͳ�������Լ���д�ò��ִ���
    	String  strUrl = "http://127.0.0.1:8080/yodalite/svrent/netty_up.jsp?mac=" + strMac
    			+ "&tk=" + GlobalData.WEB_ACCESS_TOKEN 
				+ "&ud=" + hexStr;
    	
    	NIOCallbackHandler.handleCallback(strUrl, false); 
    }

    /** 
     * @Title:string2HexString 
     * @Description:�ַ���ת16�����ַ��� 
     * @param strPart 
     *            �ַ��� 
     * @return 16�����ַ��� 
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
     * ��������
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
     * ע��ͨѶ����
     * @param strMac
     * @param socketChannel
     */
    private void regChannel(String  strMac, SocketChannel socketChannel){
    	NettyChannelMap.add(this.mCurMac, socketChannel);  
    }
}
