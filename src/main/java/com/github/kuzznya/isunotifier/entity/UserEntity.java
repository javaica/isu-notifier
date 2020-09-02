package com.github.kuzznya.isunotifier.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserEntity {
    @Id
    Long id;
    String username;
}
