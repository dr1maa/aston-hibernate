package ru.aston.mapper;

import org.mapstruct.Mapper;
import ru.aston.dto.requestDTO.RequestPersonDTO;
import ru.aston.dto.responseDTO.ResponsePersonDTO;
import ru.aston.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    ResponsePersonDTO toResponsePersonDto(Person person);

    Person toPerson(RequestPersonDTO requestPersonDTO);
}
