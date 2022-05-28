package com.example.disruptor.heigh;

public class TradeEventFactory implements com.lmax.disruptor.EventFactory<Trade> {
    @Override
    public Trade newInstance() {
        return new Trade();
    }
}
