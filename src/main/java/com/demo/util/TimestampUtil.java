package com.demo.util;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TimestampUtil {
    public Timestamp currentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
    public Timestamp oneMinuteBackTimestamp(){
        return new Timestamp(System.currentTimeMillis() - 6000);
    }
}
