package ru.aston.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.dto.requestDTO.RequestHouseDTO;
import ru.aston.dto.responseDTO.ResponseHouseDTO;
import ru.aston.entity.House;
import ru.aston.exeption.EntityNotFoundExeption;
import ru.aston.mapper.HouseMapper;
import ru.aston.repository.Repository;
import ru.aston.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceimpl implements HouseService {

    private Repository<House> repository;
    private HouseMapper mapper;

    @Override
    public List<ResponseHouseDTO> findByAll(int pageNumber, int pageSize) {
        return repository.findByAll(pageNumber, pageSize).stream()
                .map(house -> mapper.toResponseHouseDTO((House) house))
                .toList();
    }

    @Override
    public ResponseHouseDTO findByUUID(UUID uuid) throws Throwable {
        return repository.findByUUID(uuid)
                .map(house -> mapper.toResponseHouseDTO((House) house))
                .orElseThrow(() -> new EntityNotFoundExeption("Object not found", UUID.class));
    }

    @Override
    @Transactional
    public UUID create(RequestHouseDTO requestHouseDTO) {
        House house = mapper.toHouse(requestHouseDTO);
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());

        return repository.create(house);
    }

    @Override
    @Transactional
    public void update(RequestHouseDTO requestHouseDTO, UUID uuid) {
        repository.findByUUID(uuid).ifPresent(house -> {
            house.setArea(requestHouseDTO.getArea());
            house.setCountry(requestHouseDTO.getCountry());
            house.setCity(requestHouseDTO.getCity());
            house.setStreet(requestHouseDTO.getStreet());
            house.setNumber(requestHouseDTO.getNumber());

            repository.update(house);
        });
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        repository.delete(uuid);
    }
}
