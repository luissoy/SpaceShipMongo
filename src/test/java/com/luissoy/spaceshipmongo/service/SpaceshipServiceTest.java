package com.luissoy.spaceshipmongo.service;

import com.luissoy.spaceshipmongo.dto.SpaceshipDto;
import com.luissoy.spaceshipmongo.exception.DataNotFoundException;
import com.luissoy.spaceshipmongo.model.Spaceship;
import com.luissoy.spaceshipmongo.repository.SpaceshipRepository;
import com.luissoy.spaceshipmongo.response.PageResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    @Test
    void getAll() {
        // Given
        List<Spaceship> spaceshipsList = new ArrayList<>();
        spaceshipsList.add(new Spaceship(1L, "Spaceship1"));
        spaceshipsList.add(new Spaceship(2L, "Spaceship2"));
        spaceshipsList.add(new Spaceship(3L, "Spaceship3"));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Spaceship> spaceshipsPage = new PageImpl<>(spaceshipsList, pageRequest, spaceshipsList.size());

        // When
        when(spaceshipRepository.findAll(pageRequest)).thenReturn(spaceshipsPage);

        // Then
        PageResponse<Spaceship> retrievedSpaceships = spaceshipService.getAll(pageRequest);
        assertEquals(3, retrievedSpaceships.getSize());
        assertEquals(1, retrievedSpaceships.getPages());
        assertEquals(3, retrievedSpaceships.getCollection().size());
    }

    @Test
    void getAll_NoContent() {
        // Given
        List<Spaceship> spaceshipsList = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Spaceship> spaceshipsPage = new PageImpl<>(spaceshipsList, pageRequest, spaceshipsList.size());

        // When
        when(spaceshipRepository.findAll(pageRequest)).thenReturn(spaceshipsPage);

        // Then
        PageResponse<Spaceship> retrievedSpaceships = spaceshipService.getAll(pageRequest);
        assertEquals(0, retrievedSpaceships.getSize());
        assertEquals(0, retrievedSpaceships.getPages());
        assertEquals(0, retrievedSpaceships.getCollection().size());
    }

    @Test
    @SneakyThrows
    void getOne() {
        // Given
        Long id = 1L;
        Optional<Spaceship> spaceship = Optional.of(new Spaceship(1L, "Spaceship1"));

        // When
        when(spaceshipRepository.findById(id)).thenReturn(spaceship);

        // Then
        Spaceship retrievedSpaceship = spaceshipService.getOne(id);
        assertEquals("Spaceship1", retrievedSpaceship.getName());
        assertEquals(1L, retrievedSpaceship.getId());
    }

    @Test
    @SneakyThrows
    void getOne_NoExistsId() {
        // Given
        Long id = 1L;
        Optional<Spaceship> spaceship = Optional.empty();

        // When
        when(spaceshipRepository.findById(id)).thenReturn(spaceship);

        // Then
        assertThrows(DataNotFoundException.class, () -> spaceshipService.getOne(1L));
    }

    @Test
    void getAllByName() {
        // Given
        List<Spaceship> spaceshipsList = new ArrayList<>();
        spaceshipsList.add(new Spaceship(1L, "Spaceship1"));
        spaceshipsList.add(new Spaceship(2L, "Spaceship2"));
        spaceshipsList.add(new Spaceship(3L, "Spaceship3"));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Spaceship> spaceshipsPage = new PageImpl<>(spaceshipsList, pageRequest, spaceshipsList.size());

        String name = "Spaceship";

        // When
        when(spaceshipRepository.findByNameContaining(name, pageRequest)).thenReturn(spaceshipsPage);

        // Then
        PageResponse<Spaceship> retrievedSpaceships = spaceshipService.getAllByName("Spaceship", pageRequest);
        assertEquals(3, retrievedSpaceships.getSize());
        assertEquals(1, retrievedSpaceships.getPages());
        assertEquals(3, retrievedSpaceships.getCollection().size());
    }

    @Test
    void getAllByName_NoContent() {
        // Given
        List<Spaceship> spaceshipsList = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Spaceship> spaceshipsPage = new PageImpl<>(spaceshipsList, pageRequest, spaceshipsList.size());

        String name = "Spaceship";

        // When
        when(spaceshipRepository.findByNameContaining(name, pageRequest)).thenReturn(spaceshipsPage);

        // Then
        PageResponse<Spaceship> retrievedSpaceships = spaceshipService.getAllByName("Spaceship", pageRequest);
        assertEquals(0, retrievedSpaceships.getSize());
        assertEquals(0, retrievedSpaceships.getPages());
        assertEquals(0, retrievedSpaceships.getCollection().size());
    }

    @Test
    @SneakyThrows
    void save_EmptyDb() {
        // Given
        SpaceshipDto dto = new SpaceshipDto("Spaceship1");
        List<Spaceship> spaceshipsList = new ArrayList<>();
        Long id = spaceshipsList.size() + 1L;
        Spaceship spaceship = new Spaceship(id, dto);

        // When
        ArgumentCaptor<Spaceship> argument = ArgumentCaptor.forClass(Spaceship.class);
        when(spaceshipRepository.findAll()).thenReturn(spaceshipsList);
        when(spaceshipRepository.save(argument.capture())).thenReturn(spaceship);

        // Then
        Spaceship savedSpaceship = spaceshipService.save(dto);
        assertEquals("Spaceship1", savedSpaceship.getName());
        assertEquals("Spaceship1", argument.getValue().getName());
        assertEquals(1L, savedSpaceship.getId());
        assertEquals(1L, argument.getValue().getId());
    }

    @Test
    @SneakyThrows
    void save_NoEmptyDb() {
        // Given
        SpaceshipDto dto = new SpaceshipDto("Spaceship3");
        List<Spaceship> spaceshipsList = new ArrayList<>();
        spaceshipsList.add(new Spaceship(1L, "Spaceship1"));
        spaceshipsList.add(new Spaceship(2L, "Spaceship2"));
        Long id = spaceshipsList.size() + 1L;
        Spaceship spaceship = new Spaceship(id, dto);

        // When
        ArgumentCaptor<Spaceship> argument = ArgumentCaptor.forClass(Spaceship.class);
        when(spaceshipRepository.findAll()).thenReturn(spaceshipsList);
        when(spaceshipRepository.save(argument.capture())).thenReturn(spaceship);

        // Then
        Spaceship savedSpaceship = spaceshipService.save(dto);
        assertEquals("Spaceship3", savedSpaceship.getName());
        assertEquals("Spaceship3", argument.getValue().getName());
        assertEquals(3L, savedSpaceship.getId());
        assertEquals(3L, argument.getValue().getId());
    }

    @Test
    @SneakyThrows
    void update() {
        // Given
        Long id = 1L;
        SpaceshipDto dto = new SpaceshipDto("Spaceship1");
        Spaceship spaceship = new Spaceship(id, dto);

        // When
        ArgumentCaptor<Spaceship> argument = ArgumentCaptor.forClass(Spaceship.class);
        when(spaceshipRepository.existsById(id)).thenReturn(true);
        when(spaceshipRepository.save(argument.capture())).thenReturn(spaceship);

        // Then
        Spaceship updatedSpaceship = spaceshipService.update(id, dto);
        assertEquals("Spaceship1", updatedSpaceship.getName());
        assertEquals("Spaceship1", argument.getValue().getName());
        assertEquals(1L, updatedSpaceship.getId());
        assertEquals(1L, argument.getValue().getId());
    }

    @Test
    @SneakyThrows
    void update_NoExistsId() {
        // Given
        Long id = 1L;
        SpaceshipDto dto = new SpaceshipDto("Spaceship1");

        // When
        when(spaceshipRepository.existsById(id)).thenReturn(false);

        // Then
        assertThrows(DataNotFoundException.class, () -> spaceshipService.update(id, dto));
    }


    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        when(spaceshipRepository.existsById(id)).thenReturn(true);

        // Then
        assertDoesNotThrow(() -> spaceshipService.delete(id));
    }

    @Test
    void delete_NoExistsId() {
        // Given
        Long id = 1L;

        // When
        when(spaceshipRepository.existsById(id)).thenReturn(false);

        // Then
        assertThrows(DataNotFoundException.class, () -> spaceshipService.delete(id));
    }
}