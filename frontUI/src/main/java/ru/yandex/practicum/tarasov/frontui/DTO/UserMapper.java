package ru.yandex.practicum.tarasov.frontui.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.tarasov.frontui.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /*@Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthDate", target = "birthDate")*/
    User dtoToUser(UserDto userDto);
}
