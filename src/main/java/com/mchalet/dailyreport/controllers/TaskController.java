package com.mchalet.dailyreport.controllers;

import com.mchalet.dailyreport.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mchalet.dailyreport.dtos.TaskDTO;
import com.mchalet.dailyreport.service.TaskService;

import jakarta.validation.Valid;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

	@Autowired
    TaskService taskService;

	@PostMapping("/tasks/{listId}")
	public ResponseEntity<EntityModel<TaskModel>> createTask(@PathVariable Integer listId,
                                                             @RequestBody @Valid TaskDTO taskDTO) {
		TaskModel created = taskService.createTask(listId, taskDTO);
        EntityModel<TaskModel> response = EntityModel.of(created);

        UUID taskId = created.getId();
        response.add(linkTo(methodOn(TaskController.class).getTaskById(taskId)).withRel("self"));
        response.add(linkTo(methodOn(TaskController.class).updateTask(taskId, null)).withRel("update"));
        response.add(linkTo(methodOn(TaskController.class).deleteTask(taskId)).withRel("delete"));

        return ResponseEntity
                .created(linkTo(methodOn(TaskController.class).getTaskById(taskId)).toUri())
                .body(response);
	}

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<EntityModel<TaskModel>> getTaskById(@PathVariable UUID taskId) {

        TaskModel task = taskService.getTaskById(taskId);
        EntityModel<TaskModel> model = EntityModel.of(task);

        model.add(linkTo(methodOn(TaskController.class).getTaskById(taskId)).withRel("self"));
        model.add(linkTo(methodOn(TaskController.class).deleteTask(taskId)).withRel("delete"));
        model.add(linkTo(methodOn(TaskController.class).updateTask(taskId, null)).withRel("update"));

        return ResponseEntity.ok(model);
    }

	@PutMapping("/tasks/{taskId}")
	public ResponseEntity<EntityModel<TaskModel>> updateTask(@PathVariable UUID taskId,
											 @RequestBody @Valid TaskDTO taskDTO) {

		TaskModel updated = taskService.updateTask(taskId, taskDTO);

        EntityModel<TaskModel> model = EntityModel.of(updated);
        model.add(linkTo(methodOn(TaskController.class).getTaskById(taskId)).withRel("self"));
        model.add(linkTo(methodOn(TaskController.class).deleteTask(taskId)).withRel("delete"));

        return ResponseEntity.ok(model);
	}

	@DeleteMapping("/tasks/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
		taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
