package com.example.disruptor.heigh.multi;

import com.lmax.disruptor.RingBuffer;

public class Producer {
    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String uuid) {
        // 1 生产着发送消息的时候，先从ringBuffer中获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            // 2 根据这个序号找到对应的Evnet
            Order order = ringBuffer.get(sequence);
            // 3 进行实际的赋值
            order.setId(uuid);
        } finally {
            // 4 提交发布操作
            ringBuffer.publish(sequence);
        }
    }

}
