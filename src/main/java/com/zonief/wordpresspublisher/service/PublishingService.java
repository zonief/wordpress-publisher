package com.zonief.wordpresspublisher.service;

import com.zonief.wordpresspublisher.beans.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishingService {

  private final WebClient wordpressClient;

  public void publish(Article article) {
    log.info("Publishing {}...",article.getTitle());
    try {
      wordpressClient.post()
          .uri(uriBuilder -> uriBuilder
              .path("/wp-json/wp/v2/posts")
              .queryParam("title", article.getTitle())
              .queryParam("content", article.getContent())
              .queryParam("status", "publish")
              .build())
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (Exception e) {
      log.error("Error while publishing article {}.", article.getTitle(), e);
    }
    log.info("Published.");
  }

}
