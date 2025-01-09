package com.mj.mjchuan.application.handler;

/**
 * @author xinruifan
 * @create 2025-01-09 18:40
 */
public interface BaseHandler {

    void setNextNode(BaseHandler nextNode);

    void handleRequest(Object request);

}
