package com.luissoy.spaceshipmongo.model;

import com.luissoy.spaceshipmongo.dto.SpaceshipDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spaceships")
public class Spaceship {
    @Id
    private Long id;

    private String name;

    public Spaceship(Long id, SpaceshipDto spaceshipDto) {
        this.id = id;
        this.name = spaceshipDto.getName();
    }
}