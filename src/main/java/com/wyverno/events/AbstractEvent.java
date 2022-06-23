
package com.wyverno.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.wyverno.server.Server;
import org.java_websocket.WebSocket;

import java.io.IOException;

public abstract class AbstractEvent {

    protected final Server server;
    protected final JsonNode jsonNode;
    protected final WebSocket webSocket;
    protected final int requestID;

    public AbstractEvent(Server server, JsonNode jsonNode, WebSocket webSocket, int requestID) {
        this.server = server;
        this.jsonNode = jsonNode;
        this.webSocket = webSocket;
        this.requestID = requestID;
    }

    public abstract void runEvent();
}
