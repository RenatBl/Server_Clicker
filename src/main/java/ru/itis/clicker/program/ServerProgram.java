package ru.itis.clicker.program;

import ru.itis.clicker.server.Server;

public class ServerProgram {
    public static void main(String[] args) {
        Server server = new Server(1);
        server.start();
    }
}
