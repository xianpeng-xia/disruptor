package com.example.disruptor.heigh.chain;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Trade {
    private String id;
    private String name;
    private double price;
    private AtomicInteger count = new AtomicInteger(0);
}
