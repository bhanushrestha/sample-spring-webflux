package com.example.movieapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.example.movieapi.model.Movie;

import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveMongoRepository<Movie,String>{

    @Tailable
    Flux<Movie> findWithTailableCursorBy();
}
