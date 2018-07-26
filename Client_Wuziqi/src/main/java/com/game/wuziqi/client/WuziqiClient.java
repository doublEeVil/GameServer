package com.game.wuziqi.client;


import com.game.net.client.NetWebSocketClient;
import com.game.protocol.data.account.Login;
import com.game.protocol.data.battle.SendPlace;
import com.game.wuziqi.client.service.ServiceManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * 控制台类型五子棋客户端
 */
public class WuziqiClient {

    public static void main(String[] args) throws Exception{
        System.out.println("----五子棋启动中----");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        new WuziqiClient().launch(context);
    }

    public void launch(ApplicationContext context) throws Exception{
        QiPan qiPan = new QiPan();
        qiPan.print();

        // 链接服务器
        NetWebSocketClient client = new NetWebSocketClient("192.168.0.192", 8001, "ws://192.168.0.192/ws", context);
        client.launch();

        ServiceManager.getInstance().setClient(client);

        Thread.sleep(131);

        // 登录
        int playerId = 10002;
        ServiceManager.getInstance().setPlayerId(playerId);
        Login login = new Login();
        login.setPlayerId(playerId);
        client.sendData(login);

        // 查找房间，有则加入，没有就新建


        // 开始对局
        Thread.sleep(4000);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Thread.sleep(1000);
            if (!ServiceManager.getInstance().canGame()) {
                continue;
            }
            System.out.println("input x: ");
            int x = scanner.nextInt();
            System.out.println("input y: ");
            int y = scanner.nextInt();

            SendPlace sendPlace = new SendPlace();
            sendPlace.setX(x);
            sendPlace.setY(y);
            client.sendData(sendPlace);

        }
    }


}
