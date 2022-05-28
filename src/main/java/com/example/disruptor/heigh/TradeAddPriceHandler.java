package com.example.disruptor.heigh;

import com.lmax.disruptor.EventHandler;

public class TradeAddPriceHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("add  price...");
        event.setPrice(event.getPrice() + 5D);
    }

}
