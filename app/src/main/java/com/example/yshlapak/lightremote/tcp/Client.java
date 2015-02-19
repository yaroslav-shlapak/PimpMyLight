package com.example.yshlapak.lightremote.tcp;

/**
 * Created by y.shlapak on Jan 26, 2015.
 */

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    final String hostName;
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
                        Socket socket = new Socket(hostName, portNumber);
                        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
                ) {
                    String toServerString;
                    Gson json = new Gson();
                    toServerString = json.toJson(lightControlJson);
                    toServer.writeUTF(toServerString);

                } catch (UnknownHostException e) {
                    System.err.println("Don't know about host " + hostName);
                    System.exit(1);
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to " +
                            hostName);
                    System.exit(1);
                }
            }
        }.start();

    }

    public LightControlJson receive() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try (
                        Socket socket = new Socket(hostName, portNumber);
                        DataInputStream fromServer = new DataInputStream(socket.getInputStream());
                ) {
                    String fromServerString;
                    Gson json = new Gson();

                    fromServerString = fromServer.readUTF();
                    lightControlJson = json.fromJson(fromServerString, LightControlJson.class);
                } catch (UnknownHostException e) {
                    System.err.println("Don't know about host " + hostName);
                    System.exit(1);
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to " +
                            hostName);
                    System.exit(1);
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
