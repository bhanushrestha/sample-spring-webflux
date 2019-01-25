package com.example.movieapi;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieEvent;
import com.example.movieapi.service.MovieService;

@SpringBootApplication
public class MovieApiApplication {

    @Autowired
    private MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(MovieApiApplication.class, args);
    }

    // New way called Functional Reactive End-Points
    // This is a substitute for classic Rest Controllers
    @Bean
    RouterFunction<ServerResponse> routes(MovieService service) {
        return RouterFunctions
            .route(RequestPredicates.GET("/movies"),
                    request -> ServerResponse.ok().body(service.findAll(), Movie.class))
            .andRoute(RequestPredicates.GET("/movies/{id}"),
                    request -> ServerResponse.ok().body(service.findById(request.pathVariable("id")), Movie.class))
            .andRoute(RequestPredicates.GET("/movies/{id}/events"),
                    request -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                            service.findById(request.pathVariable("id")).flatMapMany(service::streamMovies),
                            MovieEvent.class));
    }

    @Bean
    CommandLineRunner init(ReactiveMongoOperations operations, MovieService service) {
        return args -> {

            service.deleteAllMovies().subscribe(null, null,
                    () -> Stream.of("Movie1", "Movie2", "Movie3")
                        .map(name -> new Movie(null, name, "Movie", LocalDateTime.now()))
                        .forEach(m -> service.save(m).subscribe(System.out::println)));

            /*
             * Alternate way to enter initial movie details
             * 
             * //CommandLineRunner init(ReactiveMongoOperations operations, MovieRepository movieRepository) {
             * Flux<Movie> productFlux = Flux .just(new Movie(null, "Avenger: Infinity Wars", "Action",
             * LocalDateTime.now()), new Movie(null, "Gladiator", "Drama/Action", LocalDateTime.now()), new Movie(null,
             * "Black Panther", "Action", LocalDateTime.now())) .flatMap(movieRepository::save);
             * 
             * productFlux.thenMany(movieRepository.findAll()).subscribe(System.out::println);
             *}));
             */
             
        };
    }

}
