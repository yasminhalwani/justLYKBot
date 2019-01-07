package justlykbot.controller;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import justlykbot.entity.ChallengeRequest;
import justlykbot.entity.FriendRequest;
import justlykbot.entity.Player;
import justlykbot.entity.Players;
import justlykbot.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PlayerController {

    @EJB(mappedName = "java:module/PlayerRepository")
    PlayerRepository playerRepository;

    @RequestMapping("/playerList")
    public String getPlayers(Model model) {
        playerRepository.insertTestdata();

        model.addAttribute("playerList", playerRepository.getPlayers());
        return "playerList";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Player Authenticate(@Valid @ModelAttribute("Player") Player player,
            BindingResult bindingResult, Model model,
            HttpSession session) {

        Player aPlayer = playerRepository.getPlayer(player.getplayerAppName(), player.getPass());

        if (aPlayer == null || bindingResult.hasErrors()) {
            model.addAttribute("Player", player);
            model.addAttribute("Error", "Refused)");
        } else {
            session.setAttribute("Player", aPlayer);
            System.out.println("Player :" + aPlayer.getplayerAppName() + " " + " Scussfully login");
        }

        return player;
    }

    //works!
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public @ResponseBody 
    List<Player> getPlayers() {
        //playerRepository.insertTestdata();
        return playerRepository.getPlayers();
    }

    @RequestMapping(value = "/game/{username}/{gamename}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String addGameByPlayer(@PathVariable("username") String playerName, @PathVariable("gamename") String gameName,
            HttpServletResponse response) {

        try {
            playerRepository.addGameByPlayer(playerName, gameName);
            return "Done";
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }

    //works
    @RequestMapping(value = "/playerfriendsrequests/{playername}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<FriendRequest> getPlayerFriendsRequests(@PathVariable("playername") String playerName,
            HttpServletResponse response) {

        try {
            return playerRepository.getFriendRequests(playerName);
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }

    @RequestMapping(value = "/respondtofriendrequest/{requestid}/{response}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String respondToPlayerFriendsRequests(@PathVariable("requestid") int requestId, @PathVariable("response") String responsed,
            HttpServletResponse response) {

        try {
            playerRepository.responseToFriendRequest(requestId, responsed);
            return "DONE";
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }

    @RequestMapping(value = "/player/{playername}/{friendname}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String sendFriendRequest(@PathVariable("playername") String playerName, @PathVariable("friendname") String friendName,
            HttpServletResponse response) {

        try {
            playerRepository.addFriend(playerName, friendName);
            return "Done";
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }

    //works but returns all the players 
    @RequestMapping(value = "/playerchallengerequests/{playerName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ChallengeRequest> getPlayerChallengeRequests(@PathVariable("playerName") String playerName,
            HttpServletResponse response) {

        try {
            System.out.println(playerRepository.getChallengeRequests(playerName));
            return playerRepository.getChallengeRequests(playerName);
        } catch (Exception ex) {
            response.setStatus(404);
        }
        return null;
    }
    
    

    @RequestMapping(value = "/players/{friendName}/{playerName}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteFriend(@PathVariable("friendName") String friendName, @PathVariable("playerName") String playerName, HttpServletResponse response) {
        try {
            playerRepository.deleteFriend(friendName, playerName);
            return "DONE!";
        } catch (Exception ex) {
            response.setStatus(500);
            return "Deleting friend failed because : \n" + ex.getMessage();
        }       
    }
}
