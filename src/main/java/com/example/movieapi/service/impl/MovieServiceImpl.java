package com.example.movieapi.service.impl;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieEvent;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.service.MovieService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Mono<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Flux<MovieEvent> streamMovies(Movie movie) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<MovieEvent> events = Flux.fromStream(Stream.generate(() -> new MovieEvent(movie,new Date(), randomUser())));
        
        return Flux.zip(interval, events).map(Tuple2::getT2);
    }
    
    private String randomUser() {
        String[] users = "User1,User2,User3,User4".split(",");
        return users[new Random().nextInt(users.length)];
    }

    @Override
    public Mono<Movie> save(Movie movie) {
        // TODO Auto-generated method stub
        return movieRepository.save(movie);
    }
    
    @Override
    public Mono<Void> deleteAllMovies(){
        return movieRepository.deleteAll();
    }
}
