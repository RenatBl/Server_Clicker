package ru.itis.clicker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.itis.clicker.models.Ball;
import ru.itis.clicker.models.Player;

public class m {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("a", "a");
        ObjectNode nodes = mapper.createObjectNode();
        nodes.put("b", node);
        System.out.println(nodes);
        JsonNode n = nodes;
        System.out.println(nodes.path("b").path("a").asText());
    }
}
