package com.luissoy.spaceshipmongo.controller;

import com.luissoy.spaceshipmongo.dto.SpaceshipDto;
import com.luissoy.spaceshipmongo.exception.DataAutoIdException;
import com.luissoy.spaceshipmongo.exception.DataNotFoundException;
import com.luissoy.spaceshipmongo.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(path="/api/v1/spaceships")
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).
                body(spaceshipService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).
                body(spaceshipService.getOne(id));
    }

    @GetMapping("names/{name}")
    public ResponseEntity<?> getAllByName(@PathVariable("name") String name, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).
                body(spaceshipService.getAllByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody SpaceshipDto spaceshipDto) throws DataAutoIdException {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(spaceshipService.save(spaceshipDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SpaceshipDto spaceshipDto) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).
                body(spaceshipService.update(id, spaceshipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws DataNotFoundException {
        spaceshipService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
