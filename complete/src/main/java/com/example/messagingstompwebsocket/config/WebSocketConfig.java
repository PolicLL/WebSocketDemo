package com.example.messagingstompwebsocket.config;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		logger.info("Registering WebSocket endpoint");
		registry.addEndpoint("/gs-guide-websocket")
//				.addInterceptors(new HandshakeInterceptor() {
//					@Override
//					public boolean beforeHandshake(
//							ServerHttpRequest request, ServerHttpResponse response,
//							WebSocketHandler wsHandler, Map<String, Object> attributes
//					) {
//						String username = request.getHeaders().getFirst("username");
//						if (username != null) {
//							attributes.put("username", username);
//							logger.info("Handshake started for username: {}", username);
//						} else {
//							logger.warn("Handshake attempted without username");
//						}
//						return true;
//					}
//
//					@Override
//					public void afterHandshake(
//							ServerHttpRequest request, ServerHttpResponse response,
//							WebSocketHandler wsHandler, Exception exception
//					) {
//						logger.info("Handshake completed");
//					}
//				})
				.setAllowedOriginPatterns("*")
				.withSockJS();
	}

}