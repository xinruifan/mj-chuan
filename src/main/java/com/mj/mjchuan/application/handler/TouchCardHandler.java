package com.mj.mjchuan.application.handler;

/**
 * @author xinruifan
 * @create 2025-01-09 18:44
 */
public class TouchCardHandler extends AbstractHandler {


    @Override
    public void setNextNode(BaseHandler nextNode) {
        this.nextHandler = nextNode;
    }

    @Override
    public void handleRequest(Object request) {

    }
}
