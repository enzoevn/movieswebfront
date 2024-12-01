package es.uah.movieswebfront.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.uah.movieswebfront.model.Actor;

public interface IActorService {
    List<Actor> getAllActors();
    Page<Actor> getAllActors(Pageable pageable);
    Actor getActorById(Integer id);
    Actor saveActor(Actor actor);
    void deleteActor(Integer id);
    void updateActor(Actor actor);

}
