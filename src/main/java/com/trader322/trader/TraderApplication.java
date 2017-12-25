package com.trader322.trader;

import com.trader322.trader.ChatBot.BotMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TraderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraderApplication.class, args);
	}

	@Bean
	public BotMessageListener botMessageListener(){
		BotMessageListener bot = new BotMessageListener();
		bot.InitBot();
		return bot;
	}

}
