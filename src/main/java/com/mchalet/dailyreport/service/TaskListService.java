package com.mchalet.dailyreport.service;

import java.util.*;

import com.mchalet.dailyreport.repositories.TaskListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchalet.dailyreport.dtos.TaskListDTO;
import com.mchalet.dailyreport.model.TaskListModel;

@Service
public class TaskListService {
	
	@Autowired
    TaskListRepository taskListRepository;

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
	
    public TaskListModel createTaskList(TaskListDTO taskListDTO) {
        var taskListModel = new TaskListModel();
        BeanUtils.copyProperties(taskListDTO, taskListModel);
        return taskListRepository.save(taskListModel);
    }

    public List<TaskListModel> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public Optional<TaskListModel> getTaskListById(Integer id) {
        return taskListRepository.findById(id);
    }

    public TaskListModel updateTaskList(Integer id, TaskListDTO taskListDTO) {
        TaskListModel taskList = taskListRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No value present"));
        BeanUtils.copyProperties(taskListDTO, taskList, getNullPropertyNames(taskListDTO));
        return taskListRepository.save(taskList);
    }

    public void deleteTaskList(Integer id) {
        TaskListModel taskList = taskListRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        taskListRepository.delete(taskList);
    }
}
