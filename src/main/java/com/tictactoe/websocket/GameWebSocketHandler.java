package com.tictactoe.websocket;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.tictactoe.entity.GameState;
import com.tictactoe.entity.Move;

import tools.jackson.databind.ObjectMapper;

public class GameWebSocketHandler extends TextWebSocketHandler {

	private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private static final GameState gameState = new GameState();
	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("Client connected: " + session.getId());
		sessions.add(session);
		// ❌ do not send data here
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		if (message.getPayload().contains("restart")) {
			resetGame();
			broadcastGameState();
			return;
		}

		// ✅ FIX 3: init message
		if (message.getPayload().contains("init")) {
			broadcastGameState();
			return;
		}

		// ❌ stop game if winner already decided
		if (gameState.getWinner() != null) {
			return;
		}

		Move move = mapper.readValue(message.getPayload(), Move.class);
		int row = move.getRow();
		int col = move.getCol();

		// allow move only if cell empty
		if (gameState.getBoard()[row][col] == null) {

			// place move
			gameState.getBoard()[row][col] = gameState.getCurrentPlayer();

			// ✅ STEP 2: CHECK WINNER HERE
			checkWinner();

			// switch player ONLY if no winner
			if (gameState.getWinner() == null) {
				gameState.setCurrentPlayer(gameState.getCurrentPlayer().equals("X") ? "O" : "X");
			}
		}

		broadcastGameState();
	}

	private void broadcastGameState() throws Exception {
		String data = mapper.writeValueAsString(gameState);

		for (WebSocketSession session : sessions) {
			if (!session.isOpen()) {
				sessions.remove(session); // ✅ SAFE for CopyOnWriteArrayList
				continue;
			}

			session.sendMessage(new TextMessage(data));
		}
	}

	// ✅ WINNER LOGIC
	private void checkWinner() {
		String[][] b = gameState.getBoard();

		// Rows
		for (int i = 0; i < 3; i++) {
			if (b[i][0] != null && b[i][0].equals(b[i][1]) && b[i][1].equals(b[i][2])) {
				gameState.setWinner(b[i][0]);
				return;
			}
		}

		// Columns
		for (int i = 0; i < 3; i++) {
			if (b[0][i] != null && b[0][i].equals(b[1][i]) && b[1][i].equals(b[2][i])) {
				gameState.setWinner(b[0][i]);
				return;
			}
		}

		// Diagonals
		if (b[0][0] != null && b[0][0].equals(b[1][1]) && b[1][1].equals(b[2][2])) {
			gameState.setWinner(b[0][0]);
			return;
		}

		if (b[0][2] != null && b[0][2].equals(b[1][1]) && b[1][1].equals(b[2][0])) {
			gameState.setWinner(b[0][2]);
		}
	}

	private void resetGame() {
		String[][] board = gameState.getBoard();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = null;
			}
		}

		gameState.setCurrentPlayer("X");
		gameState.setWinner(null);
		gameState.setDraw(false);

		System.out.println("Game restarted");
	}

}
