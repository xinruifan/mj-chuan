package com.mj.mjchuan.application.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class EventLatchManager {

    // 使用线程安全的 ConcurrentHashMap 存储每个事件对应的回执状态
    private final ConcurrentHashMap<String, EventStatus> eventStatusMap = new ConcurrentHashMap<>();

    // 事件状态类，用于存储事件的客户端数量和已收到回执的数量
    public static class EventStatus {
        private final int totalClients;
        private int receivedAcks;

        public EventStatus(int totalClients) {
            this.totalClients = totalClients;
            this.receivedAcks = 0;
        }

        public synchronized void acknowledge() {
            this.receivedAcks++;
        }

        public synchronized boolean isCompleted() {
            return receivedAcks >= totalClients;
        }

        public synchronized int getRemainingAcks() {
            return totalClients - receivedAcks;
        }
    }

    // 创建新的事件并设置客户端数量
    public void createEvent(String eventId, int totalClients) {
        eventStatusMap.put(eventId, new EventStatus(totalClients));
    }

    // 收到回执时调用
    public void acknowledgeEvent(String eventId) {
        EventStatus eventStatus = eventStatusMap.get(eventId);
        if (eventStatus != null) {
            eventStatus.acknowledge();  // 增加已收到回执的数量
        }
    }

    // 检查事件是否完成（是否已收到所有回执）
    public boolean isEventCompleted(String eventId) {
        EventStatus eventStatus = eventStatusMap.get(eventId);
        return eventStatus != null && eventStatus.isCompleted();
    }

    // 获取剩余回执数
    public int getRemainingAcks(String eventId) {
        EventStatus eventStatus = eventStatusMap.get(eventId);
        return eventStatus != null ? eventStatus.getRemainingAcks() : 0;
    }
}
