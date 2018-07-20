package com.game.world.test;

import com.game.net.client.ClientType;
import com.game.net.client.NetClient;
import com.game.protocol.data.account.Login;
import org.junit.Test;

public class TestClient {

    @Test
    public void testClient() throws Exception{
        NetClient client = new NetClient("192.168.0.192", 8001, ClientType.TP_WEBSOCKET, "ws://192.168.0.192:8001/ws");
        client.launch();
        Login login = new Login();
        login.setPlayerId(12345690);
        for (int i = 0; i < 1; i++) {
            client.sendData(login);
        }
        client.sendData(login);

        Thread.sleep(1000);
    }
}
