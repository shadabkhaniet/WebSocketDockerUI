package sample;

import java.io.IOException;
import java.nio.CharBuffer;

import javax.websocket.Session;

import redis.clients.jedis.JedisPubSub;


public class RedisKeyListener extends JedisPubSub {
//
//    public WsOutbound      wsOutbound;
//
//    private static Integer counter = 0;
//
    public RedisKeyListener() {
//        this.wsOutbound = wsOutbound;
    }

    // final static Logger logger = Logger.getLogger(RedisKeyListener.class);

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

        System.out.println("onPMessage pattern " + pattern + " " + channel + " " + message);
        String str2 = "";

        // if (counter == 0) {
        // counter = 1;
        if (channel.split(":").length >= 2)
            str2 = channel.split(":")[1];

        CharBuffer outbuf = CharBuffer.wrap("- " + str2);

        try{
            for(Session session : WebSockRedis.sessionList){
                //asynchronous communication
                session.getBasicRemote().sendText(str2);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        
//        try {
//            this.wsOutbound.writeTextMessage(outbuf);
//            this.wsOutbound.flush();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        // counter = 0;

        // } else {
        // System.out.println("counter is not 0");
        // }

        // MyInBound myInBound = new MyInBound();

        // if(message.indexOf("lifeSet") != -1)
        // {
        // // str2 = message.substring(message.indexOf("lifeSet") ,
        // message.length());
        // // new JavascriptChat().addMessage(str2);
        // }
    }

    @Override
    public void onMessage(String arg0, String arg1) {
        // TODO Auto-generated method stub
        System.out.println("message received:" + arg0 + ":" + arg1);

    }

    @Override
    public void onPUnsubscribe(String arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSubscribe(String arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUnsubscribe(String arg0, int arg1) {
        // TODO Auto-generated method stub

    }

}

