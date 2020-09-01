package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.entity.User;
import com.github.kuzznya.isunotifier.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User findUserById(Long id) {
        return repository.getOne(id);
    }

    public void add(User user) {
        repository.save(user);
    }

    public void remove(User user) {
        repository.delete(user);
    }
}
