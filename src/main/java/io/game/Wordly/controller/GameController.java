package io.game.Wordly.controller;

import io.game.Wordly.entity.Game;
import io.game.Wordly.entity.Player;
import io.game.Wordly.entity.Word;
import io.game.Wordly.service.GameService;
import io.game.Wordly.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private final GameService gameService;

    @Autowired
    private final PlayerService playerService;

    private final SimpMessagingTemplate messagingTemplate;

    public GameController(GameService gameService, PlayerService playerService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/submit-word")
    public void submitWord(@Payload Word word) {
        System.out.println(word);
        Player player = this.playerService.getPlayer(word.getPlayerId());
        Game game = this.gameService.getGame(player);
        if (this.gameService.updateScore(game, word)) {
            this.messagingTemplate.convertAndSend("/game/moves/" +
                    game.getGameId(), this.gameService.getGameDto(game, player));
        }
    }
}
