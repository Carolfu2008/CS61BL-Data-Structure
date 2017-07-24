package gitlet;

import java.io.File;

/**
 * Created by lifesaver on 18/07/2017.
 */
public class Blob {
    private File file;
    private String fileName;
    private byte[] content;
    private String filePath;
    private String shaCode;

    public Blob(String f) {
        this.fileName = f;
        this.file = new File(f);
        this.filePath = System.getProperty("user.dir") + "/" + f;
        this.content = Utils.readContents(file);
        this.shaCode = Utils.sha1(content);
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getShaCode() {
        return shaCode;
    }
}
