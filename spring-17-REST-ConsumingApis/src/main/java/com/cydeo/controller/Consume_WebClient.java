package com.cydeo.controller;

import com.cydeo.dto.GenreDTO;
import com.cydeo.dto.MovieCinemaDTO;
import com.cydeo.service.GenreService;
import com.cydeo.service.MovieCinemaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class Consume_WebClient {  // reactive programming -- how it works  ----> sync and Async

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
                                                            // this should be third party API url

    private final MovieCinemaService movieCinemaService;
    private final GenreService genreService;

    public Consume_WebClient(MovieCinemaService movieCinemaService, GenreService genreService) {
        this.movieCinemaService = movieCinemaService;
        this.genreService = genreService;
    }

    @GetMapping("/flux-movie-cinemas")
    public Flux<MovieCinemaDTO> readAllCinemaFlux(){

        return Flux.fromIterable(movieCinemaService.findAll());  // Flux means multiple elements

    }

//    @GetMapping("/mono-movie-cinema/{id}")
//    public Mono<MovieCinemaDTO> readById(@PathVariable("id") Long id){
//
//        return Mono.just(movieCinemaService.findById(id));
//
//    }

    @GetMapping("/mono-movie-cinema/{id}")
    public ResponseEntity<Mono<MovieCinemaDTO>> readById(@PathVariable("id") Long id){

        return ResponseEntity.ok(Mono.just(movieCinemaService.findById(id)));

    }

    @PostMapping("/create-genre")
    public Mono<GenreDTO> createGenre(@RequestBody GenreDTO genre){

        GenreDTO createdGenre = genreService.save(genre);

        return Mono.just(createdGenre);
//        return Mono.just(genreRepository.save(genre));

    }

    @DeleteMapping("/delete/genre/{id}")
    public Mono<Void> deleteGenre(@PathVariable("id") Long id){

        genreService.deleteById(id);

        return Mono.empty();  // will not send anything back
    }

//    ---------------------------WEBCLIENT---------------------------

    // this structure should be in service layer

    @GetMapping("/flux")  // consume reactive API
    public Flux<MovieCinemaDTO> readWithWebClient(){

        return webClient
                .get()
                .uri("/flux-movie-cinemas")  // we give an endpoint, and it is combing it with baseUrl on top
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(MovieCinemaDTO.class);  // it will give multiple MovieCinema objs

    }

    @GetMapping("/mono/{id}")  // consume reactive API
    public Mono<MovieCinemaDTO> readMonoWithWebClient(@PathVariable("id") Long id){

        return webClient
                .get()
                .uri("/mono-movie-cinema/{id}",id)
                .retrieve()
                .bodyToMono(MovieCinemaDTO.class);  // it will give only one obj

    }

}
