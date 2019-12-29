package ru.itis.clicker.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String name;
    private Integer score;

    @Override
    public String toString() {
        return "{\"name\":\"" + name + "\",\"score\":" + score + "}";
    }
}
