package com.example.disruptor.heigh.chain.handler;

import com.example.disruptor.heigh.chain.Trade;
import com.lmax.disruptor.EventHandler;

public class TradeAddPriceHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("5 add price...");
        event.setPrice(event.getPrice() + 5D);
    }

}
