package main.java.memoranda;

import java.util.Collection;
import main.java.memoranda.date.CalendarDate;

/**
 * Task interface.
 */
public interface Task {

    int SCHEDULED = 0;

    int ACTIVE = 1;

    int COMPLETED = 2;

    int FROZEN = 4;

    int FAILED = 5;

    int LOCKED = 6;

    int DEADLINE = 7;

    int PRIORITY_LOWEST = 0;

    int PRIORITY_LOW = 1;

    int PRIORITY_NORMAL = 2;

    int PRIORITY_HIGH = 3;

    int PRIORITY_HIGHEST = 4;

    CalendarDate getStartDate();

    void setStartDate(CalendarDate date);

    CalendarDate getEndDate();

    void setEndDate(CalendarDate date);

    int getStatus(CalendarDate date);

    int getProgress();

    void setProgress(int p);

    int getPriority();

    void setPriority(int p);

    String getId();

    String getText();

    void setText(String s);
    
    /*Collection getDependsFrom();
    
    void addDependsFrom(Task task);
    
    void removeDependsFrom(Task task);*/

    Collection getSubTasks();

    Task getSubTask(String id);

    boolean hasSubTasks(String id);

    void setEffort(long effort);

    long getEffort();

    void setDescription(String description);

    String getDescription();

    Task getParentTask();

    String getParentId();

    void freeze();

    void unfreeze();

    long getRate();

    nu.xom.Element getContent();
}
