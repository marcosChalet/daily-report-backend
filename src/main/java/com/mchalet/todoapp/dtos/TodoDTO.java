package com.mchalet.todoapp.dtos;

import jakarta.validation.constraints.NotBlank;

public record TodoDTO(@NotBlank String todo) { }
