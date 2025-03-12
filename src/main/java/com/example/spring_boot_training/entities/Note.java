package com.example.spring_boot_training.entities;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "notes")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column (name = "note_title")
    private String title;

    @Column (name = "note_content")
    private String content;
}
