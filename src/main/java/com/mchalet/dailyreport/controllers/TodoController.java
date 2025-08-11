package com.mchalet.todoapp.controllers;

import com.mchalet.todoapp.model.TodoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mchalet.todoapp.dtos.TodoDTO;
import com.mchalet.todoapp.service.TodoService;

import jakarta.validation.Valid;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

	@Autowired
    TodoService todoService;

	@PostMapping("/todos/{listId}")
	public ResponseEntity<EntityModel<TodoModel>> createTodo(@PathVariable Integer listId,
                                                             @RequestBody @Valid TodoDTO todoDTO) {
		TodoModel created = todoService.createTodo(listId, todoDTO);
        EntityModel<TodoModel> response = EntityModel.of(created);

        UUID todoId = created.getId();
        response.add(linkTo(methodOn(TodoController.class).getTodoById(todoId)).withRel("self"));
        response.add(linkTo(methodOn(TodoController.class).updateTodo(todoId, null)).withRel("update"));
        response.add(linkTo(methodOn(TodoController.class).deleteTodo(todoId)).withRel("delete"));

        return ResponseEntity
                .created(linkTo(methodOn(TodoController.class).getTodoById(todoId)).toUri())
                .body(response);
	}

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<EntityModel<TodoModel>> getTodoById(@PathVariable UUID todoId) {

        TodoModel todo = todoService.getTodoById(todoId);
        EntityModel<TodoModel> model = EntityModel.of(todo);

        model.add(linkTo(methodOn(TodoController.class).getTodoById(todoId)).withRel("self"));
        model.add(linkTo(methodOn(TodoController.class).deleteTodo(todoId)).withRel("delete"));
        model.add(linkTo(methodOn(TodoController.class).updateTodo(todoId, null)).withRel("update"));

        return ResponseEntity.ok(model);
    }

	@PutMapping("/todos/{todoId}")
	public ResponseEntity<EntityModel<TodoModel>> updateTodo(@PathVariable UUID todoId,
											 @RequestBody @Valid TodoDTO todoDTO) {

		TodoModel updated = todoService.updateTodo(todoId, todoDTO);

        EntityModel<TodoModel> model = EntityModel.of(updated);
        model.add(linkTo(methodOn(TodoController.class).getTodoById(todoId)).withRel("self"));
        model.add(linkTo(methodOn(TodoController.class).deleteTodo(todoId)).withRel("delete"));

        return ResponseEntity.ok(model);
	}

	@DeleteMapping("/todos/{todoId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable UUID todoId) {
		todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
}
