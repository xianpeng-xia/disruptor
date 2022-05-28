package com.example.disruptor.heigh.handler;

import com.example.disruptor.heigh.Trade;
import com.lmax.disruptor.EventHandler;

public class TradeResetPriceHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("reset  price...");
        event.setPrice(0D);
    }

}
