package org.example.service;

import org.example.model.Note;

import java.util.List;
import java.util.Scanner;

public interface NoteService {
    void help();

    Note noteNew();

    List<Note> noteList();

    void noteRemove();

    void noteExport();

    void exit(Scanner scanner);
}
