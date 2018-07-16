package com.game.order.entity;

import javax.persistence.*;

import com.game.cache.mysql.BaseEntity;
import com.game.cache.mysql.BaseIdAutoIncEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the payments database table.
 *
 * @author BEA Workshop
 */
@Entity()
@Table(name="payment_tmp")
public class PaymentTemp extends BaseIdAutoIncEntity {
    private static final long serialVersionUID = 1L;

    private String playerChannel; //玩家渠道
    private int playerId; //玩家号
    private String payChannel; //充值渠道
    private String payAgent; // 充值方式 充值渠道可能对应多种充值方式

    private String callbackUrl; // 指回调的游戏服务器地址
    private int executionCount; // 指通知回调次数
    private String orderToken; // 订单号
    private float shouldPayAmt; // 充值金额
    private float realAmt; // 付费实际金额（数据库以元为单位）
    private String remark; // 指第三方渠道商的相关数据，例如订单号等

    private int returnCode; // 指充值结果 200：充值成功 其他：充值失败
    private String returnMessage; // 返回信息

    private int statusCallback; // 订单回调结果，指渠道商服务器将充值结果回调到充值平台（0：初始化状态
    // （没有回调）200：回调成功 431：回调失败）
    private int statusNotify; // 订单通知结果，指充值平台将需要处理的订单回调到服务器进行发币等处理（0：初始化状态
    // 200：通知成功 431：通知失败）

    @Column(name = "player_channel", nullable = false)
    public String getPlayerChannel() {
        return playerChannel;
    }

    public void setPlayerChannel(String playerChannel) {
        this.playerChannel = playerChannel;
    }

    @Column(name = "player_id", nullable = false)
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Column(name = "pay_channel", nullable = false)
    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    @Column(name = "pay_agent", nullable = false)
    public String getPayAgent() {
        return payAgent;
    }

    public void setPayAgent(String payAgent) {
        this.payAgent = payAgent;
    }

    @Column(name = "callback_url", nullable = false)
    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Column(name = "execute_count", nullable = false)
    public int getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(int executionCount) {
        this.executionCount = executionCount;
    }

    @Column(name = "order_token", nullable = false)
    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    @Column(name = "should_pay_amt", nullable = false)
    public float getShouldPayAmt() {
        return shouldPayAmt;
    }

    public void setShouldPayAmt(float shouldPayAmt) {
        this.shouldPayAmt = shouldPayAmt;
    }

    @Column(name = "real_amt", nullable = false)
    public float getRealAmt() {
        return realAmt;
    }

    public void setRealAmt(float realAmt) {
        this.realAmt = realAmt;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "return_code", nullable = false)
    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    @Column(name = "return_message")
    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Column(name = "status_callback", nullable = false)
    public int getStatusCallback() {
        return statusCallback;
    }

    public void setStatusCallback(int statusCallback) {
        this.statusCallback = statusCallback;
    }

    @Column(name = "status_notify", nullable = false)
    public int getStatusNotify() {
        return statusNotify;
    }

    public void setStatusNotify(int statusNotify) {
        this.statusNotify = statusNotify;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Payment)) {
            return false;
        }
        Payment castOther = (Payment) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}