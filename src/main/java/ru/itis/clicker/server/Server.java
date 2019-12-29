package ru.itis.clicker.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.clicker.models.Player;
import ru.itis.clicker.services.JsonService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private List<ClientHandler> clients;
    private static final int PORT = 1234;
    private int quantity;

    public Server(int quantity) {
        this.quantity = quantity;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void start() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        while (true) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
    private class ClientHandler extends Thread{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private ObjectMapper mapper = new ObjectMapper();
        private Player player;

        ClientHandler(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try {
                JsonService service = new JsonService();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                while (clients.size() < quantity) {
                    clients.add(this);
                    System.out.println("New user connection");
                    String s = in.readLine();
                    JsonNode node = mapper.readTree(s);
                    this.player = getPlayer(node.path("payload").toString());
                }
                if (clients.size() == quantity) {
                    notifyClients(service.start().toString());
                    String json;
                    while ((json = in.readLine()) != null) {
                        System.out.println(json);
                        if (service.dispatch(json).path("header").asText().equals("exit")) {
                            stopConnection();
                            clients.remove(this);
                            break;
                        } else {
                            notifyClients(service.dispatch(json).asText());
                        }
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private void stopConnection() throws IOException {
            System.out.println("Connection stopped");
            this.client.close();
            in.close();
            out.close();
        }

        private Player getPlayer(String s) {
            try {
                return new Player(mapper.readTree(s).path("player").path("name").asText(),
                        mapper.readTree(s).path("player").path("score").asInt());
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
        }

        private void notifyClients(String inputLine) throws IOException {
            System.out.println("Notify " + inputLine);
            for (ClientHandler client : clients) {
                PrintWriter out = new PrintWriter(client.client.getOutputStream(), true);
                out.println(inputLine);
            }
        }
    }
}
