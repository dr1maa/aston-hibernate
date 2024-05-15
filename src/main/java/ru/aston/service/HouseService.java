package ru.aston.service;

import org.springframework.transaction.annotation.Transactional;
import ru.aston.dto.requestDTO.RequestHouseDTO;
import ru.aston.dto.responseDTO.ResponseHouseDTO;

import java.util.UUID;

public interface HouseService extends Services<RequestHouseDTO, ResponseHouseDTO> {

    @Transactional
    UUID create(RequestHouseDTO requestHouseDTO);
}
