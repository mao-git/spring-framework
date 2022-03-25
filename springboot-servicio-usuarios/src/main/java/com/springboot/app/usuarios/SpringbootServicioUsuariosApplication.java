package com.springboot.app.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.javafaker.Faker;
import com.springboot.app.usuarios.entities.User;

@SpringBootApplication
public class SpringbootServicioUsuariosApplication implements ApplicationRunner {
	
	@Autowired
	private Faker faker;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			users.add(user);
		}
		
		for (User user : users) {
			System.out.println("username: " + user.getUsername());
		}
		
		users.stream().filter(user -> user.getUsername().startsWith("e")).forEach(System.out::println);
		
		Optional<Integer> producto = Stream.of(5, 6, 83, 43, 9).sorted().reduce((a, b) -> a * b);
		
		System.out.println("el producto final es: " + producto.get());
		
		List<Integer> collect = Stream.of(5, 6, 83, 43, 9).filter(nro -> nro > 10).collect(Collectors.toList());
		
		for (Integer element : collect) {
			System.out.println("element: " + element);
		}
	}

}
