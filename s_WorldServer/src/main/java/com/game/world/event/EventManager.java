package com.game.world.event;

import com.game.world.thread.WorkThreadPool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 一个简单的事件机制
 * 直接用反射得到方法执行体以及执行参数
 */
public class EventManager {
    private static EventManager       instance = null;
    public Map<Integer, Set<EventBean>> eventMap = new HashMap<>();

    public static EventManager getInstance() {
        if (null == instance) {
            synchronized (EventManager.class) {
                if (null == instance) {
                    instance = new EventManager();
                }
            }
        }
        return instance;
    }

    private EventManager() {

    }

    public void initEvent(ApplicationContext ctx) {
        // 只有Service的方法可以监听事件, 该方法必须public
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(Service.class);
        for (Object obj : beansWithAnnotation.values()) {
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Event.class)) {
                    Event event = method.getAnnotation(Event.class);
                    int eventId = event.eventId();
                    EventBean bean = new EventBean(obj, method);
                    Set<EventBean> set = eventMap.get(eventId);
                    if (set == null) {
                        set = new HashSet<>();
                    }
                    set.add(bean);
                    eventMap.put(eventId, set);
                }
            }
        }
    }

    /**
     * 发布事件
     * 线程是同步
     * @param eventId
     * @param params
     */
    public void eventExecute(int eventId, Object ... params) {
        Set<EventBean> set = eventMap.get(eventId);
        if (null == set || set.isEmpty()) {
            return;
        }
        for (EventBean bean : set) {
            try {
                Method method = bean.getMethod();
                Object obj = bean.getObj();
                method.invoke(obj, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发布事件
     * 异步执行
     * @param eventId
     * @param params
     */
    public void eventExecuteASync(int eventId, Object ... params) {
        WorkThreadPool.execute(()->{
            eventExecute(eventId, params);
        });
    }
}
