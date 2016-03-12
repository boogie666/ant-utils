package com.github.boogie666;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by boogie666 on 2/14/2016.
 */
public class Resource {
    protected final DownloadTask parent;
    protected final Project project;

    private String from;
    private String as;

    public Resource(Project project, DownloadTask downloadTask) {
        this.project = project;
        this.parent = downloadTask;
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

    public final void download() {
        this.project.log("Downloading " + this.getAs() +" from " + this.getFrom());
        try (InputStream in = new URL(this.getFrom()).openStream()){
            try{
                download(in);
            }catch (IOException ex){
                this.project.log(ex.getMessage());
                throw new BuildException("Can't process " + this.getAs(), ex);
            }
        } catch (IOException e) {
            throw new BuildException("Failed to download dependency from: " + this.getFrom(), e);
        }
    }

    protected void download(InputStream in) throws IOException {
        Files.copy(in, this.parent.path(this), StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }
}
