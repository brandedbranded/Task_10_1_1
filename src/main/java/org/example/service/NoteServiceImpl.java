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
    public void help() {
        noteDao.help();
    }

    @Override
    public Note noteNew() {
        return noteDao.noteNew();
    }

    @Override
    public List<Note> noteList() {
        return noteDao.noteList();
    }

    @Override
    public void noteRemove() {
        noteDao.noteRemove();
    }

    @Override
    public void noteExport() {
        noteDao.noteExport();
    }

    @Override
    public void exit(Scanner scanner) {
        noteDao.exit(scanner);
    }

    public void start() {
        System.out.println("Это Ваша записная книжка. Вот список доступных команд:\nhelp\nnote-new\nnote-list\nnote-remove\nnote-export\nexit");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            switch (command) {
                case "help" -> help();
                case "exit" -> exit(scanner);
                case "note-new" -> noteNew();
                case "note-list" -> noteList();
                case "note-remove" -> noteRemove();
                case "note-export" -> noteExport();
                default ->
                        log.warning("Несуществующая команда\nИспользуйте команду help для получения доступных команд");
            }
        }
    }
}

