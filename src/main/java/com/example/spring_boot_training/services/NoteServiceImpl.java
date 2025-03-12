package com.example.spring_boot_training.services;

import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.entities.Note;
import com.example.spring_boot_training.exception.NoteNotFoundException;
import com.example.spring_boot_training.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public GetNoteDto createNote(CreateNoteDto createNoteDto) {
        Note note = new Note();
        note.setTitle(createNoteDto.getTitle());
        note.setContent(createNoteDto.getContent());
        Note savedNote = noteRepository.save(note);

        return new GetNoteDto(savedNote.getId(), savedNote.getTitle(), savedNote.getContent());
    }

    @Override
    public List<GetNoteDto> getAllNotes() {
        return noteRepository.findAll().stream().map(note -> new GetNoteDto(note.getId(), note.getTitle(),
                note.getContent())).collect(Collectors.toList());
    }

    @Override
    public GetNoteDto getNoteById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() ->new NoteNotFoundException("Not able to find note with id: " + id));
        return new GetNoteDto(note.getId(), note.getTitle(), note.getContent());
    }

    @Override
    public GetNoteDto updateNote(Long id, UpdateNoteDto updateNoteDto) {
        Note note = noteRepository.findById(id).orElseThrow(() ->new NoteNotFoundException("Note not Updated"));
        note.setTitle(updateNoteDto.getTitle());
        note.setContent(updateNoteDto.getContent());
        Note savedNote = noteRepository.save(note);
        return new GetNoteDto(savedNote.getId(), savedNote.getTitle(), savedNote.getContent());
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public GetNoteDto partiallyUpdateNote(Long id, Map<String, Object> updates) {
        Note note = noteRepository.findById(id).orElseThrow(() ->new NoteNotFoundException("Not Updated"));
        updates.forEach((key, value) -> {
            switch (key){
                case "title":
                    note.setTitle((String) value);
                    break;
                case "content":
                    note.setContent((String) value);
                    break;
                default:
                    break;
            }
        });

        Note updateNote = noteRepository.save(note);
        return new GetNoteDto(updateNote.getId(), updateNote.getTitle(), updateNote.getContent());
    }

}
