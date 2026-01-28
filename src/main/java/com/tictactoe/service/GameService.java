package com.tictactoe.service;

import org.springframework.stereotype.Service;

import com.tictactoe.entity.GameState;

@Service
public class GameService {

	private GameState gameState = new GameState();

	public GameState getGameState() {
		return gameState;
	}

	public GameState makeMove(int row, int col) {

		if (gameState.getBoard()[row][col] == null && gameState.getWinner() == null) {

			gameState.getBoard()[row][col] = gameState.getCurrentPlayer();
			checkWinner();
			togglePlayer();
		}
		return gameState;
	}

	private void togglePlayer() {
		gameState.setCurrentPlayer(gameState.getCurrentPlayer().equals("X") ? "O" : "X");
	}

	public void resetGame() {
		gameState = new GameState();
	}

	private void checkWinner() {
		String[][] b = gameState.getBoard();

		for (int i = 0; i < 3; i++) {
			// Rows
			if (b[i][0] != null && b[i][0].equals(b[i][1]) && b[i][1].equals(b[i][2])) {
				gameState.setWinner(b[i][0]);
				return;
			}

			// Columns
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

}
