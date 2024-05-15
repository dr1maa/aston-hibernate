package ru.aston.service;

import org.springframework.transaction.annotation.Transactional;
import ru.aston.dto.requestDTO.RequestPersonDTO;
import ru.aston.dto.responseDTO.ResponsePersonDTO;

import java.util.UUID;

public interface PersonService extends Services<RequestPersonDTO, ResponsePersonDTO> {

    @Transactional
    void create(RequestPersonDTO requestPersonDTO, UUID uuid);
}
