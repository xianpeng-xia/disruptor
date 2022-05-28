package com.example.disruptor.heigh.chain;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

public class TradePushlisher implements Runnable {
    private Disruptor disruptor;
    private CountDownLatch countDownLatch;

    private static int PUBLISH_COUNT = 1;

    public TradePushlisher(Disruptor disruptor, CountDownLatch countDownLatch) {
        this.disruptor = disruptor;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        TradeTranslator tradeTranslator = new TradeTranslator();
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            disruptor.publishEvent(tradeTranslator);
        }
        countDownLatch.countDown();
    }

    class TradeTranslator implements EventTranslator<Trade> {
        private Random random = new Random();

        @Override
        public void translateTo(Trade event, long sequence) {
            this.generateTrade(event);
        }

        private void generateTrade(Trade event) {

            event.setPrice(random.nextDouble() * 9999);
        }
    }
}
