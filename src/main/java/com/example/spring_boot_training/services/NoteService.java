package com.example.spring_boot_training.services;

import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.entities.User;
import java.util.List;
import java.util.Map;
public interface NoteService {
    GetNoteDto createNote(CreateNoteDto createNoteDto, User user);
    List<GetNoteDto> getAllNotes(User user);
    GetNoteDto getNoteById(Long id, User user);
    GetNoteDto updateNote(Long id, UpdateNoteDto updateNoteDto, User user);
    void deleteNote(Long id, User user);
    GetNoteDto partiallyUpdateNote(Long id, Map<String, Object> updates, User user);
}
