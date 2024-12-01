package es.uah.movieswebfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MovieswebfrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieswebfrontApplication.class, args);
    }

}
