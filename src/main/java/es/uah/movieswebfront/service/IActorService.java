package es.uah.movieswebfront.service;

import java.util.List;

import es.uah.movieswebfront.model.Actor;

public interface IActorService {
    List<Actor> getAllActors();
    Actor getActorById(Integer id);
    Actor saveActor(Actor actor);
    void deleteActor(Integer id);

}
