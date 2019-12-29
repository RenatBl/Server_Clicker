package ru.itis.clicker.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ball {
    private Double x;
    private Double y;
    private Double size;

    public Ball(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{\"x\":" + x + ",\"y\":" + y + ",\"size\":" + size + "}";
    }
}
