package com.uvic.venus.repository;

import com.uvic.venus.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SecretDAO extends JpaRepository<Secret, String> {

    List<Secret> findAllByUsername(String username);
}
