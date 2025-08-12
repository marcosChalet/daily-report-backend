package com.mchalet.dailyreport.service;

import com.mchalet.dailyreport.model.TaskListModel;
import com.mchalet.dailyreport.repositories.TaskListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchalet.dailyreport.dtos.TaskDTO;
import com.mchalet.dailyreport.model.TaskModel;
import com.mchalet.dailyreport.repositories.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {
	
	@Autowired
    TaskRepository taskRepository;
	@Autowired
    TaskListRepository taskListRepository;

	public TaskModel createTask(Integer listId, TaskDTO taskDTO) {
		TaskListModel taskList = taskListRepository
                .findById(listId)
                .orElseThrow(() -> new NoSuchElementException("The list does not exist"));

		var task = new TaskModel();
		BeanUtils.copyProperties(taskDTO, task);
        taskList.getTasks().add(task);
        List<TaskModel> allTasks = taskListRepository.save(taskList).getTasks();
        return allTasks
                .stream()
                .reduce((f, s) -> s)
                .orElseThrow(() -> new NoSuchElementException("The task does not created"));
	}

	public TaskModel updateTask(UUID id, TaskDTO taskDTO) {
		TaskModel task = taskRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task does not exist"));

		BeanUtils.copyProperties(taskDTO, task);
        return taskRepository.save(task);
	}

    public void deleteTask(UUID id) {
        TaskModel task = taskRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task does not exist"));

        taskRepository.delete(task);
    }

    public TaskModel getTaskById(UUID id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task does not exist"));
    }
}
