package com.wisestep.urlShortener.repository;

import com.wisestep.urlShortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByShortenedUrl(final String shortenedUrl);

    Optional<Url> findByOriginalUrl(final String originalUrl);
}
