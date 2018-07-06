package com.game.world.test.event_spring;

import org.springframework.context.ApplicationEvent;

/**
 * 通过spring的内置机制发布和监听事件
 */
public class MyEvent extends ApplicationEvent {
    private int eventId;
    private Object[] params;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public MyEvent(Object source, int eventId, Object[] params) {
        super(source);
        this.eventId = eventId;
        this.params = params;
    }
}
