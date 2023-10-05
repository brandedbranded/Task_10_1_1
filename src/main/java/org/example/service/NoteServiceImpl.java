package org.example.service;

import lombok.extern.java.Log;
import org.example.dao.NoteDao;
import org.example.dao.NoteDaoImpl;
import org.example.model.Note;

import java.util.List;
import java.util.Scanner;

@Log
public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao = new NoteDaoImpl();

    @Override
    public void getAllCommands() {
        noteDao.getAllCommands();
    }

    @Override
    public Note createNewNote() {
        return noteDao.createNewNote();
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.getAllNotes();
    }

    @Override
    public void removeNoteById() {
        noteDao.removeNoteById();
    }

    @Override
    public void exportNotesToFile() {
        noteDao.exportNotesToFile();
    }

    @Override
    public void exit(Scanner scanner) {
        noteDao.exit(scanner);
    }

    public void start() {
        log.info("Это Ваша записная книжка. Вот список доступных команд:\nhelp\nnote-new\nnote-list\nnote-remove\nnote-export\nexit");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            switch (command) {
                case "help" -> getAllCommands();
                case "exit" -> exit(scanner);
                case "note-new" -> createNewNote();
                case "note-list" -> getAllNotes();
                case "note-remove" -> removeNoteById();
                case "note-export" -> exportNotesToFile();
                default ->
                        log.warning("Несуществующая команда\nИспользуйте команду help для получения доступных команд");
            }
        }
    }
}

