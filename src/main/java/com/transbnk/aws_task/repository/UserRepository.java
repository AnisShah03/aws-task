package com.transbnk.aws_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transbnk.aws_task.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    
}
