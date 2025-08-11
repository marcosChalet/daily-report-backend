package com.mchalet.todoapp.dtos;

import com.mchalet.todoapp.model.TagModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TodoListDTO(@NotBlank String title,
                          @NotNull Integer todoType,
                          List<TagModel> tags) { }
