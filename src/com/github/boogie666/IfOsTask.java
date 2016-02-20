package com.github.boogie666;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by boogie666 on 2/14/2016.
 */
public class IfOsTask extends Task implements TaskContainer {
    private Os os;
    private final List<Task> tasks;

    public IfOsTask() {
        tasks = new ArrayList<>();
    }

    @Override
    public void execute() throws BuildException {
        if(checkOs()){
            tasks.forEach(Task::perform);
        }
    }

    private boolean checkOs() {
        final String osName = this.os.name();
        final String systemOs = System.getProperty("os.name", "nothing").toLowerCase(Locale.ENGLISH);
        return systemOs.contains(osName);
    }

    public void setOs(Os os) {
        this.os = os;
    }

    @Override
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public enum Os{
        win, mac
    }

}
