package com.game.pay.service.impl;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.common.util.HttpClientUtils;
import com.game.pay.entity.Payment;
import com.game.pay.service.NotifyService;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("com.game.pay.service.impl.NotifyServiceImpl")
public class NotifyServiceImpl implements NotifyService, Runnable {

    @Autowired
    private GenericMySqlService genericMySqlService;
    private Queue<Payment> paymentQueue = new ConcurrentLinkedDeque<>();
    private ExecutorService http_pool = Executors.newCachedThreadPool();

    public NotifyServiceImpl() {
        start();
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void addPayment(Payment payment) {
        paymentQueue.add(payment);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void run() {
        while (true) {
            try {
                if (paymentQueue.isEmpty()) {
                    synchronized (this) {
                        this.wait();
                    }
                }
                Payment payment = paymentQueue.poll();
                if (payment.getExecutionCount() >= 5) {
                    continue;
                }
                payment.setExecutionCount(1 + payment.getExecutionCount());
                http_pool.submit(new NotifyTask(payment));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    class NotifyTask implements Runnable{
        private Payment payment;

        public NotifyTask(Payment payment) {
            this.payment = payment;
        }

        @Override
        public void run() {
            String url = payment.getCallbackUrl();
            List<NameValuePair> data = new ArrayList<>();
            boolean isSuccess = true;
            try {
                String ret = HttpClientUtils.PostData(url, data);
                if (!"success".equals(ret)) {
                    isSuccess = false;
                }
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
            }
            if (isSuccess) {
                payment.setStatusNotify(200);
            } else {
                payment.setStatusNotify(443);
            }
            genericMySqlService.update(payment);
            genericMySqlService.flush();

            if (!isSuccess && payment.getExecutionCount() < 5) {
                // 失败5次内
                addPayment(payment);
            }
        }
    }
}
