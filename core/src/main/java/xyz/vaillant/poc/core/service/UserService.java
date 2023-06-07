package xyz.vaillant.poc.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.vaillant.poc.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
