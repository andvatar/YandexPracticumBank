package ru.yandex.practicum.tarasov.accounts.DTO;

import org.mapstruct.Mapper;
import ru.yandex.practicum.tarasov.accounts.entity.User;


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
