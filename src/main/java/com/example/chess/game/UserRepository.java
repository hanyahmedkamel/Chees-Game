package com.example.chess.game;

import com.example.chess.game.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<UserModel,Integer> {

    UserModel findByEmail(String email);




}
