package ru.yandex.practicum.tarasov.frontui.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.tarasov.frontui.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return restTemplate.getForObject("http://accounts/user/" + username, User.class);
    }
}
