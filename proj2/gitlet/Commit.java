package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lifesaver on 16/07/2017.
 */
public class Commit implements Serializable {
    private String shaCode;
    private String description;
    private String timeStamp;
    private String former;
    private Map<String, String> fileID;

    public Commit() {
        this.description = "initial commit";
        this.timeStamp = curTime();
        this.fileID = null;
        this.former = null;
        this.shaCode = Utils.sha1(description, timeStamp);
    }

    public Commit(String regDes) {
        this.description = regDes;
        this.timeStamp = curTime();
        this.former = GitMethod.HEAD;
        this.fileID = adding(trans(GitMethod.staged));
        this.shaCode = Utils.sha1(description, former, strChanging(), timeStamp);
        GitMethod.HEAD = shaCode;
    }

    public String[] strChanging() {
        String[] strlst = new String[fileID.size() * 2];
        Iterator<String> iter = this.fileID.keySet().iterator();
        int cnt = 0;
        while (iter.hasNext()) {
            strlst[cnt] = iter.next();
            strlst[cnt + 1] = fileID.get(strlst[cnt]);
            cnt += 2;
        }
        return strlst;
    }

    public Blob[] trans(ArrayList<Blob> before) {
        Blob[] after = new Blob[before.size()];
        for (int i = 0; i <= after.length; i++) {
            after[i] = before.get(i);
        }
        return after;
    }

    public String curTime() {
        Date date = new Date();
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return t.format(date);
    }

    public Map adding(Blob[] tracked) {
        for (int i = 0; i < tracked.length; i++) {
            fileID.put(tracked[i].getFileName(), tracked[i].getShaCode());
        }
        return fileID;
    }

    public String getShaCode() {
        return shaCode;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getFormer() {
        return former;
    }

    public Map<String, String> getFileID() {
        return fileID;
    }

    public void printLog(Commit commit) {
        System.out.println("===");
        System.out.println("Commit " + commit.shaCode);
        System.out.println(timeStamp);
        System.out.println(description);
        System.out.println();
    }
}
