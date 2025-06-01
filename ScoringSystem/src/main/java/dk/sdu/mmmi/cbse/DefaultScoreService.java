package dk.sdu.mmmi.cbse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultScoreService implements IScoreService {
    private RestTemplate restTemplate;

    @Autowired
    public void ScoringServiceClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public void updateScore() {
        restTemplate.postForLocation( "/scores/update", null);
    }

    @Override
    public int getScore() {
        return restTemplate.getForObject( "/scores/current", Integer.class);
    }
}
