package com.wyverno.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.wyverno.client.Client;
import com.wyverno.response.Response;
import com.wyverno.server.DataBase;
import com.wyverno.server.Server;
import org.java_websocket.WebSocket;

import java.io.IOException;

public class RegistrationEvent extends AbstractEvent {

    public RegistrationEvent(Server server, JsonNode jsonNode, WebSocket webSocket, int requestID) {
        super(server, jsonNode, webSocket, requestID);
    }

    @Override
    public void runEvent() {
        DataBase dataBase = this.server.getDataBase();

        try {

            Response response;

            if (dataBase.isFreeName(this.jsonNode.get("nickname").asText())) {
                Client client = new Client(this.jsonNode.get("nickname").asText(), this.jsonNode.get("password").asText());
                dataBase.addClient(client);
                response = new Response(this.requestID, Response.Type.registration, 0, "OK");
                System.out.println("Client is registred! Username: " + client.getUsername() + " Password: " + client.getPassword());
            } else {
                response = new Response(this.requestID, Response.Type.registration, 1, "Bad name");
                System.out.println("Client choose is exists name: " + this.jsonNode.get("nickname").asText());
            }

            this.webSocket.send(response.toJSON());
            System.out.println("Sending to socket");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
