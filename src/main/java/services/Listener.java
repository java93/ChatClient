package services;

import controllers.ChatController;
import controllers.LoginController;
import jdk.internal.util.xml.impl.Input;
import messages.Message;
import messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Listener implements Runnable {

    private static final String HASCONNECTED = "has connected";

    private Socket socket;
    private String hostname;
    private int port;
    private static String username;
    private ChatController controller;
    private static ObjectOutputStream oos;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;

    public Listener(String hostname, int port,String username, ChatController controller) {
        this.hostname = hostname;
        this.port = port;
        this.controller = controller;
        Listener.username = username;
    }

    public void run() {

        try {
            socket = new Socket(hostname, port);
            LoginController.getInstance().showScene();
            os = socket.getOutputStream();
            is = socket.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            LoginController.getInstance().showErrorDialog("Could not connect to server");
            e.printStackTrace();
        }

        try {
            connect();
            while (socket.isConnected()) {
                Message message;
                message = (Message) ois.readObject();

                if (Objects.nonNull(message)) {
                    switch (message.getType()) {
                        case USER:
                            controller.addToChat(message);
                            break;
                        case CONNECTED:
                        case DISCONNECTED:
                            controller.setUserList(message);
                            break;
                        case SERVER:
//                            controller.addAsServer(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /*This method is first message to server for connecting*/
    private void connect() throws IOException {
        Message message = new Message();
        message.setName(username);
        message.setType(MessageType.CONNECTED);
        message.setMsg(HASCONNECTED);

        oos.writeObject(message);
    }
}
