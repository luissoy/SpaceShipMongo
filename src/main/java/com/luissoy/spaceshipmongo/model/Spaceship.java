package com.luissoy.spaceshipmongo.model;

import com.luissoy.spaceshipmongo.dto.SpaceshipDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "spaceships")
public class Spaceship {
    @Id
    private Long id;

    private String name;

    public Spaceship(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Spaceship(Long id, SpaceshipDto spaceshipDto) {
        this.id = id;
        this.name = spaceshipDto.getName();
    }
}