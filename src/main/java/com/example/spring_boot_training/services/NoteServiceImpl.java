package com.example.spring_boot_training.services;

import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.entities.Note;
import com.example.spring_boot_training.entities.User;
import com.example.spring_boot_training.exception.NoteNotFoundException;
import com.example.spring_boot_training.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public GetNoteDto createNote(CreateNoteDto createNoteDto, User user) {
        Note note = Note.builder()
                .title(createNoteDto.getTitle())
                .content(createNoteDto.getContent())
                .user(user)
                .build();
        Note savedNote = noteRepository.save(note);
        return convertToDto(savedNote);
    }

    @Override
    public List<GetNoteDto> getAllNotes(User user) {
        return noteRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GetNoteDto getNoteById(Long id, User user) {
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        return convertToDto(note);
    }

    @Override
    public GetNoteDto updateNote(Long id, UpdateNoteDto updateNoteDto, User user) {
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        note.setTitle(updateNoteDto.getTitle());
        note.setContent(updateNoteDto.getContent());

        Note updatedNote = noteRepository.save(note);
        return convertToDto(updatedNote);
    }

    @Override
    public void deleteNote(Long id, User user) {
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        noteRepository.delete(note);
    }

    @Override
    public GetNoteDto partiallyUpdateNote(Long id, Map<String, Object> updates, User user) {
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title" -> note.setTitle((String) value);
                case "content" -> note.setContent((String) value);
            }
        });

        Note updatedNote = noteRepository.save(note);
        return convertToDto(updatedNote);
    }

    private GetNoteDto convertToDto(Note note) {
        return GetNoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
