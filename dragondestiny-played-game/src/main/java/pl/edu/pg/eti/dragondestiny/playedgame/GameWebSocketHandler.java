package pl.edu.pg.eti.dragondestiny.playedgame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.NotificationMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final MultiSessionMap<String, WebSocketSession> sessionsMap = new MultiSessionMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        String[] elements = path.split("/");
        String id = elements[elements.length - 1];
        sessionsMap.put(id, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionsMap.remove(session);
    }

    public void broadcastMessage(String playedGameId, NotificationMessage message) throws JsonProcessingException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message.toString()));
        for (WebSocketSession session : sessionsMap.map.get(playedGameId)) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
            }
        }
    }

    private class MultiSessionMap<K, V> {
        private final Map<K, ArrayList<V>> map = new HashMap<>();

        public void put(K key, V value) {
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        public void remove(V value) {
            List<K> keysToRemove = new ArrayList<>();
            map.forEach((key, list) -> {
                if (list.remove(value) && list.isEmpty()) {
                    keysToRemove.add(key);
                }
            });
            keysToRemove.forEach(map::remove);
        }
    }
}
