package com.github.boogie666;

import org.apache.tools.ant.Project;

/**
 * Created by boogie666 on 2/14/2016.
 */
public class Resource {
    private String from;
    private String as;
    private final Project project;

    public Resource(Project project) {
        this.project = project;
    }


    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = project.replaceProperties(from);
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = project.replaceProperties(as);
    }
}
