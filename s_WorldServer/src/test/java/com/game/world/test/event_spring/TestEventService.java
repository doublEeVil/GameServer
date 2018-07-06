package com.game.world.test.event_spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("testEventSpringService")
public class TestEventService {

    @EventListener
    public void testEvent2(MyEvent myEvent) {
        System.out.println("收到spring 自定义消息");
        System.out.println(myEvent);
        System.out.println(myEvent.getEventId());
        for (Object obj : myEvent.getParams()) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }
}
