package ru.yandex.practicum.tarasov.accounts.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.accounts.DTO.ChangePasswordDto;
import ru.yandex.practicum.tarasov.accounts.DTO.UserDto;
import ru.yandex.practicum.tarasov.accounts.DTO.UserMapper;
import ru.yandex.practicum.tarasov.accounts.entity.Authority;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.repository.AuthorityRepository;
import ru.yandex.practicum.tarasov.accounts.repository.UserRepository;


import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User createUser(UserDto userDto) {
        User user = userMapper.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority userAuthority = authorityRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Authority not found"));
        user.setAuthorities(Set.of(userAuthority));
        return userRepository.save(user);
    }

    public User changePassword(ChangePasswordDto changePasswordDto) {
        if(!changePasswordDto.password().equals(changePasswordDto.confirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = userRepository.findByUsername(changePasswordDto.login()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(changePasswordDto.password()));

        return userRepository.save(user);
    }
}
