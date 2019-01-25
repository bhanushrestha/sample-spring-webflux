package com.example.movieapi.service;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

        public Flux<MovieEvent> streamMovies(Movie movie);
        public Flux<Movie> findAll();
        public Mono<Movie> findById(String id);
        public Mono<Void> deleteAllMovies();
        public Mono<Movie> save(Movie movie);
};
