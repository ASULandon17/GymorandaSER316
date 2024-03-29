/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package main.java.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.ui.AppFrame;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static Project _project = null;
    private static TaskList _tasklist = null;
    private static NoteList _notelist = null;
    private static ResourcesList _resources = null;
    private static Vector projectListeners = new Vector();

        
    static {
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);

        if (_project == null) {
            // alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
            // references to missing project
            _project = ProjectManager.getProject("__default");
            if (_project == null)
                _project = (Project)ProjectManager.getActiveProjects().get(0);
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());

        }

        _tasklist = CurrentStorage.get().openTaskList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();                                               
            }
        });
    }
        

    public static Project get() {
        return _project;
    }

    public static TaskList getTaskList() {
            return _tasklist;
    }

    public static NoteList getNoteList() {
            return _notelist;
    }
    
    public static ResourcesList getResourcesList() {
            return _resources;
    }
    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        TaskList newTaskList = CurrentStorage.get().openTaskList(project);
        NoteList newNoteList = CurrentStorage.get().openNoteList(project);
        ResourcesList newResources = CurrentStorage.get().openResourcesList(project);
        notifyListenersBefore(project, newNoteList, newTaskList, newResources);
        _project = project;
        _tasklist = newTaskList;
        _notelist = newNoteList;
        _resources = newResources;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }

    // Method is not being used. Is this necessary to keep?
    public static Collection getChangeListeners() {
        return projectListeners;
    }

    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, ResourcesList rl) {
        // Replaced standard for loop with enhanced for loop for better readability
        for (Object projectListener : projectListeners) {
            ((ProjectListener) projectListener).projectChange(project, nl, tl, rl);
            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }
    
    private static void notifyListenersAfter() {
        // Replaced standard for loop with enhanced for loop for better readability
        for (Object projectListener : projectListeners) {
            ((ProjectListener) projectListener).projectWasChanged();
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeResourcesList(_resources, _project);
        storage.storeProjectManager();
    }

    // Removed free() method. Java has garbage bin, method was both unused and unnecessary.
}
