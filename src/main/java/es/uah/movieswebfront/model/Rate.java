package es.uah.movieswebfront.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Rate {
    private Integer idRate;
    private Integer idUsuario;
    private String username;
    private String movieTitle;
    private Usuario usuario;
    private Integer idMovie;
    private Movie movie;
    private Double value;
    private Date fecha;
    private Double rateAverage;

}