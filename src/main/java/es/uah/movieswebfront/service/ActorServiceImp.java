package es.uah.movieswebfront.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.movieswebfront.model.Actor;

@Service
public class ActorServiceImp implements IActorService {

    private String baseUrl = "http://localhost:8001/actors";

    @Override
    public List<Actor> getAllActors() {
        RestTemplate restTemplate = new RestTemplate();
        Actor[] actors = restTemplate.getForObject(baseUrl, Actor[].class);
        assert actors != null;
        return List.of(actors);}

    @Override
    public Actor getActorById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl + "/" + id, Actor.class);
    }

    @Override
    public Actor saveActor(Actor actor) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseUrl, actor, Actor.class);
    }

    @Override
    public void deleteActor(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(baseUrl + "/" + id);
    }

    @Override
    public void updateActor(Actor actor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(baseUrl + "/" + actor.getId(), actor);
    }

}
