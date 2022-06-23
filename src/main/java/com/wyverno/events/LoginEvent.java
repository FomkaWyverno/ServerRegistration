package com.wyverno.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.wyverno.client.Client;
import com.wyverno.response.Response;
import com.wyverno.server.DataBase;
import com.wyverno.server.Server;
import org.java_websocket.WebSocket;

public class LoginEvent extends AbstractEvent {

    public LoginEvent(Server server, JsonNode jsonNode, WebSocket webSocket, int requestID) {
        super(server, jsonNode, webSocket, requestID);
    }

    @Override
    public void runEvent() {
        DataBase dataBase = this.server.getDataBase();

        try {
            Response response;

            Client client = dataBase.getClient(jsonNode.get("nickname").asText());
            if (client != null) {
                if (client.getPassword().equals(jsonNode.get("password").asText())) {
                    response = new Response(this.requestID, Response.Type.login, 0, "OK");
                    System.out.println("Client is logged: Username: " + client.getUsername() + " Password: " + client.getPassword());
                } else {
                    response = new Response(this.requestID, Response.Type.login, 1, "BAD PASSWORD");
                    System.out.println("Client is not logged: Username: " + client.getUsername() + " Password: " + client.getPassword() + " Bad Password: " + this.jsonNode.get("password").asText());
                }
            } else {
                response = new Response(this.requestID, Response.Type.login, 2, "USERNAME IS NOT EXISTS");
                System.out.println("Client choose is not exists account: Name: " + this.jsonNode.get("nickname").asText());
            }

            webSocket.send(response.toJSON());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response;


    }
}
