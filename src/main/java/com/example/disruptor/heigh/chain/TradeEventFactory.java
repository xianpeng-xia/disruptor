package com.example.disruptor.heigh.chain;

public class TradeEventFactory implements com.lmax.disruptor.EventFactory<Trade> {
    @Override
    public Trade newInstance() {
        return new Trade();
    }
}
