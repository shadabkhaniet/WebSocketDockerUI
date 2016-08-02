package sample;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import redis.clients.jedis.Jedis;

@ServerEndpoint(value = "/wsocket")
public class WebSockRedis {
    // notice:not thread-safe
    public static ArrayList<Session> sessionList = new ArrayList<Session>();

    @OnOpen
    public void onOpen(Session session) {
        try {
            sessionList.add(session);
            // asynchronous communication
            session.getBasicRemote().sendText("Hello!");
            try {
                final Jedis jedisInstance = new Jedis("ec2-52-33-201-37.us-west-2.compute.amazonaws.com", 6379, 120000);

                final RedisKeyListener redisKeyListener = new RedisKeyListener();

                // jedisInstance.psubscribe(redisKeyListener, "__key*__:foo*");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Subscribing to \"__key*__:user*\". This thread will be blocked.");
                            // jedis1.psubscribe(keyExpiredListener,
                            // "__key*__:*");
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

        } catch (IOException e) {
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessionList.remove(session);
    }

    @OnMessage
    public void onMessage(String msg) {
        try {
            for (Session session : sessionList) {
                // asynchronous communication
                session.getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
        }
    }
}
