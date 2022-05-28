package com.example.disruptor.heigh.handler;

import java.util.UUID;

import com.example.disruptor.heigh.Trade;
import com.lmax.disruptor.EventHandler;

public class TradeSetIdHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("setId...");
        Thread.sleep(2000);
        String id = UUID.randomUUID().toString().replace("-","");
        event.setId(id);
    }

}
