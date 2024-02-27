package com.luissoy.spaceshipmongo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceshipDto {
    private String name;

    public SpaceshipDto(String name) {
        this.name = name;
    }
}
