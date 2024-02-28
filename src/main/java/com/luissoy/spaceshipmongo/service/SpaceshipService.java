package com.luissoy.spaceshipmongo.service;

import com.luissoy.spaceshipmongo.exception.DataAutoIdException;
import com.luissoy.spaceshipmongo.exception.DataNotFoundException;
import com.luissoy.spaceshipmongo.model.Spaceship;
import com.luissoy.spaceshipmongo.dto.SpaceshipDto;
import com.luissoy.spaceshipmongo.repository.SpaceshipRepository;
import com.luissoy.spaceshipmongo.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;


    public PageResponse<Spaceship> getAll(Pageable pageable) {
        return new PageResponse<>(spaceshipRepository.findAll(pageable));
    }

    public Spaceship getOne(Long id) throws DataNotFoundException {
        Optional<Spaceship> spaceship = spaceshipRepository.findById(id);

        if (spaceship.isEmpty()) {
            throw new DataNotFoundException();
        } else {
            return spaceship.get();
        }
    }

    public PageResponse<Spaceship> getAllByName(String name, Pageable pageable) {
        return new PageResponse<>(spaceshipRepository.findByNameContaining(name, pageable));
    }

    public Spaceship save(SpaceshipDto dto) throws DataAutoIdException {
        Long id = autoIncrement();
        Spaceship spaceship = new Spaceship(id, dto);
        return spaceshipRepository.save(spaceship);
    }

    public Spaceship update(Long id, SpaceshipDto dto) throws DataNotFoundException {
        if (!spaceshipRepository.existsById(id)) {
            throw new DataNotFoundException();
        }

        return spaceshipRepository.save(new Spaceship(id, dto));
    }

    public void delete(Long id) throws DataNotFoundException {
        if (!spaceshipRepository.existsById(id)) {
            throw new DataNotFoundException();
        }

        spaceshipRepository.deleteById(id);
    }

    // private methods
    private Long autoIncrement() throws DataAutoIdException {
        try {
            List<Spaceship> spaceships = spaceshipRepository.findAll();
            return spaceships.isEmpty()? 1 :
                    spaceships.stream().max(Comparator.comparing(Spaceship::getId)).get().getId() + 1;
        } catch (Exception e) {
            throw new DataAutoIdException(e.getMessage());
        }
    }
}