package com.cipherops.OutboundRelayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutboundRelayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboundRelayServiceApplication.class, args);
	}

}
