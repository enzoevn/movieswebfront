package es.uah.movieswebfront.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Rate {
    private Integer idRate;
    private Integer idUsuario;
    private Integer idMovie;
    private Double value;
    private Date fecha;
    private Double rateAverage;

    public Rate() {
    }

    public Rate(Integer idMovie, Integer idUsuario) {
        this.idRate = 0;
        this.idMovie = idMovie;
        this.idUsuario = idUsuario;
        this.fecha = new Date();
        this.value = 0.0;
        this.rateAverage = 0.0;
    }
}