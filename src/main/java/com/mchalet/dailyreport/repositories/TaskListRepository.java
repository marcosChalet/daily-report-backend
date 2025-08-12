package com.mchalet.dailyreport.repositories;

import com.mchalet.dailyreport.model.TaskListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListModel, Integer> { }
