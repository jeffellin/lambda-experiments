package com.ellin.demo.javalambda;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

@Configuration
@Slf4j
public class SQLImportManager {

        private static final String IMPORT_SQL =
            "LOAD TABLE public.%s FROM ('%s') EXTERNAL LOCATION \"%s\" EXTERNAL FORMAT \"%s\"";

//  \"\"\" %  (sname,tname,mkey,ybexternallocation,ybexternalformat)   "
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public void readFile(String tempDir) throws Exception{
        try (CSVReader reader = new CSVReader(new FileReader(tempDir))) {
            List<String[]> r = reader.readAll();
            ArrayList<StoreData> l = new ArrayList<>();
            r.forEach(x -> {
                    l.add(createStoreData(x));
            });
            insertRecord(l);
        }
    }

    private StoreData createStoreData(String[] x) {

        return StoreData.builder()
                .txn_id(x[0])
                .store_id(Integer.parseInt(x[1]))
                .amount(Integer.parseInt(x[2]))
                .timestamp(Timestamp.valueOf(x[3])).build();
    }

    private void insertRecord(Collection<StoreData> data) throws Exception {
        // Step 1: Establishing a Connection

            jdbcTemplate.batchUpdate("INSERT INTO order_data (txn_id,store_id,amount,order_date)" +
                            "VALUES (?, ?, ?,?)",
                    data,
                    100,
                    (PreparedStatement ps, StoreData product) -> {
                        ps.setString(1, product.getTxn_id());
                        ps.setInt(2, product.getStore_id());
                        ps.setInt(3, product.getAmount());
                        ps.setTimestamp(4, product.getTimestamp());
                    });


        // Step 4: try-with-resource statement will auto close the connection.
    }
    public void store(String key, String bucket) throws Exception {

        AmazonS3Client s3 = new AmazonS3Client();
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property)+"/"+key;
        getObjectBytes(s3,bucket,key,tempDir);
        readFile(tempDir);
        }
    public static void getObjectBytes(AmazonS3Client s3, String bucketName, String keyName, String path) {
        GetObjectRequest gor = new GetObjectRequest(bucketName,keyName);
        s3.getObject(gor,new File(path));

    }
}
