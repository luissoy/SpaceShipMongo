package com.luissoy.spaceshipmongo.repository;

import com.luissoy.spaceshipmongo.model.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends MongoRepository<Spaceship, Long> {
    Page<Spaceship> findByNameContaining(String name, Pageable pageable);
}
