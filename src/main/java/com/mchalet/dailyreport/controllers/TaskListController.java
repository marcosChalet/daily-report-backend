package com.mchalet.dailyreport.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.mchalet.dailyreport.dtos.TaskListDTO;
import com.mchalet.dailyreport.model.TaskListModel;
import com.mchalet.dailyreport.service.TaskListService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskListController {
	
	@Autowired
    TaskListService taskListService;

    @PostMapping("/tasklists")
    public ResponseEntity<EntityModel<TaskListModel>> createTaskList(@RequestBody @Valid TaskListDTO taskListDTO) {

        TaskListModel created = taskListService.createTaskList(taskListDTO);
        EntityModel<TaskListModel> response = EntityModel.of(created);

        Integer listId = created.getId();
        response.add(linkTo(methodOn(TaskListController.class).getTaskListById(listId)).withRel("self"));
        response.add(linkTo(methodOn(TaskListController.class).updateTaskList(listId, null)).withRel("update"));
        response.add(linkTo(methodOn(TaskListController.class).deleteTaskList(listId)).withRel("delete"));
        response.add(linkTo(methodOn(TaskListController.class).getAllTaskLists()).withRel("tasklists"));

        return ResponseEntity
                .created(linkTo(methodOn(TaskListController.class).getTaskListById(listId)).toUri())
                .body(response);
    }

    @GetMapping("/tasklists")
    public ResponseEntity<CollectionModel<EntityModel<TaskListModel>>> getAllTaskLists() {
        List<TaskListModel> taskLists = taskListService.getAllTaskLists();

        List<EntityModel<TaskListModel>> taskListModels = taskLists.stream()
                .map(taskList -> EntityModel.of(taskList,
                        linkTo(methodOn(TaskListController.class).getTaskListById(taskList.getId())).withRel("tasks")
                ))
                .toList();

        CollectionModel<EntityModel<TaskListModel>> collectionModel = CollectionModel.of(
                taskListModels,
                linkTo(methodOn(TaskListController.class).getAllTaskLists()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/tasklists/{listId}")
    public ResponseEntity<EntityModel<TaskListModel>> getTaskListById(@PathVariable Integer listId) {

        return taskListService.getTaskListById(listId)
                .map(taskList -> {
                    EntityModel<TaskListModel> response = EntityModel.of(taskList);

                    response.add(linkTo(methodOn(TaskListController.class).getTaskListById(listId)).withSelfRel());
                    response.add(linkTo(methodOn(TaskListController.class).getAllTaskLists()).withRel("tasklists"));
                    response.add(linkTo(methodOn(TaskListController.class).updateTaskList(listId, null)).withRel("update"));
                    response.add(linkTo(methodOn(TaskListController.class).deleteTaskList(listId)).withRel("delete"));
                    response.add(linkTo(methodOn(TaskListController.class).createTaskList(null)).withRel("create"));
                    response.add(linkTo(methodOn(TaskController.class).createTask(listId, null)).withRel("add-task"));

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/tasklists/{listId}")
    public ResponseEntity<EntityModel<TaskListModel>> updateTaskList(@PathVariable Integer listId,
                                                 @RequestBody @Valid TaskListDTO taskListDTO) {

        TaskListModel updated = taskListService.updateTaskList(listId, taskListDTO);
        EntityModel<TaskListModel> response = EntityModel.of(updated);

        response.add(linkTo(methodOn(TaskListController.class).getTaskListById(listId)).withRel("self"));
        response.add(linkTo(methodOn(TaskListController.class).deleteTaskList(listId)).withRel("delete"));
        response.add(linkTo(methodOn(TaskListController.class).getAllTaskLists()).withRel("tasklists"));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasklists/{listId}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Integer listId) {
        taskListService.deleteTaskList(listId);
        return ResponseEntity.noContent().build();
    }
}
