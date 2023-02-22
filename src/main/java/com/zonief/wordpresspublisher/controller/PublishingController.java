package com.zonief.wordpresspublisher.controller;

import com.zonief.wordpresspublisher.beans.Article;
import com.zonief.wordpresspublisher.service.PublishingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publisher")
@RequiredArgsConstructor
public class PublishingController {

  private final PublishingService publishingService;

  @PostMapping("/publish")
  public void publish(@RequestBody Article article) {
    publishingService.publish(article);
  }

}
