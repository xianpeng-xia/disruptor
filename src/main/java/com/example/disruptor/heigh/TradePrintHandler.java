package com.example.disruptor.heigh;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;

public class TradePrintHandler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("print...");
        System.out.println("Trade = " + event.toString());
    }

}
