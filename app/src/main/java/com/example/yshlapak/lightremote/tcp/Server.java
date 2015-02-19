package com.example.yshlapak.lightremote.tcp;

/**
 * Created by y.shlapak on Jan 26, 2015.
 */

import com.example.yshlapak.lightremote.json.LightControlJson;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    final int portNumber;

    public LightControlJson getLightControlJson() {
        return lightControlJson;
    }

    public void setLightControlJson(LightControlJson lightControlJson) {
        this.lightControlJson = lightControlJson;
    }

    private LightControlJson lightControlJson;
    public void send(LightControlJson lightControlJsonIn) {
        lightControlJson = lightControlJsonIn;

        new Thread() {
            @Override
            public void run() {
                try (
                        ServerSocket serverSocket = new ServerSocket(portNumber);
                        Socket clientSocket = serverSocket.accept();
                        DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());
                ) {

                    String toClientString;
                    Gson json = new Gson();

                    toClientString = json.toJson(lightControlJson);
                    toClient.writeUTF(toClientString);
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port "
                            + portNumber + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        }.start();
    }

    public LightControlJson receive() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try (
                        ServerSocket serverSocket = new ServerSocket(portNumber);
                        Socket clientSocket = serverSocket.accept();
                        DataInputStream fromClient = new DataInputStream(clientSocket.getInputStream());
                ) {

                    String fromClientString;
                    fromClientString = fromClient.readUTF();
                    Gson json = new Gson();
                    lightControlJson = json.fromJson(fromClientString, LightControlJson.class);
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port "
                            + portNumber + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lightControlJson;
    }
}
