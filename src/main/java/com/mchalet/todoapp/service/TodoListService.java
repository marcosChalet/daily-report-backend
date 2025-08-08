package com.mchalet.todoapp.service;

import java.util.*;

import com.mchalet.todoapp.repositories.TodoListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchalet.todoapp.dtos.TodoListDTO;
import com.mchalet.todoapp.model.TodoListModel;

@Service
public class TodoListService {
	
	@Autowired
    TodoListRepository todoListRepository;

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
	
    public TodoListModel createTodoList(TodoListDTO todoListDTO) {
        var todoListModel = new TodoListModel();
        BeanUtils.copyProperties(todoListDTO, todoListModel);
        return todoListRepository.save(todoListModel);
    }

    public List<TodoListModel> getAllTodoLists() {
        return todoListRepository.findAll();
    }

    public Optional<TodoListModel> getTodoListById(Integer id) {
        return todoListRepository.findById(id);
    }

    public TodoListModel updateTodoList(Integer id, TodoListDTO todoListDTO) {
        TodoListModel todoList = todoListRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No value present"));
        BeanUtils.copyProperties(todoListDTO, todoList, getNullPropertyNames(todoListDTO));
        return todoListRepository.save(todoList);
    }

    public void deleteTodoList(Integer id) {
        TodoListModel todoList = todoListRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        todoListRepository.delete(todoList);
    }
}
