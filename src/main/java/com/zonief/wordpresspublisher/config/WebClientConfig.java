package com.zonief.wordpresspublisher.config;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
@Slf4j
public class WebClientConfig {

  private static final String USERNAME = "wordpress-publisher";
  private static final String PASSWORD = "y8mg wGSC QPb2 6vSm Qysm lkW9";
  @Value("${webclient.baseUrl}")
  private String baseUrl;

  @Bean
  public WebClient webClient() throws SSLException {
    log.info("Creating webClient with baseUrl: {}", baseUrl);
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    SslContext sslContext = SslContextBuilder
        .forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build();
    var httpClient = HttpClient.create()
        .wiretap("reactor.netty.http.client.HttpClient",
            LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
        .secure(t -> t.sslContext(sslContext));
    return WebClient.builder()
        .defaultHeaders(header -> header.setBasicAuth(USERNAME, PASSWORD))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
        .baseUrl(baseUrl)
        .exchangeStrategies(strategies)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

}
