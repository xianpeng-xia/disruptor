package com.example.disruptor.heigh.handler;

import com.example.disruptor.heigh.Trade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeSetNameHandler implements EventHandler<Trade>, WorkHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade event) throws Exception {
        System.out.println("setName...");
        Thread.sleep(1000);
        event.setName("H1");
    }
}
