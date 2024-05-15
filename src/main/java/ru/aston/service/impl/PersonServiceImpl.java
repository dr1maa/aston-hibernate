package ru.aston.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.dto.requestDTO.RequestPersonDTO;
import ru.aston.dto.responseDTO.ResponsePersonDTO;
import ru.aston.entity.House;
import ru.aston.entity.Person;
import ru.aston.exeption.EntityNotFoundExeption;
import ru.aston.mapper.PersonMapper;
import ru.aston.repository.Repository;
import ru.aston.service.PersonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private Repository<House> houseRepository;
    private Repository<Person> repository;
    private PersonMapper mapper;

    @Override
    public List<ResponsePersonDTO> findByAll(int pageNumber, int pageSize) {
        return repository.findByAll(pageNumber, pageSize).stream()
                .map(person -> mapper.toResponsePersonDto((Person) person))
                .toList();
    }

    @Override
    public ResponsePersonDTO findByUUID(UUID uuid) throws Throwable {
        return repository.findByUUID(uuid)
                .map(person -> mapper.toResponsePersonDto((Person) person))
                .orElseThrow(() -> new EntityNotFoundExeption("Object not found", UUID.class));
    }

    @Override
    @Transactional
    public void create(RequestPersonDTO requestPersonDTO, UUID uuid) {
        houseRepository.findByUUID(uuid).ifPresent(house -> {
            Person person = mapper.toPerson(requestPersonDTO);
            person.setUuid(UUID.randomUUID());
            person.setCreateDate(LocalDateTime.now());
            person.setUpdateDate(person.getCreateDate());
            person.setHouse(house);
            repository.update(person);
        });
    }

    @Override
    @Transactional
    public void update(RequestPersonDTO requestPersonDTO, UUID uuid) {
        repository.findByUUID(uuid).ifPresent(person -> {
            person.setName(requestPersonDTO.getName());
            person.setSurname(requestPersonDTO.getSurname());
            person.setSex(requestPersonDTO.getSex());
            person.setPassportSeries(requestPersonDTO.getPassportSeries());
            person.setPassportNumber(requestPersonDTO.getPassportNumber());
            person.setUpdateDate(LocalDateTime.now());

            repository.update(person);
        });

    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        repository.delete(uuid);
    }
}
