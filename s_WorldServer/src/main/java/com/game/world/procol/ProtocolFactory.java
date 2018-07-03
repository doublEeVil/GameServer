package com.game.world.procol;

import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MessageLite;

import java.util.HashMap;
import java.util.Map;

public class ProtocolFactory {
    private static Map<Class, IDataHandler> handlerMap = new HashMap<>();
    private static Map<Short, GeneratedMessageV3> id2MsgMap = new HashMap<>();
    private static Map<Class, Short> msg2idMap = new HashMap<>();

    public static void register(Class<? extends MessageLite> msg, IDataHandler handler) {
        if (handlerMap.containsKey(msg)) {
            throw new RuntimeException("多个handler处理同一个消息 dataType:" + msg.getName()
                    + " handler1:" + handler.getClass().getName()
                    + " handler2:" + handlerMap.get(msg).getClass().getName());
        }
        handlerMap.put(msg, handler);
    }

    public static IDataHandler getDataHandler(MessageLite msg) {
        return handlerMap.get(msg.getClass());
    }

    public static short getMsgHeadId(MessageLite msg) {
        return msg2idMap.getOrDefault(msg.getClass(), (short)0);
    }

    public static GeneratedMessageV3 getMsg(short headId) {
        return id2MsgMap.get(headId);
    }

    // 添加对应的proto 与 id 的对应关系
    static {
        // 100 Test 协议组
        id2MsgMap.put(Protocol.Test_Req1, Req1Protos.Req1.getDefaultInstance());
        id2MsgMap.put(Protocol.Test_Req2, Req2Protos.Req2.getDefaultInstance());
    }

    // 处理
    static {
        id2MsgMap.forEach((k,v) -> {
            msg2idMap.put(v.getClass(), k);
        });
    }
}
