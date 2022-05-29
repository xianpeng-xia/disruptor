package com.example.disruptor.heigh.multi;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer implements WorkHandler<Order> {
    private String consumerId;
    private static AtomicInteger count = new AtomicInteger(0);
    private Random random = new Random();

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        log.info("当前消费者:{},消费者信息：{}", this.consumerId, event);
        count.incrementAndGet();
    }

    public Integer getCount() {
        return count.get();
    }
}
