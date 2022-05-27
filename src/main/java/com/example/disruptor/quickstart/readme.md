
使用
- 建立一个工厂Event类，用来创建Event类实例对象

```
OrderEvent,OrderEventFactory
```

- 需要有一个监听事件类，用来处理数据（Event类）
```
OrderEventHandler
```
- 实例化Disruptor实例，配置一系列参数，编写Disruptor核心组件

```
Main


        // 1 实例化disruptor对象
        OrderEventFactory factory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(factory, ringBufferSize, executorService, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 2 添加消费者的监听
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3 启动disruptor
        disruptor.start();


```
- 编写生产者组件，向Disruptor容器中去投递数据

```
         // 4 ringBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (int i = 0; i < 10; i++) {
            byteBuffer.putLong(0, i);
            producer.sendData(byteBuffer);
        }
```