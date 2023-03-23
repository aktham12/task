package com.atypon.authenticationservice.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


@RestController
@RequestMapping("/api")
public class AuthenticationController {


    @GetMapping("protected/endpoint1")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");

    }



    @GetMapping("protected/endpoint2")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("Hi protection");

    }


    @GetMapping("/endpoint3")
    public ResponseEntity<String> HiNoProtection() {
        return ResponseEntity.ok("Hi no protection");
    }



}
