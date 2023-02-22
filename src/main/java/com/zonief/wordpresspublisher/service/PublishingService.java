package com.zonief.wordpresspublisher.service;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Content;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import com.zonief.wordpresspublisher.beans.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishingService {

  private final Wordpress wordpressClient;

  public void publish(Article article) {
    log.info("Publishing...");
    Post post = new Post();
    var title = new Title();
    title.setRaw(article.getTitle());
    post.setTitle(title);
    var content = new Content();
    content.setRaw(article.getContent());
    post.setContent(content);
    try {
      wordpressClient.createPost(post, PostStatus.pending);
    } catch (PostCreateException e) {
      log.error("Error while publishing the article", e);
    }
  }

}
