package com.mj.mjchuan.application.cache;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Component
public class EventLatchManager<T> {

    // 使用线程安全的 ConcurrentHashMap 存储每个事件对应的回执状态
    private final ConcurrentHashMap<String, EventStatus> eventStatusMap = new ConcurrentHashMap<>();

    // 事件状态类，用于存储事件的客户端数量和已收到回执的数量
    public class EventStatus {
        private final int totalClients;
        private final List<T> receivedAcks;  // 存储每个客户端的回执信息

        public EventStatus(int totalClients) {
            this.totalClients = totalClients;
            this.receivedAcks = Collections.synchronizedList(new ArrayList<>());  // 线程安全的列表
        }

        public synchronized void acknowledge(T clientId) {
            // 如果回执信息未满，添加客户端回执
            if (!receivedAcks.contains(clientId)) {
                receivedAcks.add(clientId);
            }
        }

        public synchronized boolean isCompleted() {
            // 如果已收到的回执数量大于等于客户端数量，则事件完成
            return receivedAcks.size() >= totalClients;
        }

        public synchronized List<T> getReceivedAcks() {
            return new ArrayList<>(receivedAcks);  // 返回回执信息的副本
        }

        public synchronized int getRemainingAcks() {
            return totalClients - receivedAcks.size();  // 剩余回执数
        }
    }

    // 创建新的事件并设置客户端数量
    public void createEvent(String eventId, int totalClients) {
        eventStatusMap.put(eventId, new EventStatus(totalClients));
    }

    // 收到回执时调用
    public void acknowledgeEvent(String eventId, T clientId) {
        EventStatus eventStatus = eventStatusMap.get(eventId);
        if (eventStatus != null) {
            eventStatus.acknowledge(clientId);  // 增加客户端的回执信息
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

    // 获取回执的详细信息
    public List<T> getReceivedAcks(String eventId) {
        EventStatus eventStatus = eventStatusMap.get(eventId);
        return eventStatus != null ? eventStatus.getReceivedAcks() : Collections.emptyList();
    }

    // 当事件回执完成后，进行处理并清理该事件数据
    public void clearEvent(String eventId) {
        eventStatusMap.remove(eventId);
    }

}
