package com.example.disruptor.heigh.multi;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Main")
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 1 创建ringBuf
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI,
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },
                1024 * 1024,
                new YieldingWaitStrategy()

        );

        // 2 通过ringBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3 创建多个多消费者组
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }
        // 4 构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers
        );
        // 5  设置多个消费者sequence序号，用于单独统计消费进度，并设置到RingBuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6 启动 workPool
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                for (int j = 0; j < 10; j++) {
                    producer.sendData(UUID.randomUUID().toString());
                }
            }).start();
        }

        Thread.sleep(2000);
        log.error("---线程创建完毕,开始生产数据---");
        countDownLatch.countDown();
        Thread.sleep(10000);
        log.error("任务总数:{}", consumers[2].getCount());
    }

    static class EventExceptionHandler implements ExceptionHandler<Order> {

        @Override
        public void handleEventException(Throwable throwable, long seq, Order event) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}
