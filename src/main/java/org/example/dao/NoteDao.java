package org.example.dao;

import org.example.model.Note;

import java.util.List;
import java.util.Scanner;

public interface NoteDao {
    void getAllCommands();

    Note createNewNote();

    List<Note> getAllNotes();

    void removeNoteById();

    void exportNotesToFile();

    void exit(Scanner scanner);

}
