package com.wisestep.urlShortener.service;

import com.google.common.hash.Hashing;
import com.wisestep.urlShortener.entity.Url;
import com.wisestep.urlShortener.exception.ExpiredLinkException;
import com.wisestep.urlShortener.exception.InvalidUrlException;
import com.wisestep.urlShortener.exception.UrlNotFoundException;
import com.wisestep.urlShortener.repository.UrlRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public static String getOriginalUrl(String url) {
        return Hashing.murmur3_32_fixed().hashString(url, Charset.defaultCharset()).toString();
    }

    public static boolean isUrlValid(String url) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http","https"}
        );
        return urlValidator.isValid(url);
    }

    public static long getTimeDifferenceInMinutes(String timeStamp) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date savedTimeStamp = sdf.parse(timeStamp);
        Date currentTimeStamp = sdf.parse(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
        return TimeUnit.MILLISECONDS.toMinutes(currentTimeStamp.getTime() - savedTimeStamp.getTime());
    }

    public ResponseEntity<?> getOriginUrl(String shortUrl) throws ParseException {
        Optional<Url> optionalUrl = urlRepository.findByShortenedUrl(shortUrl);
        if (optionalUrl.isPresent()) {
            if (getTimeDifferenceInMinutes(optionalUrl.get().getTimestamp()) > 5) {
                throw new ExpiredLinkException("This Link has been expired. Need to create short url again.");
            }
            return ResponseEntity.ok(optionalUrl.get());
        }
        throw new UrlNotFoundException("No such link exists.");
    }

    public Url createShortUrl(String url) {
        Url urlObject = new Url();
        urlObject.setOriginalUrl(url);
        urlObject.setShortenedUrl(getOriginalUrl(url));
        urlObject.setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
        return urlRepository.save(urlObject);
    }

    public Url generateShortUrl(String url, String resourceRefer) throws ParseException {

        if(!isUrlValid(url)) {
            throw new InvalidUrlException("Please Enter a valid Url.");
        }

        var optionalUrl = urlRepository.findByOriginalUrl(url);
        if (optionalUrl.isPresent()) {
            Url savedUrl = optionalUrl.get();
            if (getTimeDifferenceInMinutes(savedUrl.getTimestamp()) > 5) {
                savedUrl.setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
                return urlRepository.save(savedUrl);
            }
            throw new InvalidUrlException("short url for this url already exists - " + resourceRefer + savedUrl.getShortenedUrl());

        }
        return createShortUrl(url);
    }

}
