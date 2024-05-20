package com.ellin.demo.javalambda.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CLIDataLoader implements CommandLineRunner {

    @Autowired
    DataLoader dataLoader;

    public static void main(String[] args) {
        SpringApplication.run(CLIDataLoader.class, args);
    }

    @Override
    public void run(String... args) {
        dataLoader.load();
    }
}
