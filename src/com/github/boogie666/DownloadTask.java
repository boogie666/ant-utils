package com.github.boogie666;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends Task{
    private String to;
    private final List<Resource> resources;

    public DownloadTask() {
        this.resources = new ArrayList<>();
    }

    @Override
    public void execute() throws BuildException {
        resources.forEach(this::download);
        log("Downloaded files to " + this.to);
    }

    private void download(Resource resource) {
        log("Downloading " + resource.getAs() +" from " + resource.getFrom());
        try (InputStream in = new URL(resource.getFrom()).openStream()){
            Files.copy(in, path(resource), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new BuildException("Failed to download dependency from: " + resource.getFrom());
        }
    }

    private Path path(Resource resource) {
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
        final Resource dependency = new Resource(this.getProject());
        this.resources.add(dependency);
        return dependency;
    }

}
