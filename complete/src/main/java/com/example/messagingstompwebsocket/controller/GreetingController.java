package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.Greeting;
import com.example.messagingstompwebsocket.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		logger.info("Received message: {}", message.getName());
		Thread.sleep(1000); // simulated delay
		String response = "Hello, " + message.getName() + "!";
		logger.info("Sending greeting: {}", response);
		return new Greeting(response);
	}

	// Method to send private messages
	public void sendMessageToUser(String username, String message) {
		logger.info("Sending private message to {}: {}", username, message);
		messagingTemplate.convertAndSendToUser(username, "/queue/reply", message);
	}
}