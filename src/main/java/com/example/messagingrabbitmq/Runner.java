package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Введите сообщение: ");
			String message = scanner.next();
			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.exchangeName, "foo.bar.baz", message);
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		}
	}
}

