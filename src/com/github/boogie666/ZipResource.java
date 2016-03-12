package com.github.boogie666;

import org.apache.tools.ant.Project;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by boogie666 on 3/5/2016.
 */
public class ZipResource extends Resource {
    private static final int MAGIC_BUFFER_SIZE = 8192;

    private String pathInZip;
    public ZipResource(Project project, DownloadTask parent) {
        super(project, parent);
    }



    @Override
    protected void download(InputStream in) throws IOException {
        final ZipInputStream zin = createZipInputStream(in);
        final ByteArrayInputStream in1 = extractFile(zin);
        this.project.log("Downloading and extracting " + this.getAs() + " archive");
        super.download(new BufferedInputStream(in1));

        in1.close();
        zin.close();
    }



    private ZipInputStream createZipInputStream(InputStream in) {
        return new ZipInputStream(new BufferedInputStream(in, MAGIC_BUFFER_SIZE));
    }

    private ByteArrayInputStream extractFile(ZipInputStream zin) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        ZipEntry ze = null;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.getName().equals(this.getPathInZip())) {
                int len;
                while ((len = zin.read()) != -1) {
                    out.write(len);
                }
                return new ByteArrayInputStream(out.toByteArray());
            }
        }
        throw new IOException("File " + this.getPathInZip() + " not found in zip");
    }

    public void setPathInZip(String pathInZip) {
        this.pathInZip = pathInZip;
    }

    public String getPathInZip() {
        return pathInZip;
    }
}
