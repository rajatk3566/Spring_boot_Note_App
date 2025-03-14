package com.example.spring_boot_training.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDto {
    @NotBlank(message = "Title cannot be Empty")
    private String title;
    @NotBlank(message = "Content cannot be Empty")
    private String content;
}
