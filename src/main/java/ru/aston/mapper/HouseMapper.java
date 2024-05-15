package ru.aston.mapper;

import org.mapstruct.Mapper;
import ru.aston.dto.requestDTO.RequestHouseDTO;
import ru.aston.dto.responseDTO.ResponseHouseDTO;
import ru.aston.entity.House;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    ResponseHouseDTO toResponseHouseDTO(House house);

    House toHouse(RequestHouseDTO requestHouseDTO);
}
