package com.ellin.demo.javalambda;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class StoreData {

    private String txn_id;
    private int store_id;
    private int amount;
    private Timestamp timestamp;

}
