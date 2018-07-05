package com.game.world.servlet;

import com.game.common.util.PrintUtils;
import com.game.world.AppConfig;
import com.game.world.WorldServer;
import com.game.world.event.EventId;
import com.game.world.event.EventManager;
import com.game.world.event.springevent.EventPublisher;
import com.game.world.event.springevent.MyEvent;
import com.game.world.game.service.base.TestServer;
import com.game.world.net.IHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = LogManager.getLogger(TestServlet.class);

    @Autowired
    private TestServer testServer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(" ts  dhh ...");
        resp.getWriter().write(" 132 ");
        System.out.println(testServer);
        TestServer t = (TestServer)WorldServer.getContext().getBean("testServer");
        System.out.println(t);
        t.say();

        Map<String, Object> map = WorldServer.getContext().getBeansWithAnnotation(IHandler.class);
        map.keySet().forEach((k)->{
            System.out.println(k);
        });

        // 测试发布事件
        EventManager.getInstance().eventExecute(EventId.TEST1, "param1", "param2");

        // 测试使用spring的发布事件
        EventPublisher.getPublisher().publishEvent(new MyEvent(this, EventId.TEST2, new Object[]{"paa", 123}));

        // 测试mysql读写数据
        t.testMysql();

        // 跑一个异常
        t.testException();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}