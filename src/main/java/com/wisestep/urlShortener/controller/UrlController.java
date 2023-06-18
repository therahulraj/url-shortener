package com.wisestep.urlShortener.controller;

import com.wisestep.urlShortener.entity.Url;
import com.wisestep.urlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://url-shortener-angular-app.vercel.app/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<?> createShortUrl(@RequestHeader(HttpHeaders.REFERER) String resourceRefer,
                                            @RequestBody String url) throws ParseException {
        Url urlCreated = urlService.generateShortUrl(url, resourceRefer);
        return ResponseEntity.ok(urlCreated);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortUrl) throws ParseException {
        return urlService.getOriginUrl(shortUrl);
    }



}
