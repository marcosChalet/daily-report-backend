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
public class TodoController {

	@Autowired
    TodoService todoService;

	@PostMapping("/todos/{listId}")
	public ResponseEntity<EntityModel<TodoModel>> createTodo(@PathVariable Integer listId,
                                                             @RequestBody @Valid TodoDTO todoDTO) {
		TodoModel created = todoService.createTodo(listId, todoDTO);
        EntityModel<TodoModel> model = EntityModel.of(created);

        UUID todoId = created.getId();
        model.add(linkTo(methodOn(TodoController.class).getTodoById(todoId)).withSelfRel());
        model.add(linkTo(methodOn(TodoController.class).updateTodo(todoId, null)).withRel("update"));
        model.add(linkTo(methodOn(TodoController.class).deleteTodo(todoId)).withRel("delete"));

        return ResponseEntity
                .created(linkTo(methodOn(TodoController.class).getTodoById(todoId)).toUri())
                .body(model);
	}

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<EntityModel<TodoModel>> getTodoById(@PathVariable UUID id) {

        TodoModel todo = todoService.getTodoById(id);
        EntityModel<TodoModel> model = EntityModel.of(todo);

        model.add(linkTo(methodOn(TodoController.class).getTodoById(id)).withRel("self"));
        model.add(linkTo(methodOn(TodoController.class).deleteTodo(id)).withRel("delete"));
        model.add(linkTo(methodOn(TodoController.class).updateTodo(id, null)).withRel("update"));

        return ResponseEntity.ok(model);
    }

	@PutMapping("/todos/{todoId}")
	public ResponseEntity<EntityModel<TodoModel>> updateTodo(@PathVariable UUID id,
											 @RequestBody @Valid TodoDTO todoDTO) {

		TodoModel updated = todoService.updateTodo(id, todoDTO);

        EntityModel<TodoModel> model = EntityModel.of(updated);
        model.add(linkTo(methodOn(TodoController.class).getTodoById(id)).withRel("self"));
        model.add(linkTo(methodOn(TodoController.class).deleteTodo(id)).withRel("delete"));

        return ResponseEntity.ok(model);
	}

	@DeleteMapping("/todos/{todoId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable UUID todoId) {
		todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
}
