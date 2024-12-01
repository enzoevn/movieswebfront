package es.uah.movieswebfront.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Actor> getAllActors(Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();
        Actor[] actors = restTemplate.getForObject(baseUrl, Actor[].class);
        assert actors != null;
        List<Actor> actorList = Arrays.asList(actors);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), actorList.size());
        return new PageImpl<>(actorList.subList(start, end), pageable, actorList.size());
    }

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
