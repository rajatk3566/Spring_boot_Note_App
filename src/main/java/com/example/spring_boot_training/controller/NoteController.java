package com.example.spring_boot_training.controller;


import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/note/create")
    public GetNoteDto createNote(@RequestBody CreateNoteDto createNoteDto) {
        return noteService.createNote(createNoteDto);
    }

    @GetMapping("/notes")
    public List<GetNoteDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/note/{id}")
    public GetNoteDto getNoteById(@PathVariable Long id){
        return  noteService.getNoteById(id);
    }

    @PutMapping("/note/update/{id}")
    public GetNoteDto updateNote(@PathVariable Long id, @RequestBody UpdateNoteDto updateNoteDto){
        return  noteService.updateNote(id, updateNoteDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
    }

    @PatchMapping("/{id}")
    public GetNoteDto partiallyUpdateNote(@PathVariable Long id, @RequestBody Map<String,Object> updates){
        return noteService.partiallyUpdateNote(id, updates);
    }
}
