package com.mchalet.todoapp.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.mchalet.todoapp.dtos.TodoListDTO;
import com.mchalet.todoapp.model.TodoListModel;
import com.mchalet.todoapp.service.TodoListService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoListController {
	
	@Autowired
    TodoListService todoListService;

    @PostMapping("/todolists")
    public ResponseEntity<EntityModel<TodoListModel>> createTodoList(@RequestBody @Valid TodoListDTO todoListDTO) {

        TodoListModel created = todoListService.createTodoList(todoListDTO);
        EntityModel<TodoListModel> response = EntityModel.of(created);

        Integer listId = created.getId();
        response.add(linkTo(methodOn(TodoListController.class).getTodoListById(listId)).withRel("self"));
        response.add(linkTo(methodOn(TodoListController.class).updateTodoList(listId, null)).withRel("update"));
        response.add(linkTo(methodOn(TodoListController.class).deleteTodoList(listId)).withRel("delete"));
        response.add(linkTo(methodOn(TodoListController.class).getAllTodoLists()).withRel("todolists"));

        return ResponseEntity
                .created(linkTo(methodOn(TodoListController.class).getTodoListById(listId)).toUri())
                .body(response);
    }

    @GetMapping("/todolists")
    public ResponseEntity<CollectionModel<EntityModel<TodoListModel>>> getAllTodoLists() {
        List<TodoListModel> todoLists = todoListService.getAllTodoLists();

        List<EntityModel<TodoListModel>> todoListModels = todoLists.stream()
                .map(todoList -> EntityModel.of(todoList,
                        linkTo(methodOn(TodoListController.class).getTodoListById(todoList.getId())).withRel("todos")
                ))
                .toList();

        CollectionModel<EntityModel<TodoListModel>> collectionModel = CollectionModel.of(
                todoListModels,
                linkTo(methodOn(TodoListController.class).getAllTodoLists()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/todolists/{listId}")
    public ResponseEntity<EntityModel<TodoListModel>> getTodoListById(@PathVariable Integer listId) {

        return todoListService.getTodoListById(listId)
                .map(todoList -> {
                    EntityModel<TodoListModel> response = EntityModel.of(todoList);

                    response.add(linkTo(methodOn(TodoListController.class).getTodoListById(listId)).withSelfRel());
                    response.add(linkTo(methodOn(TodoListController.class).getAllTodoLists()).withRel("todolists"));
                    response.add(linkTo(methodOn(TodoListController.class).updateTodoList(listId, null)).withRel("update"));
                    response.add(linkTo(methodOn(TodoListController.class).deleteTodoList(listId)).withRel("delete"));
                    response.add(linkTo(methodOn(TodoListController.class).createTodoList(null)).withRel("create"));
                    response.add(linkTo(methodOn(TodoController.class).createTodo(listId, null)).withRel("add-todo"));

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/todolists/{listId}")
    public ResponseEntity<EntityModel<TodoListModel>> updateTodoList(@PathVariable Integer listId,
                                                 @RequestBody @Valid TodoListDTO todoListDTO) {

        TodoListModel updated = todoListService.updateTodoList(listId, todoListDTO);
        EntityModel<TodoListModel> response = EntityModel.of(updated);

        response.add(linkTo(methodOn(TodoListController.class).getTodoListById(listId)).withRel("self"));
        response.add(linkTo(methodOn(TodoListController.class).deleteTodoList(listId)).withRel("delete"));
        response.add(linkTo(methodOn(TodoListController.class).getAllTodoLists()).withRel("todolists"));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/todolists/{listId}")
    public ResponseEntity<Void> deleteTodoList(@PathVariable Integer listId) {
        todoListService.deleteTodoList(listId);
        return ResponseEntity.noContent().build();
    }
}
