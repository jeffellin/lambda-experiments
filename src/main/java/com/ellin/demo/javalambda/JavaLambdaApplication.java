package com.ellin.demo.javalambda;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import com.amazonaws.services.lambda.runtime.serialization.events.LambdaEventSerializers;

import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class JavaLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaLambdaApplication.class, args);
	}
	@Bean
	public Function<S3Event, String> uppercase(SQLImportManager sqlImportManager) {



		//S3_BUCKET = event['Records'][0]['s3']['bucket']['name']
		//mkey = event['Records'][0]['s3']['object']['key']

		//path = mkey.split('/')
		//tname = path[len(path)-2]
		//sname = path[len(path)-3]

		log.info("......WE GOT HERE YAY");
		//template.store("df");
		return value -> {
			log.info("Received event {}",value);
			String bucket = value.getRecords().get(0).getS3().getBucket().getName();
			String key = value.getRecords().get(0).getS3().getObject().getKey();
			String[] path = key.split("/");
			String tName = path[path.length-1];
			//String sName = path[path.length-3];
			//log.info("processing record {}:{}:{}",key,tName,sName);
            try {
                sqlImportManager.store(key,bucket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "processed";

		};
	}
}
