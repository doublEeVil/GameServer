package com.game.pay.service.impl;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.common.util.PrintUtils;
import com.game.pay.entity.Payment;
import com.game.pay.entity.PaymentTemp;
import com.game.pay.service.NotifyService;
import com.game.pay.service.OrderService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("com.game.pay.service.impl.OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GenericMySqlService genericMySqlService;
    @Autowired
    private NotifyService notifyService;

    public PaymentTemp getPaymentTempByToken(String token) {
        String hql = " from PaymentTemp t where t.orderToken = ?";
        return genericMySqlService.getByHql(hql, token);
    }

    @Override
    public void savePayment(PaymentTemp temp, String payChannel, String payAgent, float realAmt, String remark) throws Exception {
        Payment payment = new Payment();

        PropertyUtils.copyProperties(payment, temp);
        payment.setPayChannel(payChannel);
        payment.setPayAgent(payAgent);
        payment.setRealAmt(realAmt);
        payment.setRemark(remark);
        // 固定值
        payment.setStatusCallback(200);
        payment.setReturnCode(200);
        payment.setReturnMessage("success"); //只有成功才返回并记录信息

        genericMySqlService.save(payment);
        notifyService.addPayment(payment);
    }

    /**
     * 游戏启动尝试失败订单再次发送通知
     */
    @PostConstruct
    public void retryNotifyFailPayment() {
        System.out.println("启动中 ... 恢复异常订单");
        String hql = "from Payment t where t.statusNotify != ?";
        List<Payment> list = genericMySqlService.getAllByHql(hql, 200);
        for (Payment payment : list) {
            notifyService.addPayment(payment);
        }
    }
}
