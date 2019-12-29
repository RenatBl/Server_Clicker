package ru.itis.clicker.program;

import com.beust.jcommander.JCommander;
import ru.itis.clicker.server.Server;
import ru.itis.clicker.services.Args;

public class Main {
    public static void main(String[] args) {
        Args args1 = new Args();
        JCommander jCommander = new JCommander(args1);
        jCommander.parse(args);
        Server server = new Server(args1.getQuantity());
        server.start();
    }
}
