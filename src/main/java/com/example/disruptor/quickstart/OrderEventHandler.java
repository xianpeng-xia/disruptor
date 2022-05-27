package com.example.disruptor.quickstart;

import com.lmax.disruptor.EventHandler;

public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者: value = " + event.getValue() + " seq = "+sequence+" , endOfBatch = "+endOfBatch);
    }
}
