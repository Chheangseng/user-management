package com.tcs.user_auth_management.config.security;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebOnStartConfig implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    System.out.println(
        "------------ "
            + "Application start up time: "
            + event.getTimeTaken().getSeconds()
            + " S ------------");
  }
}
