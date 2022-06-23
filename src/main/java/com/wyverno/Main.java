package com.wyverno;

import com.wyverno.server.Server;

import java.io.*;
import java.net.InetSocketAddress;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server(new InetSocketAddress(47));
        server.start();
        System.out.println("Server is start");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String str = null;
            System.out.println("Print \"close\" if you want close program");
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
