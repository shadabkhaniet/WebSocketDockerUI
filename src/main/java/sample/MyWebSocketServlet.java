package sample;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * WebSocketServlet is contained in catalina.jar. It also needs servlet-api.jar
 * on build path
 * 
 * @author wangs
 * 
 */
@WebServlet("/wsocket")
public class MyWebSocketServlet extends WebSocketServlet {

    private static final long                               serialVersionUID = 1L;

    // for new clients, <sessionId, streamInBound>
    private static ConcurrentHashMap<String, StreamInbound> clients          = new ConcurrentHashMap<String, StreamInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String protocol, HttpServletRequest httpServletRequest) {

        // Check if exists
        HttpSession session = httpServletRequest.getSession();

        // find client
        StreamInbound client = clients.get(session.getId());
        if (null != client) {
            return client;

        } else {
            client = new MyInBound(httpServletRequest);
            clients.put(session.getId(), client);
        }

        return client;
    }

    public StreamInbound getClient(String sessionId) {
        return clients.get(sessionId);
    }

    public void addClient(String sessionId, StreamInbound streamInBound) {
        clients.put(sessionId, streamInBound);
    }
}
