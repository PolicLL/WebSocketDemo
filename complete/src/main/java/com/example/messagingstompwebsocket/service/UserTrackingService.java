package com.example.messagingstompwebsocket.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserTrackingService {

  private static final Logger logger = LoggerFactory.getLogger(UserTrackingService.class);
  private final Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

  public void addUser(String username) {
    connectedUsers.add(username);
    logger.info("User connected: {}", username);
  }

  public void removeUser(String username) {
    connectedUsers.remove(username);
    logger.info("User disconnected: {}", username);
  }

  public Set<String> getConnectedUsers() {
    logger.info("Current connected users: {}", connectedUsers);
    return connectedUsers;
  }
}