package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.service.UserTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
  private final UserTrackingService userTrackingService;
  private final SimpMessagingTemplate messagingTemplate;

  public WebSocketEventListener(UserTrackingService userTrackingService, SimpMessagingTemplate messagingTemplate) {
    this.userTrackingService = userTrackingService;
    this.messagingTemplate = messagingTemplate;
  }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = accessor.getFirstNativeHeader("username");
    if (username != null) {
      userTrackingService.addUser(username);
      logger.info("WebSocket connection established for user: {}", username);
      messagingTemplate.convertAndSend("/topic/notifications", username + " connected.");
    } else {
      logger.warn("WebSocket connection attempted without a username");
    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) accessor.getSessionAttributes().get("username");
    if (username != null) {
      userTrackingService.removeUser(username);
      logger.info("WebSocket connection closed for user: {}", username);
      messagingTemplate.convertAndSend("/topic/notifications", username + " disconnected.");
    } else {
      logger.warn("Disconnection detected without a username");
    }
  }
}