package org.example.dao;

import org.example.model.Note;

import java.util.List;
import java.util.Scanner;

public interface NoteDao {
    void help();

    Note noteNew();

    List<Note> noteList();

    void noteRemove();

    void noteExport();

    void exit(Scanner scanner);

}
