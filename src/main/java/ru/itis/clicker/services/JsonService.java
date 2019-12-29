package ru.itis.clicker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.itis.clicker.models.Ball;
import ru.itis.clicker.models.Player;

import static java.lang.Math.random;

public class JsonService {
    private ObjectMapper mapper = new ObjectMapper();

    public JsonNode dispatch(String json) {
        try {
            JsonNode getNode = mapper.readTree(json);
            ObjectNode postNode = mapper.createObjectNode();
            switch (getNode.path("header").asText()) {
                case "move": {
                    postNode = game(getNode.path("payload"));
                    break;
                } case "exit": {
                    postNode.put("header", "exit");
                    break;
                }
            }
            return postNode;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private ObjectNode game(JsonNode payload) {
        ObjectNode node = mapper.createObjectNode();
        int score = payload.path("payload").path("player").path("score").asInt() + (int) (random() * 18);
        if (score >= 100) {
            node.put("header", "finished");
            node.put("winner", payload.path("payload").path("player").path("name").asText());
            return node;
        } else {
            node.put("header", "game");
            Player player = new Player(payload.path("payload").path("player").path("name").asText(), score);
            node.put("player", mapper.valueToTree(player));
            double x = 0;
            double y = 0;
            while (!(x > 25 && x < 965 && y > 25 && y < 565)) {
                x = random() * 1000;
                y = random() * 600;
            }
            Ball ball = new Ball(x, y);
            node.put("ball", mapper.valueToTree(ball));
            return node;
        }
    }

    public JsonNode start() {
        ObjectNode node = mapper.createObjectNode();
        node.put("header", "start");
        double x = 0;
        double y = 0;
        while (!(x > 25 && x < 965 && y > 25 && y < 565)) {
            x = random() * 1000;
            y = random() * 600;
        }
        Ball ball = new Ball(x, y);
        node.put("ball", mapper.valueToTree(ball));
        return node;
    }
}
