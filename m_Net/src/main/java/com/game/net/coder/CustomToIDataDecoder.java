package com.game.net.coder;

import com.game.net.handler.IData;
import com.game.net.handler.Msg2Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * 得到的字节码转自定义AbstractData
 * 假设包结构是
 * 包头 + 包体
 * 包头 = (固定值(byte 101) + (固定值byte -102) (short 协议类型) + (byte 保留位) +（short 长度） = 7 byte
 */
public class CustomToIDataDecoder extends ByteToMessageDecoder {
    private static Logger log = LogManager.getLogger(CustomToIDataDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 7) { // 可读部分小于7，直接退出
            in.markReaderIndex();
            byte flag1 = in.readByte(); //固定头1
            byte flag2 = in.readByte(); //固定头2
            short dataType = in.readShort(); //协议类型
            byte flag3 = in.readByte(); //保留位
            short length = in.readShort(); //协议长度

            if (flag1 != 101 || flag2 != -102) {
                in.skipBytes(in.readableBytes());
                return;
            }

            if (flag3 != -88) {
                //整个包是有问题的，废弃这个包
                in.skipBytes(in.readableBytes());
                return;
            }

            if (in.readableBytes() < length) { //可读部分小于body，恢复读指针
                in.resetReaderIndex();
                return;
            }

            ByteBuf bodyBuf = in.readBytes(length);
            IData msg = decodeBody(dataType, bodyBuf);
            out.add(msg);
        }
    }

    /**
     * 根据协议号解码数据
     * @param dataType
     * @param bodyBuf
     * @return
     */
    private IData decodeBody(short dataType, ByteBuf bodyBuf) {
        return Msg2Msg.decodeToIData(dataType, bodyBuf);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
    }
}
