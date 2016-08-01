package sample;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import redis.clients.jedis.Jedis;

/**
 * Need tomcat-koyote.jar on class path, otherwise has compile error
 * "the hierarchy of the type ... is inconsistent"
 * 
 * @author wangs
 * 
 */
public class MyInBound extends MessageInbound {

    private String     name;

    public WsOutbound myoutbound;

    public MyInBound(HttpServletRequest httpSerbletRequest) {

    }

    @Override
    public void onOpen(WsOutbound outbound) {
        System.out.println("on open..");
        this.myoutbound = outbound;
        final WsOutbound myoutbound2 = outbound ;
        try {
            this.myoutbound.writeTextMessage(CharBuffer.wrap("Realtime list of registered user:"));

            final Jedis jedisInstance = new Jedis("ec2-52-33-201-37.us-west-2.compute.amazonaws.com", 6379, 120000);

            final RedisKeyListener redisKeyListener = new RedisKeyListener(myoutbound2);

//            jedisInstance.psubscribe(redisKeyListener, "__key*__:foo*");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Subscribing to \"__key*__:user*\". This thread will be blocked.");
                        // jedis1.psubscribe(keyExpiredListener, "__key*__:*");
                        jedisInstance.psubscribe(redisKeyListener, "__key*__:user*");
                        System.out.println("Subscription ended.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Subscribing failed." + e);
                    }
                }
            }).start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onClose(int status) {
        System.out.println("Close client");
        // remove from list
    }

    @Override
    protected void onBinaryMessage(ByteBuffer arg0) throws IOException {

    }

    @Override
    protected void onTextMessage(CharBuffer inChar) throws IOException {

        System.out.println("Accept msg");
        CharBuffer outbuf = CharBuffer.wrap("- " + this.name + " says : ");
        CharBuffer buf = CharBuffer.wrap(inChar);

        if (name != null) {
            this.myoutbound.writeTextMessage(outbuf);
            this.myoutbound.writeTextMessage(buf);
        } else {
            this.name = inChar.toString();

            CharBuffer welcome = CharBuffer.wrap("== Welcome " + this.name + "!");
            this.myoutbound.writeTextMessage(welcome);
        }

        this.myoutbound.flush();

    }

}