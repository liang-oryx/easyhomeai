package org.r2funny.litesvr;

/**
 * socket ӳ�����, �洢�������
 * @author liang
 *
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;  
import io.netty.channel.socket.SocketChannel;  

public class NettyChannelMap {
	private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();  
	
    public static void add(String clientId,SocketChannel socketChannel){  
        map.put(clientId,socketChannel);  
    } 
    
    public static Channel get(String clientId){  
       return map.get(clientId);  
    }
    
    public static void remove(String clientId){
    	map.remove(clientId);
    }
    
    public static void remove(SocketChannel socketChannel){  
        for (@SuppressWarnings("rawtypes") Map.Entry entry:map.entrySet()){  
            if (entry.getValue()==socketChannel){  
                map.remove(entry.getKey());  
            }  
        }  
    }  
    
    /**
     * ���Map
     */
    public static void clear(){
    	map.clear();
    }
    
    /**
     * ����Map�ĸ���
     * @return
     */
    public static int count(){
    	return map.size();
    }
}
