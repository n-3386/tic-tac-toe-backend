package com.tictactoe.entity;

import java.util.Arrays;

public class GameState {

    private String[][] board = new String[3][3];
    private String currentPlayer = "X";
    private String winner;
    private boolean draw;
	public String[][] getBoard() {
		return board;
	}
	public void setBoard(String[][] board) {
		this.board = board;
	}
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public boolean isDraw() {
		return draw;
	}
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	@Override
	public String toString() {
		return "GameState [board=" + Arrays.toString(board) + ", currentPlayer=" + currentPlayer + ", winner=" + winner
				+ ", draw=" + draw + "]";
	}

    
}
