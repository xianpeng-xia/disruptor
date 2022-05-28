package com.example.disruptor.heigh.chain.handler;

import com.example.disruptor.heigh.chain.Trade;
import com.lmax.disruptor.EventHandler;

public class TradeResetPriceHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("4 reset price...");
        event.setPrice(0D);
    }

}
