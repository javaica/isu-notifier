package com.github.kuzznya.isunotifier.service;

import com.github.kuzznya.isunotifier.entity.UserEntity;
import com.github.kuzznya.isunotifier.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    public UserEntity findUserById(Long id) {
        return repository.getOne(id);
    }

    public void add(UserEntity user) {
        repository.save(user);
    }

    public void remove(UserEntity user) {
        repository.delete(user);
    }
}
