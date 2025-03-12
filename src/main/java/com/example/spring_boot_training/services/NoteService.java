package com.example.spring_boot_training.services;

import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface NoteService {
    GetNoteDto createNote(CreateNoteDto createNoteDto);
    List<GetNoteDto> getAllNotes();
    GetNoteDto getNoteById(Long id);
    GetNoteDto updateNote(Long id, UpdateNoteDto updateNoteDto);
    void deleteNote(Long id);
    GetNoteDto partiallyUpdateNote(Long id, Map<String, Object>updates);
}
