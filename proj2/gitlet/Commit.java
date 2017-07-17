package gitlet;

import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifesaver on 16/07/2017.
 */
public class Commit {

    private int id;
    private String description;
    private String timeStamp;
    private Commit former;
    public HashMap<String, String> fileID;

    public Commit(int initID, String initDate,String initDes) {
        this.id = initID;
        this.description = initDes;
        this.timeStamp = initDate;
    }

    public Commit(int regID, String regDes, String regDate, Commit regFormer, HashMap<String, String> regFile) {
        this.id = regID;
        this.description = regDes;
        this.timeStamp = regDate;
        this.former = regFormer;
        this.fileID=regFile;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Commit getFormer() {
        return former;
    }

    public HashMap<String, String> getFileID() {
        return fileID;
    }
}
