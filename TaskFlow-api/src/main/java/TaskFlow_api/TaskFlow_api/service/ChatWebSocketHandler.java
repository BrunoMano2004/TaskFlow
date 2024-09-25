package TaskFlow_api.TaskFlow_api.service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserIdFromSession(session); // Obtenha o ID do usuário (como você desejar)
        sessions.put(userId, session);
        System.out.println("Usuário conectado: " + userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Mensagem recebida: " + message.getPayload());
    }

    // Método para enviar mensagem a um usuário específico
    public void sendMessageToUser(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para remover a sessão quando a conexão for fechada
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserIdFromSession(session);
        sessions.remove(userId);
        System.out.println("Usuário desconectado: " + userId);
    }

    // Método auxiliar para obter o ID do usuário da sessão (implementação depende do seu contexto)
    private String getUserIdFromSession(WebSocketSession session) {
        // Supondo que você tenha um atributo de sessão que guarda o ID do usuário
        return (String) session.getAttributes().get("userId");
    }
}
