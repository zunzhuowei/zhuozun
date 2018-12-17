package com.qs.game.config;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.EventEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zun.wei on 2018/12/17 15:49.
 * Description: https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started
 */
@Slf4j
public class DisruptorConfig {


    public static class SocketMessageEvent {

        private EventEntity eventEntity;

        public void setEvent(EventEntity eventEntity) {
            this.eventEntity = eventEntity;
        }

        public EventEntity getEvent() {
            return eventEntity;
        }
    }


    public static class SocketMessageEventFactory implements EventFactory<SocketMessageEvent> {

        @Override
        public SocketMessageEvent newInstance() {
            return new SocketMessageEvent();
        }
    }


    public static class SocketMessageEventTranslator implements EventTranslatorOneArg<SocketMessageEvent, EventEntity> {

        @Override
        public void translateTo(SocketMessageEvent socketMessageEvent, long sequence, EventEntity event) {
            socketMessageEvent.setEvent(event);
        }
    }

    public static class SocketMessageThreadFactory implements ThreadFactory {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Disruptor #" + mCount.getAndIncrement());
        }
    }

    public static class SocketMessageEventHandler implements EventHandler<SocketMessageEvent> {

        @Override
        public void onEvent(SocketMessageEvent socketMessageEvent, long sequence, boolean endOfBatch) throws Exception {
            EventEntity eventEntity = socketMessageEvent.getEvent();
            Event event = eventEntity.getEvent();
            EvenType evenType = eventEntity.getEvenType();

            String sid = event.getSid();

            Handler handler = Handler.getInstance(event, true);
            if (Objects.isNull(handler)) {
                try {
                    event.getSysWebSocket().closeWebSocket(sid);
                } catch (IOException e) {
                    log.warn("MessageRouter route executeRouteMessage handler closeWebSocket 2", e.getMessage());
                    //e.printStackTrace();
                }
                log.warn("MessageRouter route executeRouteMessage handler is null,sid:{}", sid);
                return;
            }

            try {
                switch (evenType) {
                    case ON_OPEN:
                        handler.getOpenEvenHandler().handler(event);
                        break;
                    case ON_CLOSE:
                        handler.getCloseEvenHandler().handler(event);
                        break;
                    case ON_STR_MESSAGE: {
                        handler.getStrEvenHandler().handler(event);
                        break;
                    }
                    case ON_BYTE_MESSAGE: {
                        handler.getBinaryEvenHandler().handler(event);
                        break;
                    }
                    case ON_PONE_MESSAGE:
                        handler.getPongEvenHandler().handler(event);
                        break;
                    case ON_ERROR_MESSAGE:
                        handler.getErrorEvenHandler().handler(event);
                        break;
                    default:
                        throw new RuntimeException("no event error !");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class SocketMessageExceptionHandler implements ExceptionHandler<SocketMessageEvent> {

        @Override
        public void handleEventException(Throwable ex, long sequence, SocketMessageEvent event) {
            ex.printStackTrace();
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            ex.printStackTrace();
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            ex.printStackTrace();
        }
    }


    public static class SocketMessageEventProducer {

        private RingBuffer<SocketMessageEvent> ringBuffer;

        public SocketMessageEventProducer(RingBuffer<SocketMessageEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void onData(EventEntity eventEntity) {
            EventTranslatorOneArg<SocketMessageEvent, EventEntity> translator = new SocketMessageEventTranslator();
            ringBuffer.publishEvent(translator, eventEntity);
        }

    }

    private static SocketMessageEventProducer socketMessageEventProducer = null;


    public static SocketMessageEventProducer getSocketMessageProducer() {
        if (Objects.isNull(socketMessageEventProducer)) {
            int ringBufferSize = 1024;//必须是2的N次方

            Disruptor<SocketMessageEvent> disruptor = new Disruptor<SocketMessageEvent>(
                    new SocketMessageEventFactory(),
                    ringBufferSize,
                    new SocketMessageThreadFactory(),
                    ProducerType.SINGLE,
                    new BlockingWaitStrategy()
            );

            disruptor.handleEventsWith(new SocketMessageEventHandler());
            disruptor.setDefaultExceptionHandler(new SocketMessageExceptionHandler());
            RingBuffer<SocketMessageEvent> ringBuffer = disruptor.start();
            socketMessageEventProducer = new SocketMessageEventProducer(ringBuffer);
        }
        return socketMessageEventProducer;
    }

}
