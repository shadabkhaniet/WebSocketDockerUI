package sample;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Logger;

import javax.websocket.Session;

import redis.clients.jedis.JedisPubSub;

public class RedisKeyListener extends JedisPubSub {
    //
    // public WsOutbound wsOutbound;
    //

    // private static final Logger myLogger =
    // Logger.getLogger(RedisKeyListener.class.getName());

    private static Integer      counter   = 0;

//    private static StringBuffer strBuffer = new StringBuffer();

    //
    public RedisKeyListener() {
        // this.wsOutbound = wsOutbound;
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
        String str3 = "";
        // if (counter == 0) {
        counter = counter + 1;
        if (channel.split(":").length >= 2)
            str2 = channel.split(":")[1];

        
        if(str2.split("_").length >= 2)
            str3 = str2.split("_")[1];
        
//        strBuffer = strBuffer.append("\n" + str2);

//        CharBuffer outbuf = CharBuffer.wrap("- " + str2);

        try {
            for (Session session : WebSockRedis.sessionList) {
                // asynchronous communication
                // session.getBasicRemote().setBatchingAllowed(true);
                session.getBasicRemote().sendText(str3);
                System.err.println("sending string: " + str3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (counter == 1) {
//
//            try {
//                Thread.currentThread().sleep(2000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            try {
//                for (Session session : WebSockRedis.sessionList) {
//                    // asynchronous communication
//                    // session.getBasicRemote().setBatchingAllowed(true);
//                    session.getBasicRemote().sendText(strBuffer.toString());
//                    System.err.println("sending string: " + strBuffer);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.err.println("counter value before" + counter);
//            strBuffer = strBuffer.append("\n" + str2);
//            counter = counter - 2;
//            System.err.println("counter decreased value: " + counter);
//        }

        // try {
        // this.wsOutbound.writeTextMessage(outbuf);
        // this.wsOutbound.flush();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
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

