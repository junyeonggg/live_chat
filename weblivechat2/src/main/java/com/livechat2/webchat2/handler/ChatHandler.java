package com.livechat2.webchat2.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class ChatHandler implements WebSocketHandler{
	private final List<WebSocketSession> sessions = new ArrayList();
	//연결이 완료되었을 때 호출되는 메소드이다.
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		System.out.println("입장합니다. 현재 채팅방 인원 : "+sessions.size());
		session.sendMessage(new TextMessage("서버에 연결되었습니다!"));
	}

	//메시지를 수신했을 때 호출되는 메소드이다.
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("연결 완료");
		for(WebSocketSession wssession:sessions) {
			wssession.sendMessage(new TextMessage(""+message.getPayload()));
		}
	}

	//에러가 발생했을때 호출되는 메소드이다. (네트워크 끊김, 서버 끊김 등)
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("에러 발생: " + exception.getMessage());
        sessions.remove(session);
        session.close(CloseStatus.SERVER_ERROR);	
	}

	//연결이 종료되었을 때 호출되는 메소드이다.
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId());
        sessions.remove(session);
        System.out.println("퇴장합니다. 현재 채팅방 인원 : "+sessions.size());
	}

	// 부분 메시지를 처리할지 여부 , default : false
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
