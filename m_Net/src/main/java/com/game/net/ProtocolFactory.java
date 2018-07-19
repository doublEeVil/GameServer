package com.game.net;


import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;

import java.util.HashMap;
import java.util.Map;

public class ProtocolFactory {
    private static Map<Class, IDataHandler> DataClass_Handler_Map = new HashMap<>();
    private static Map<Short, IData> DataId_Data_Map = new HashMap<>();

    public static void register(Class<? extends IData> msg, IDataHandler handler) {
        if (DataClass_Handler_Map.containsKey(msg)) {
            throw new RuntimeException("多个handler处理同一个消息 dataType:" + msg.getName()
                    + " handler1:" + handler.getClass().getName()
                    + " handler2:" + DataClass_Handler_Map.get(msg).getClass().getName());
        }
        // 协议类 协议处理类
        DataClass_Handler_Map.put(msg, handler);
        // 协议号对协议实体
        try {
            IData data = msg.newInstance();
            DataId_Data_Map.put(data.getProtocolId(), data);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static IDataHandler getDataHandler(IData msg) {
        return DataClass_Handler_Map.get(msg.getClass());
    }

    /**
     * 来一个IData数据浅拷贝
     * newInstance耗时会比clone多一些
     * @param protocolId
     * @return
     */
    public static IData getIDataCopy(short protocolId) {
        IData data = DataId_Data_Map.get(protocolId);
        if (data != null) {
            try {
                return data.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
