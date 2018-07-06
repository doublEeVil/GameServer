package com.game.world.test.event_my;

import com.game.world.event.Event;
import org.springframework.stereotype.Service;

@Service
public class TestEventService {

    @Event(eventId = -101)
    public void getEventMsg(String param1, String param2, int num) {
        System.out.println("收到自定义事件通知：" + param1 + " " + param2 + " " + num);
    }
}