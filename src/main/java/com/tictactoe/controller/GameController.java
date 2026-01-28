package com.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tictactoe.entity.GameState;
import com.tictactoe.service.GameService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public GameState getGame() {
        return gameService.getGameState();
    }

    @PostMapping("/move")
    public GameState move(@RequestParam int row, 
                          @RequestParam int col) {
        return gameService.makeMove(row, col);
    }

    @PostMapping("/reset")
    public void reset() {
        gameService.resetGame();
    }
}
