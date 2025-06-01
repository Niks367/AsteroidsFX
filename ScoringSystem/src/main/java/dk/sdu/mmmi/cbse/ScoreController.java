package dk.sdu.mmmi.cbse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final IScoreService scoreService;

    @Autowired
    public ScoreController(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/current")
    public int getCurrentScore() {
        return scoreService.getScore();
    }

    @PostMapping("/update")
    public void updateScore() {
        scoreService.updateScore();
    }
}
