import com.game.net.handler.Msg2Msg;
import com.game.protocol.data.account.Login;
import io.netty.buffer.ByteBuf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        String s = "{\n" +
                "\"code\":0,\n" +
                "\"subcode\":0,\n" +
                "\"message\":\"\",\n" +
                "\"default\":0,\n" +
                "\"data\":\n" +
                "[{\n" +
                "\"is_vip\":\"1\",\n" +
                "\"vip_level\":\"8\",\n" +
                "\"score\":\"1000\",\n" +
                "\"expiredtime\":\"1407312182\"\n" +
                "}]\n" +
                "}";
        JSONObject json = JSONObject.fromObject(s);
        JSONArray array = json.getJSONArray("data");
        System.out.println(array.getJSONObject(0).getString("score"));
    }


    @org.junit.Test
    public void test1() {
        Login login = new Login();
        login.setPlayerId(123);
        ByteBuf buf = Msg2Msg.encodeFromIData(login);
        System.out.println(buf.readByte());
        System.out.println("====");
    }
}
