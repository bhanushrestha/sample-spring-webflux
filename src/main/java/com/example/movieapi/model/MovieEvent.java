package com.example.movieapi.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieEvent {
    private Movie movie;
    private Date when;
    private String user;
}
