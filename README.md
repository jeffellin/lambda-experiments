```
CREATE EXTERNAL FORMAT IF NOT EXISTS jeffs3storage_lambda_odl_user_1317086 
TYPE csv WITH (delimiter ',', skip_blank_lines 'true');
CREATE EXTERNAL STORAGE IF NOT EXISTS jeffs3storage_lambda_odl_user_1317086 
TYPE s3 ENDPOINT 'https://s3.us-east-1.amazonaws.com' REGION 'us-east-1' 
IDENTITY '<access key>' CREDENTIAL '<secret>';
CREATE EXTERNAL LOCATION IF NOT EXISTS jeffs3storage_lambda_odl_user_1317086
PATH 'jellin-s3-sql' EXTERNAL STORAGE jeffs3storage_lambda_odl_user_1317086 

```

```sql

CREATE TABLE demo.cars (
    brand character varying(255),
    model character varying(255),
    year integer
)
DISTRIBUTE ON (brand);

LOAD TABLE demo.cars FROM ('demo/cars/cars.csv') EXTERNAL LOCATION "jeffs3storage_lambda_odl_user_1317086" EXTERNAL FORMAT "jeffs3storage_lambda_odl_user_1317086" WITH    (read_sources_concurrently 'ALLOW', num_readers '2')

select * from cars



```