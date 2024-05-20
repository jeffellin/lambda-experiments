package com.ellin.demo.javalambda.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.Data;

public class CLIDataLoader  {


    public static void main(String[] args) {

        CLIDataLoader cdl = new CLIDataLoader();
        cdl.run(args);

    }

    public void run(String... args) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.load();
    }
}
