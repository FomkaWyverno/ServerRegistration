package com.wyverno;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyverno.server.Server;

import java.io.*;
import java.net.InetSocketAddress;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server(new InetSocketAddress(47));
        server.start();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String str = null;

            while ((str = reader.readLine()) != null) {
                if (str.equals("close")) {
                    server.close();
                    System.out.println("Closing");
                    break;
                }
            }
        }
    }
}
