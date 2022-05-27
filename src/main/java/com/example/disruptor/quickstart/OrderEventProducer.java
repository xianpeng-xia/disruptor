package com.example.disruptor.quickstart;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class OrderEventProducer {
    RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer data) {
        // 1 在生产者发送消息的是，从ringBuffer离获取一个可用的序号
        long sequence = ringBuffer.next();

        try {

            // 2 根据sequence找到具体的orderEvent对象（空对象）
            OrderEvent orderEvent = ringBuffer.get(sequence);

            // 3 赋值
            orderEvent.setValue(data.getLong(0));
            System.out.println("生产者: value = " + data.getLong(0) + " seq = " + sequence);

        } finally {
            // 4 提交发布操作
            ringBuffer.publish(sequence);
        }
    }


}
