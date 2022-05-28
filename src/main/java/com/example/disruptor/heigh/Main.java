package com.example.disruptor.heigh;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 构造一个线程池用于提交任务
        ExecutorService workExecutor = Executors.newFixedThreadPool(4);
        // 1 构造Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(new TradeEventFactory(),
                1024 * 1024,
                Executors.newFixedThreadPool(4),
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        // 2 把消费者设置到Disruptor中，

        // 2.1 串行操作
       /*
       disruptor.handleEventsWith(new TradeSetNameHandler())
                .handleEventsWith(new TradeSetIdHandler())
                .handleEventsWith(new TradePrintHandler());
        */

        // 2.2 并行操作
        disruptor.handleEventsWith(new TradeSetNameHandler(), new TradeSetIdHandler(), new TradePrintHandler());
        // disruptor.handleEventsWith(new TradeSetIdHandler());
        // disruptor.handleEventsWith(new TradePrintHandler());


        // 3 启动Disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        workExecutor.submit(new TradePushlisher(disruptor, countDownLatch));

        countDownLatch.await();
        disruptor.shutdown();
        workExecutor.shutdown();
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }
}
