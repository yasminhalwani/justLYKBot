package justlykbot.controller;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import justlykbot.entity.Trophies;
import justlykbot.repository.TrophyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrophyController {

    @EJB(mappedName = "java:module/TrophyRepository")
    TrophyRepository trophyRepository;

    //works!!!!
    @RequestMapping(value = "/trophiesByGame/{gameName}", method = RequestMethod.GET)
    public @ResponseBody
    Trophies getTrophiesByGameName(@PathVariable("gameName") String gameName,
            HttpServletResponse response) {

        return new Trophies(trophyRepository.getTrophiesByGameName(gameName));
    }

    //works!!!!
    @RequestMapping(value = "/trophiesByPlayer/{playerId}", method = RequestMethod.GET)
    public @ResponseBody
    Trophies getTrophiesByPlayerName(@PathVariable("playerId") int playerId,
            HttpServletResponse response) {

        return new Trophies(trophyRepository.getTrophiesByPlayerName(playerId));
    }
}
