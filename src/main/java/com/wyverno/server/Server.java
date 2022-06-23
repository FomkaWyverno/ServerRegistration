package com.wyverno.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyverno.events.AbstractEvent;
import com.wyverno.events.LoginEvent;
import com.wyverno.events.RegistrationEvent;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server extends WebSocketServer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DataBase dataBase;
    private final Thread thread;

    public Server(InetSocketAddress address, DataBase dataBase) {
        super(address);
        this.dataBase = dataBase;
        thread = new Thread(this);
    }

    public Server(InetSocketAddress address) throws IOException {
        super(address);
        this.dataBase = new DataBase();
        thread = new Thread(this);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Connection client -> " + webSocket.getRemoteSocketAddress().getHostString() + ":" + webSocket.getRemoteSocketAddress().getPort());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        try {
            System.out.println("Client message -> " + message);
            JsonNode jsonNode = objectMapper.readTree(message);

            this.processingRequest(jsonNode,webSocket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    private void processingRequest(JsonNode jsonNode, WebSocket webSocket) {
        JsonNode dataNode = jsonNode.get("data");

        switch (dataNode.get("type").asText()) {
            case "registration" : {
                AbstractEvent event = new RegistrationEvent(this,dataNode,webSocket,jsonNode.get("requestID").asInt());
                event.runEvent();
                break;
            }
            case "login" : {
                AbstractEvent event = new LoginEvent(this,dataNode,webSocket,jsonNode.get("requestID").asInt());
                event.runEvent();
                break;
            }
        }
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void start() {
        this.thread.start();
    }

    public void close() throws InterruptedException {
        this.dataBase.close();
        this.thread.interrupt();
    }
}
