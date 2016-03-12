package com.github.boogie666;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends Task{
    String to;
    final List<Resource> resources;

    public DownloadTask() {
        this.resources = new ArrayList<>();
    }

    @Override
    public void execute() throws BuildException {
        resources.forEach(Resource::download);
        log("Downloaded files to " + this.to);
    }



    public Path path(Resource resource) {
        final String path = this.to + File.separator + resource.getAs();
        return Paths.get(path);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = getProject().replaceProperties(to);
    }

    public Resource createResource(){
        final Resource dependency = new Resource(this.getProject(), this);
        this.resources.add(dependency);
        return dependency;
    }

    public Resource createZipedResource(){
        final ZipResource zipResource = new ZipResource(this.getProject(), this);
        this.resources.add(zipResource);
        return zipResource;
    }

}
