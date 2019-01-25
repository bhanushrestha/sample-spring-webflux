package com.example.movieapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.movieapi.model.Movie;
import com.example.movieapi.repository.MovieRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieApiApplicationTests {

        @Autowired
        private WebTestClient webTestClient;
        
        @Autowired
        MovieRepository movieRepository;
    
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testGetAllMovie() {
	    webTestClient.get()
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Movie.class);
	}

}

