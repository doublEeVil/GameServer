package com.game.net.test.client;

import com.game.net.client.ClientType;
import com.game.net.client.NetClient;
import com.game.net.client.NetWebSocketClient;
import com.game.net.handler.Msg2Msg;
import com.game.net.test.TestData;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestClient {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testWebsocketClient() throws Exception{
        NetWebSocketClient client = new NetWebSocketClient("192.168.0.192", 9200, "ws://192.168.0.192/ws", context);
        client.launch();
        client.sendData(new TextWebSocketFrame("45fas"));

        TestData data = new TestData();
        data.setName("jajs");
        ByteBuf buf = Msg2Msg.encodeFromIData(data);

        client.sendData(new BinaryWebSocketFrame(buf));
        Thread.sleep(1233);
    }

    @Test
    public void testTcpClient() throws Exception {
        NetClient client = new NetClient("localhost", 9200, ClientType.TP_TCP, context);
        client.launch();

        TestData data = new TestData();
        data.setName("ip]][[[]]]");
        client.sendData(data);

        Thread.sleep(1234);
    }

    @Test
    public void testUdpClient() throws Exception {
        NetClient client = new NetClient("localhost", 9200, ClientType.TP_UDP, context);
        client.launch();

        TestData data = new TestData();
        data.setName("ip]][[[]]]");
        client.sendData(data);

        Thread.sleep(1234);
    }
 }
