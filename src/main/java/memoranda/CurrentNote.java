package main.java.memoranda;

import java.util.Collection;
import java.util.Vector;

public class CurrentNote {

    private static Note currentNote = null;
    private static Vector noteListeners = new Vector();

    public static Note get() {
        return currentNote;
    }

    public static void set(Note note, boolean toSaveCurrentNote) {
        noteChanged(note, toSaveCurrentNote);
        currentNote = note;
    }

    public static void reset() {
//    	 set toSave to true to mimic status quo behaviour only. the appropriate setting could be false
        set(null, true);
    }

    public static void addNoteListener(NoteListener nl) {
        noteListeners.add(nl);
    }

    // This method is never used. Is it important? Can we delete it?
    public static Collection getChangeListeners() {
        return noteListeners;
    }

    private static void noteChanged(Note note, boolean toSaveCurrentNote) {
        // Changed to enhanced for from regular for loop to improve readability
        for (Object noteListener : noteListeners) {
            ((NoteListener) noteListener).noteChange(note, toSaveCurrentNote);
        }
    }
}
