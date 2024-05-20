package com.ellin.demo.javalambda;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
        String s3Event = """
            {
                "Records": [
                  {
                    "eventVersion": "2.0",
                    "eventSource": "aws:s3",
                    "awsRegion": "us-east-1",
                    "eventTime": "1970-01-01T00:00:00.000Z",
                    "eventName": "ObjectCreated:Put",
                    "userIdentity": {
                      "principalId": "EXAMPLE"
                    },
                    "requestParameters": {
                      "sourceIPAddress": "127.0.0.1"
                    },
                    "responseElements": {
                      "x-amz-request-id": "EXAMPLE123456789",
                      "x-amz-id-2": "EXAMPLE123/5678abcdefghijklambdaisawesome/mnopqrstuvwxyzABCDEFGH"
                    },
                    "s3": {
                      "s3SchemaVersion": "1.0",
                      "configurationId": "testConfigRule",
                      "bucket": {
                        "name": "jellin-sql-s3-yb",
                        "ownerIdentity": {
                          "principalId": "EXAMPLE"
                        },
                        "arn": "arn:aws:s3:::example-bucket"
                      },
                      "object": {
                        "key": "public/order_data/bar.csv",
                        "size": 1024,
                        "eTag": "0123456789abcdef0123456789abcdef",
                        "sequencer": "0A1B2C3D4E5F678901"
                      }
                    }
                  }
                ]
              }
                """;

       // assertEquals("echo", "echo");

    @Test
    public void testS3Event() throws Exception {
        System.setProperty("MAIN_CLASS", JavaLambdaApplication.class.getName());
        System.setProperty("spring.cloud.function.definition", "uppercase");
        FunctionInvoker invoker = new FunctionInvoker();

        InputStream targetStream = new ByteArrayInputStream(this.s3Event.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        invoker.handleRequest(targetStream, output, null);

        String result = new String(output.toByteArray(), StandardCharsets.UTF_8);
        System.out.println(result);
    }
}
