package es.uah.movieswebfront.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {
    private Integer idMovie;
    private Double rating;
    private Date fecha;
    private Integer usuario;

    public Rate() {
    }

    public Rate(Integer idMovie, Usuario usuario) {
        this.idMovie = idMovie;
        this.usuario = usuario.getIdUsuario();
        this.fecha = new Date();
        this.rating = 0.0;
    }
}