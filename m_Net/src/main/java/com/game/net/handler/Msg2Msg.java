package com.game.net.handler;

import com.game.net.ProtocolFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static com.game.common.util.BytesUtils.getValue;
import static com.game.common.util.BytesUtils.setValue;

public final class Msg2Msg {

    private static Logger log = LogManager.getLogger(Msg2Msg.class);

    /**
     * 将网络数据解码成IData
     * @param dataType
     * @param bodyBuf
     * @return
     */
    public static IData decodeToIData(short dataType, ByteBuf bodyBuf) {
        IData data = ProtocolFactory.getIDataCopy(dataType);
        if (null == data) {
            System.err.println("不存在的协议号： " + dataType);
            log.error("不存在的协议号： " + dataType);
            return null;
        }
        Field[] fs = data.getClass().getDeclaredFields();
        for (Field f : fs) {
            try {
                PropertyUtils.setProperty(data, f.getName(), getValue(f, bodyBuf));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 将IData编码成网络数据
     * @param data
     * @return
     */
    public static ByteBuf encodeFromIData(IData data) {

        ByteBuf bodyBuf = Unpooled.directBuffer();
        Field[] fs = data.getClass().getDeclaredFields();

        for (Field f : fs) {
            try {
                Object val = PropertyUtils.getProperty(data, f.getName());
                setValue(f, val, bodyBuf);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ByteBuf headBuf = Unpooled.directBuffer();
        headBuf.writeByte(101);                   //头两位
        headBuf.writeByte(-102);
        headBuf.writeShort(data.getProtocolId()); // 协议号
        headBuf.writeByte(-88);                   // 保留位
        headBuf.writeShort(bodyBuf.readableBytes());  // 长度

        ByteBuf all = Unpooled.wrappedBuffer(headBuf, bodyBuf);
        all.retain();
        return all;
    }
}
