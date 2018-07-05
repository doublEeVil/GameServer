package com.game.world.game.service.base;

import com.game.world.event.Event;
import com.game.world.event.EventId;
import com.game.world.event.springevent.MyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("abcService")
public class AbcService {
    public void say() {
        System.out.println("...");
    }

    @Event(eventId = EventId.TEST1)
    public void testEvent1(String s1, String s2) {
        System.out.println("监听事件===" + s1 + " " + s2);
    }

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
