package com.konradmikolaj.boardgamelibrarymanager.repository;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
