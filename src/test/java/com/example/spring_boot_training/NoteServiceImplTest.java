package com.example.spring_boot_training;
import com.example.spring_boot_training.dto.CreateNoteDto;
import com.example.spring_boot_training.dto.GetNoteDto;
import com.example.spring_boot_training.dto.UpdateNoteDto;
import com.example.spring_boot_training.entities.Note;
import com.example.spring_boot_training.entities.User;
import com.example.spring_boot_training.exception.NoteNotFoundException;
import com.example.spring_boot_training.repository.NoteRepository;
import com.example.spring_boot_training.services.NoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteServiceImpl noteService;

    private final User testUser = User.builder()
            .id(1L)
            .email("test@example.com")
            .build();

    @Test
    void createNote_SavesNoteWithUser_ReturnsDto() {
        CreateNoteDto createDto = new CreateNoteDto("Test Title", "Test Content");
        Note savedNote = Note.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(testUser)
                .build();

        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);
        GetNoteDto result = noteService.createNote(createDto, testUser);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Content", result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void getAllNotes_ReturnsUserNotes() {
        Note note1 = Note.builder().id(1L).user(testUser).build();
        Note note2 = Note.builder().id(2L).user(testUser).build();
        when(noteRepository.findByUser(testUser)).thenReturn(List.of(note1, note2));
        List<GetNoteDto> result = noteService.getAllNotes(testUser);
        assertEquals(2, result.size());
        verify(noteRepository).findByUser(testUser);
    }

    @Test
    void getNoteById_ExistingNote_ReturnsDto() {
        Note note = Note.builder().id(1L).user(testUser).build();
        when(noteRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(note));
        GetNoteDto result = noteService.getNoteById(1L, testUser);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getNoteById_NonExistingNote_ThrowsException() {
        when(noteRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.empty());
        assertThrows(NoteNotFoundException.class,
                () -> noteService.getNoteById(1L, testUser));
    }

    @Test
    void updateNote_UpdatesFields_ReturnsUpdatedDto() {
        Note existingNote = Note.builder()
                .id(1L)
                .title("Old Title")
                .content("Old Content")
                .user(testUser)
                .build();

        UpdateNoteDto updateDto = new UpdateNoteDto("New Title", "New Content");
        when(noteRepository.findByIdAndUser(1L, testUser))
                .thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(existingNote);
        GetNoteDto result = noteService.updateNote(1L, updateDto, testUser);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
        verify(noteRepository).save(existingNote);
    }

    @Test
    void updateNote_NonExistingNote_ThrowsException() {
        when(noteRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.empty());
        assertThrows(NoteNotFoundException.class,
                () -> noteService.updateNote(1L, new UpdateNoteDto(), testUser));
    }

    @Test
    void deleteNote_DeletesExistingNote() {
        Note note = Note.builder().id(1L).user(testUser).build();
        when(noteRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(note));
        noteService.deleteNote(1L, testUser);
        verify(noteRepository).delete(note);
    }

    @Test
    void deleteNote_NonExistingNote_ThrowsException() {
        when(noteRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.empty());
        assertThrows(NoteNotFoundException.class,
                () -> noteService.deleteNote(1L, testUser));
    }

    @Test
    void partiallyUpdateNote_UpdatesTitle_ReturnsUpdatedDto() {
        Note existingNote = Note.builder()
                .id(1L)
                .title("Old Title")
                .content("Content")
                .user(testUser)
                .build();

        when(noteRepository.findByIdAndUser(1L, testUser))
                .thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class)))
                .thenReturn(existingNote);
        GetNoteDto result = noteService.partiallyUpdateNote(1L,
                Map.of("title", "New Title"), testUser);
        assertEquals("New Title", result.getTitle());
        assertEquals("Content", result.getContent());
    }


    @Test
    void partiallyUpdateNote_UpdatesContent_ReturnsUpdatedDto() {
        Note existingNote = Note.builder()
                .id(1L)
                .title("Title")
                .content("Old Content")
                .user(testUser)
                .build();

        when(noteRepository.findByIdAndUser(1L, testUser))
                .thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class)))
                .thenReturn(existingNote);
        GetNoteDto result = noteService.partiallyUpdateNote(1L,
                Map.of("content", "New Content"), testUser);
        assertEquals("Title", result.getTitle());
        assertEquals("New Content", result.getContent());
    }


    @Test
    void partiallyUpdateNote_InvalidField_IgnoresField() {
        Note existingNote = Note.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .user(testUser)
                .build();

        when(noteRepository.findByIdAndUser(1L, testUser))
                .thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class)))
                .thenReturn(existingNote);
        GetNoteDto result = noteService.partiallyUpdateNote(1L,
                Map.of("invalid", "value"), testUser);
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
    }

}
