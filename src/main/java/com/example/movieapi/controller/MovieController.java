/*package com.example.movieapi.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieEvent;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.service.MovieService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {
    
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    
    
    private Logger logger = LoggerFactory.getLogger(MovieController.class);
    public MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    
    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> getStream(@PathVariable String id){
        //return movieService.findById(id).flatMap(movie -> movieService.movieStreams(movie));
        
        //return movieService.streamMovies(movieService.findById(id).block());
        return movieService.findById(id).flatMapMany(movieService::streamMovies);
        //return movieService.findById(id).flatMap(movieService::streamMovies);
    }
    
    //@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping
    public Flux<Movie> getMovies(){
        //logger.info("Received request for all"
          //      + " movies {}", movieRepository.findAll().toStream().toArray().toString());
        
        return movieService.findAll();
        //return movieRepository.findAll();
        //return movieRepository.findWithTailableCursorBy().delayElements(Duration.ofMillis(2500));
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Movie>> getMovie(@PathVariable String id){
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movie> saveMovie(@RequestBody Movie movie){
        return movieRepository.save(movie);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Movie>> updateMovie(@PathVariable(value = "id") String id, @RequestBody Movie movie){
        return movieRepository.findById(id)
                .flatMap(existingMovie -> {
                    existingMovie.setName(movie.getName());
                    existingMovie.setGenre(movie.getGenre());
                    existingMovie.setReleaseDate(movie.getReleaseDate());
                    return movieRepository.save(existingMovie);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable(value = "id") String id){
        return movieRepository.findById(id)
                .flatMap(existingMovie ->
                    movieRepository.delete(existingMovie)
                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllMovies(){
        return movieRepository.deleteAll();
    }
}
*/