package com.inspire12.likelionwebsocket.handshake;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        String token = getTokenFromRequest(request);
        System.out.println("token: " + token);
        if (token != null) {
            return () -> token;
        }

        return null;
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        // URL 파라미터에서 토큰 추출
        String query = request.getURI().getQuery();
        if (query != null && query.contains("token=")) {
            return query.split("token=")[1];
        }
        return null;
    }
}
