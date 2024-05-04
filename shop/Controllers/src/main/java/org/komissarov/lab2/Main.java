package org.komissarov.lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories("org.komissarov.shop.da.repositories")
@EntityScan("org.komissarov.shop.da.entity")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("1"));
    }
}