package org.example.dao;

import lombok.extern.java.Log;
import org.example.model.Note;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
public class NoteDaoImpl implements NoteDao {
    @Override
    public void help() {
        log.info("Вызвана команда help");
        System.out.println("Список доступных комманд: \n help - все доступные команды \n note-new - создать новую заметку \n note-list - вывести весь список заметок \n note-remove - удалить заметку \n note-export - поместить все заметки в текстовый файл \n exit - завершить работу приложения");
    }


    @Override
    public Note noteNew() {
        log.info("Вызвана команда note-new");
        Note newNote = null;
        List<String> noteLabelsList;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите заметку");
        String noteText = scanner.nextLine();
        try {
            if (noteText.length() < 3) {
                log.info("Текст заметки должен содержать более 3х символов. Введено: " + noteText);
                throw new NoSuchElementException("Текст заметки должен содержать более 3х символов. Введено: " + noteText);
            }
            System.out.println("Добавить метки?");
            String labelslabels = scanner.nextLine();
            noteLabelsList = List.of(labelslabels.toUpperCase().split(" "));
            List<String> filteredLabels = new ArrayList<>();
            for (String label : noteLabelsList) {
                if (!label.matches("^[a-zA-Z]+$")) {
                    log.info("Метка должна состоять только из букв. Введено: " + labelslabels);
                    throw new IllegalArgumentException("Метка должна состоять только из букв. \nВведите новую команду:");
                } else {
                    filteredLabels.add(label);
                }
            }
            newNote = new Note(noteText, filteredLabels);
            Note.getNoteList().add(newNote);
            log.info("Заметка успешно добавлена:\n" + newNote);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return newNote;
    }

    @Override
    public List<Note> noteList() {
        log.info("Вызвана команда note-list");
        Scanner scanner = new Scanner(System.in);
        List<String> labels;
        List<Note> filteredNotes = new ArrayList<>();
        String ll;
        System.out.println("Введите метки, чтобы отобразить определенные заметки или оставьте пустым для отображения всех заметок");
        try {
            ll = scanner.nextLine();
            if (!ll.isEmpty()) {
                labels = List.of(ll.toUpperCase().split(" "));
                for (String label : labels) {
                    if (!label.matches("^[a-zA-Z]+$")) {
                        log.info("Метка должна состоять только из букв. Введено: " + label);
                        throw new IllegalArgumentException("Метка должна состоять только из букв. \nВведите новую команду:");
                    } else {
                        for (Note note : Note.getNoteList()) {
                            if (note.getLabel().contains(label)) {
                                filteredNotes.add(note);
                            }
                        }
                    }
                }
                if (filteredNotes.isEmpty()) {
                    log.info("Заметки с такой меткой не существует");
                    throw new IllegalArgumentException("Заметки с такой меткой не существует");
                }
            } else {
                if (Note.getNoteList().isEmpty()) {
                    log.info("Список заметок пуст");
                    throw new IllegalArgumentException("Список заметок пуст");
                }
                filteredNotes = Note.getNoteList();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        for (Note n : filteredNotes) {
            System.out.println(n);
        }
        return filteredNotes;
    }

    @Override
    public void noteRemove() {
        log.info("Вызвана команда note-remove");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id удаляемой заметки");
        int id;
        String ids = scanner.nextLine();
        String regex = "^\\d*";

        try {
            if (!ids.matches(regex)) {
                log.info("Неверный формат ввода. Число должно быть одно. Введено: " + ids);
                throw new NoSuchElementException("Неверный формат ввода. Число должно быть одно. Введено: " + ids);
            } else if (Note.getNoteList().isEmpty()) {
                log.warning("Список заметок пуст");
                throw new NumberFormatException("Список заметок пуст");
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            return;
        }
        id = Integer.parseInt(ids);
        List<Integer> noteIdS = new ArrayList<>();
        for (Note note : Note.getNoteList()) {
            noteIdS.add(note.getId());
        }
        if (!noteIdS.contains(id)) {
            log.info("Заметка не найдена");
        }
        Iterator<Note> iterator = Note.getNoteList().iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getId() == id) {
                iterator.remove();
                log.info("Заметка c id " + id + " удалена");
            }
        }
    }

    @Override
    public void noteExport() {
        log.info("Вызвана команда note-export");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss");
        String nameOfFile = "notes" + dateFormat.format(new Date()) + ".txt";
        try (FileWriter fileWriter = new FileWriter(nameOfFile)) {
            if (Note.getNoteList().isEmpty()) {
                log.info("Список заметок пуст");
                throw new IOException("Список заметок пуст");
            }
            for (Note n : Note.getNoteList()) {
                fileWriter.write(n.toString());
            }
            log.info("Заметки экспортированы в файл: " + nameOfFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void exit(Scanner scanner) {
        log.info("Вызвана команда exit");
        log.info("Работа приложения завершена \n      Всего доброго!");
        System.exit(0);
    }
}
