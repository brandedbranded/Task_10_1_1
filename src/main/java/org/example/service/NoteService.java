package org.example.service;

import org.example.model.Note;

import java.util.List;
import java.util.Scanner;

public interface NoteService {
    void getAllCommands();

    Note createNewNote();

    List<Note> getAllNotes();

    void removeNoteById();

    void exportNotesToFile();

    void exit(Scanner scanner);
}
