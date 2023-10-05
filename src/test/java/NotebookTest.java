import org.example.model.Note;
import org.example.service.NoteService;
import org.example.service.NoteServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@DisplayName("Название теста")
public class NotebookTest {
    private final NoteService noteService = new NoteServiceImpl();
    @Mock
    public List<Note> mockNoteList = new ArrayList<>();
    private String input;
    private InputStream inputStream;
    private Note newNote;

    @DisplayName("Уникальность генерируемых id")
    @Test
    public void generateUniqueId() {
        input = "Text\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        newNote = noteService.createNewNote();

        String input2 = "Texttext\nlabel label";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(inputStream2);
        Note newnote2 = noteService.createNewNote();

        assertNotEquals(newNote.getId(), newnote2.getId());
    }

    @DisplayName("Создание записки с некорректным текстом")
    @Test
    public void createNote_invalidText_throwsException() {
        input = "Oh\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(Note.class, "noteList", mockNoteList);
        newNote = noteService.createNewNote();

        assertThrows(NoSuchElementException.class, noteService::createNewNote);
        assertEquals(0, mockNoteList.size());
        assertNull(newNote);
    }

    @DisplayName("Создание записки с корректным текстом")
    @Test
    public void createNote_validText_addNoteToList() {
        input = "Oh no, my hand\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(Note.class, "noteList", mockNoteList);
        newNote = noteService.createNewNote();

        assertEquals(1, mockNoteList.size());
        assertEquals("Oh no, my hand", newNote.getText());
    }

    @DisplayName("Создание записки с некорректной меткой")
    @Test
    public void createNote_invalidLabel_noteEqualsNull() {
        input = "Oh no, my hand\nметка пипетка";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(Note.class, "noteList", mockNoteList);
        newNote = noteService.createNewNote();

        assertEquals(0, mockNoteList.size());
        assertNull(newNote);
    }

    @DisplayName("Создание записки с корректной меткой")
    @Test
    public void createNote_validLabel_addNoteToList() {
        input = "Oh no, my hand\ntag";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(Note.class, "noteList", mockNoteList);
        newNote = noteService.createNewNote();

        assertFalse(mockNoteList.isEmpty());
        assertEquals(List.of("TAG"), newNote.getLabel());
    }

    @DisplayName("Удаление записки с некорректным id")
    @Test
    public void removeNote_invalidId_throwsException() {
        String id = "айди";
        InputStream inputStream = new ByteArrayInputStream(id.getBytes());
        System.setIn(inputStream);
        noteService.removeNoteById();
        assertThrows(NoSuchElementException.class, noteService::removeNoteById);
    }

    @DisplayName("Удаление записки с корректным id")
    @Test
    public void removeNote_validId_removeNoteFromList() {
        input = "Oh no, my hand\ntag";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(Note.class, "noteList", mockNoteList);
        newNote = noteService.createNewNote();
        InputStream is = new ByteArrayInputStream(String.valueOf(newNote.getId()).getBytes());
        System.setIn(is);
        noteService.removeNoteById();

        assertTrue(mockNoteList.isEmpty());
    }


}
