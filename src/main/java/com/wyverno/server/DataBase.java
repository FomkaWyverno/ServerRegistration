package com.wyverno.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.wyverno.client.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final File dataBaseFile = new File("database.json");



    private final List<Client> clientsBase;

    public DataBase() throws IOException {
        List<Client> clients;

        try (BufferedReader reader = new BufferedReader(new FileReader(dataBaseFile))) {
            StringBuilder sb = new StringBuilder();

            while (reader.ready()) {
                sb.append(reader.readLine());
            }

            clients = objectMapper.readValue(sb.toString(), new TypeReference<List<Client>>() {});
        } catch (MismatchedInputException e) {
            System.out.println("Data base is empty");
            clients = new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("Data base is not exists!!!");
            clients = new ArrayList<>();
        }

        this.clientsBase = clients;
    }

    public void addClient(Client client) throws IOException {
        this.clientsBase.add(client);
    }

    public Client getClient(String name) {
        for (Client client : clientsBase) {
            if (client.getUsername().equals(name)) {
                return client;
            }
        }
        return null;
    }

    public boolean isFreeName(String name) {
        name = name.toLowerCase();
        for (Client client : clientsBase) {
            if (client.getUsername().toLowerCase().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public void close() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataBaseFile))) {
            writer.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.clientsBase));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DataBase{" +
                "clients=" + clientsBase +
                '}';
    }
}
