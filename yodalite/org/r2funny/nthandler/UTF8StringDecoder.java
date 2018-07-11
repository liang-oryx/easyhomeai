package org.r2funny.nthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.Charset;

import java.util.List;

public class UTF8StringDecoder extends MessageToMessageDecoder<ByteBuf>  {
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
                          List<Object> out) throws Exception {
		Charset utf8 = Charset.forName("UTF-8");
		String  strData = msg.toString(utf8); 
        //System.out.println("String Decoder decode msg is " + msg.toString());
        //System.out.println("String Decoder decode utf-8 msg is " + msg.toString(utf8));
        out.add(strData);
    }
}
