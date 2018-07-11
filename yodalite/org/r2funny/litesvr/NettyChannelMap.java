package org.r2funny.litesvr;

/**
 * socket 映射对象, 存储相关数据
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
     * 清空Map
     */
    public static void clear(){
    	map.clear();
    }
    
    /**
     * 返回Map的个数
     * @return
     */
    public static int count(){
    	return map.size();
    }
}
