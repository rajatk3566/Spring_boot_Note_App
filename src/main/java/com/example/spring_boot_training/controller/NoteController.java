package com.example.spring_boot_training.controller;

import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.entities.User;
import com.example.spring_boot_training.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/a pi/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<GetNoteDto> createNote(
            @RequestBody CreateNoteDto createNoteDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(noteService.createNote(createNoteDto, user));
    }

    @GetMapping
    public ResponseEntity<List<GetNoteDto>> getAllNotes(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(noteService.getAllNotes(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNoteDto> getNoteById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(noteService.getNoteById(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetNoteDto> updateNote(
            @PathVariable Long id,
            @RequestBody UpdateNoteDto updateNoteDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(noteService.updateNote(id, updateNoteDto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        noteService.deleteNote(id, user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetNoteDto> partiallyUpdateNote(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(noteService.partiallyUpdateNote(id, updates, user));
    }
}
