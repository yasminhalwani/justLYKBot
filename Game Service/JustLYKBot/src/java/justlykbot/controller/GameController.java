package justlykbot.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import justlykbot.entity.Game;
import justlykbot.entity.Games;
import justlykbot.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class GameController {

    @EJB(mappedName = "java:module/GameRepository")
    GameRepository gameRepository;

    //works!
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public @ResponseBody
    List<Game> getGames() {

       return gameRepository.getGames();
    }

    //works!    
   /* @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Game getGame(@PathVariable("id") int gameId,
            HttpServletResponse response) {

        try {
            return gameRepository.getGame(gameId);
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }*/

    //works!
    @RequestMapping(value = "/games/{gameName}/{playerName}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteGameByPlayer(@PathVariable("gameName") String gameName,@PathVariable("playerName") String playerName, HttpServletResponse response) {
        try {
            gameRepository.deleteGame(gameName,playerName);
        } catch (Exception ex) {
            response.setStatus(500);
            return "Deleting game failed because : \n" + ex.getMessage();
        }
        return "Game " + gameName + " was deleted";
    }
    
     @RequestMapping(value = "/games/{playerName}/{gameName}", method = RequestMethod.POST)
     public @ResponseBody String addGameByPlayer(@PathVariable("gameName") String gameName,@PathVariable("playerName") String playerName,
     HttpServletResponse response) {

     try {
     gameRepository.addGameByPlayer(playerName, gameName);
     return "DONE";
     }
     catch (Exception ex) {
     response.setStatus(500);
     return "Adding game failed because : \n" + ex.getMessage();
     }
     }
     
            /* @RequestMapping(value = "/players/{playerName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByPlayer(@PathVariable("playerName") String playerName, HttpServletResponse response) {
        try {
            playerRepository.deleteFriend(friendName, playerName);
            return "DONE!";
        } catch (Exception ex) {
            response.setStatus(500);
            return "Deleting friend failed because : \n" + ex.getMessage();
        }*/
}
