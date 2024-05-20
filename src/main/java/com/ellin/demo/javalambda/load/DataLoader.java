package com.ellin.demo.javalambda.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class DataLoader {

    public void load() {

        for (int i=0; i<50005;i++) {
            addItem();
        }

    }

    int getRandomInt(int max){
        int min = 0;
        return  new Random().nextInt(max - min + 1) + min;
    }

    int getRandomInt(int min, int max){
         return  new Random().nextInt(max - min + 1) + min;
    }

    private void addItem() {

      //template.update("insert into items(txn_id,store_id,item_num,department,order_date,ship_date)"+
       //
        //
        //       "VALUES (?,?,?,?, ?,?);", UUID.randomUUID(),1,1,1,getOrderDate(),new java.sql.Timestamp(System.currentTimeMillis()) );

        //template.update("insert into order_data (txn_id,store_id,amount,order_date)" +
         //       "VALUES (?,?,?,?)",
          //      UUID.randomUUID(),getRandomInt(10),
          //      getRandomInt(5000,8000),
           //     new java.sql.Timestamp(System.currentTimeMillis()));

        String newRole = UUID.randomUUID().toString()+","+getRandomInt(10)+","+getRandomInt(5000,8000)+","+new java.sql.Timestamp(System.currentTimeMillis())+"\n";
        try {
            apppendFile("/tmp/foo.csv",newRole);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static Timestamp getOrderDate(){
            Instant tdo = Instant.now().minus(Duration.ofDays(7));
            return Timestamp.from(between(tdo, Instant.now()));
        }
        public static Instant between(Instant startInclusive, Instant endExclusive) {
            long startSeconds = startInclusive.getEpochSecond();
            long endSeconds = endExclusive.getEpochSecond();
            long random = ThreadLocalRandom
                    .current()
                    .nextLong(startSeconds, endSeconds);
            return Instant.ofEpochSecond(random);
        }

        public static void apppendFile(String file, String content) throws IOException {

            // append to the file
            try (FileWriter fw = new FileWriter(file, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }


        }
}
