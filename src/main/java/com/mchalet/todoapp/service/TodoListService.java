package com.mchalet.todoapp.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.mchalet.todoapp.repositories.TodoListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchalet.todoapp.dtos.TodoListDTO;
import com.mchalet.todoapp.model.TodoListModel;

@Service
public class TodoListService {
	
	@Autowired
    TodoListRepository todoListRepository;
	
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
        BeanUtils.copyProperties(todoListDTO, todoList);
        return todoList;
    }

    public void deleteTodoList(Integer id) {
        TodoListModel todoList = todoListRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        todoListRepository.delete(todoList);
    }
}
