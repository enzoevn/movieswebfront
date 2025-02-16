package es.uah.movieswebfront.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Rate {
    private Integer idRate;
    private Usuario usuario;
    private Integer idMovie;
    private Double value;
    private Date fecha;
    private Double rateAverage;

}