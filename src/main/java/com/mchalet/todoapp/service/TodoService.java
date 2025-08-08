package com.mchalet.todoapp.service;

import com.mchalet.todoapp.model.TodoListModel;
import com.mchalet.todoapp.repositories.TodoListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mchalet.todoapp.dtos.TodoDTO;
import com.mchalet.todoapp.model.TodoModel;
import com.mchalet.todoapp.repositories.TodoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {
	
	@Autowired
    TodoRepository todoRepository;
	@Autowired
    TodoListRepository todoListRepository;

	public TodoModel createTodo(Integer listId, TodoDTO todoDTO) {
		TodoListModel todoList = todoListRepository
                .findById(listId)
                .orElseThrow(() -> new NoSuchElementException("The list does not exist"));

		var todo = new TodoModel();
		BeanUtils.copyProperties(todoDTO, todo);
        todoList.getTodos().add(todo);
        List<TodoModel> allTodos = todoListRepository.save(todoList).getTodos();
        return allTodos
                .stream()
                .reduce((f, s) -> s)
                .orElseThrow(() -> new NoSuchElementException("The todo does not created"));
	}

	public TodoModel updateTodo(UUID id, TodoDTO todoDTO) {
		TodoModel todo = todoRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo does not exist"));

		BeanUtils.copyProperties(todoDTO, todo);
        return todoRepository.save(todo);
	}

    public void deleteTodo(UUID id) {
        TodoModel todo = todoRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo does not exist"));

        todoRepository.delete(todo);
    }

    public TodoModel getTodoById(UUID id) {
        return todoRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo does not exist"));
    }
}
