package com.game.ipd.test;

import com.game.common.util.CryptionUtils;
import com.game.common.util.HttpClientUtils;
import com.game.common.util.Md5Utils;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})

public class TestLoad {

    @Test
    public void testCrypte() {
        System.out.println("====" + " p");

        String key = "qpvytned";
        String input = "zhujisnahn";
        try {
            byte[] encode = CryptionUtils.Encrypt(input.getBytes("utf-8"), key);
            byte[] output = CryptionUtils.Decrypt(encode, key);
            String out = new String(output, "utf-8");
            System.out.println("out: " + out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoad() {
        try {
            JSONObject jsonObject = new JSONObject();
            String username = "zjs";
            String password = "zjs";
            String version = "1020";
            String channel = "1010";
            int isChannel = 0;
            String md5key = "ga!y^d&eh*wgd";
            String key = "qpvytned";

            String sign = Md5Utils.md5(username + password + version + channel + isChannel + md5key);

            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("channel", channel);
            jsonObject.put("version", version);
            jsonObject.put("isChannel", isChannel);
            jsonObject.put("sign", sign);

            String url = "http://127.0.0.1:8080/load?data=";
            byte[] en =  CryptionUtils.Encrypt(jsonObject.toString().getBytes(), key);
            url += CryptionUtils.bytesToHexString(en);
            String data = HttpClientUtils.GetData(url);
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServerList() {
        String url = "http://127.0.0.1:8080/serverList?token=";
        url += "34420027-5924-4423-a8f0-15bd0de21db9";
        try {
            String result = HttpClientUtils.GetData(url);
            System.out.println("serverlist === > ");
            System.err.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIpd() {
        String url = "http://127.0.0.1:8080/ipd?serverId=2&version=200&token=";
        url += "b55e87f3-b888-4858-827e-8b112fc3bf15";
        try {
            String result = HttpClientUtils.GetData(url);
            System.out.println("ipd === > ");
            System.err.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
