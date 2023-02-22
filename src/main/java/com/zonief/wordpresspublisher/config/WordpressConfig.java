package com.zonief.wordpresspublisher.config;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordpressConfig {

 private static final String baseUrl = "https://vitefait.ovh";
 private static final String username = "ys7414";
 private static final String password = "vitefaitwelldonE";
 boolean debug = false;
 boolean usePermalinkEndpoint = false;

 @Bean
 public Wordpress wordpressClient() {
  return ClientFactory.fromConfig(
      ClientConfig.of(baseUrl, username, password, usePermalinkEndpoint, debug));
  }

}
