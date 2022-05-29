package com.example.disruptor.heigh.multi;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private String id;
    private String name;
    private double price;
}
