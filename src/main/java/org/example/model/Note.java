package org.example.model;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Data
public class Note {
    private static int res = 0;
    @Getter
    private static List<Note> noteList = new ArrayList<>();
    private final String text;
    @Getter
    private int id;
    @Getter
    private List<String> label;

    public Note(String text, List<String> label) {
        id = generateID();
        this.text = text;
        this.label = label;
    }

    public static int generateID() {
        return ++res;
    }

    @Override
    public String toString() {
        return String.format("{%d} # {%s}\n%s\n===============\n", this.getId(), this.getText(), this.getLabel());
    }

}
