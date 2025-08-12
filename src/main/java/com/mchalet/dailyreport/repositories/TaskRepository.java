package com.mchalet.dailyreport.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mchalet.dailyreport.model.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> { }
