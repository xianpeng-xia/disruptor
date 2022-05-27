package com.example.disruptor.quickstart;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {
    public static void main(String[] args) {

        // 1 实例化disruptor对象
        OrderEventFactory factory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(factory, ringBufferSize, executorService, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 2 添加消费者的监听
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3 启动disruptor
        disruptor.start();

        // 4 ringBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (int i = 0; i < 10; i++) {
            byteBuffer.putLong(0, i);
            producer.sendData(byteBuffer);
        }

        disruptor.shutdown();
        executorService.shutdown();
    }
}
