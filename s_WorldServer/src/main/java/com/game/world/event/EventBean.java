package com.game.world.event;

import java.lang.reflect.Method;

public class EventBean {
    public Object obj;
    public Method method;

    public EventBean(Object obj, Method method) {
        this.obj = obj;
        this.method = method;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
