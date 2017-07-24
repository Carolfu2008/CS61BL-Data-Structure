package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


/**
 * Created by lifesaver on 16/07/2017.
 */
public class Commit implements Serializable {
    protected String shaCode;
    protected String description;
    protected String timeStamp;
    protected String former;
    protected HashMap<String, String> fileID;
    protected String branch;
    protected HashSet<String> removed;

    public Commit() {
        this.description = "initial commit";
        this.timeStamp = curTime();
        this.fileID = null;
        this.former = null;
        this.shaCode = Utils.sha1(description, timeStamp);
        this.branch = "master";
    }

    public Commit(String regDes, HashMap<String, String> files) {
        this.description = regDes;
        this.timeStamp = curTime();
        this.former = GitMethod.serialRead("head").getShaCode();
        this.fileID = files;
        String[] allStaff = strChanging(files, description, timeStamp, former);
        this.shaCode = Utils.sha1(allStaff);
        GitMethod.HEAD = shaCode;
    }

    public String[] strChanging(HashMap file, String a, String b, String c) {
        String[] strlst = new String[file.size() * 2 + 3];
        Set<String> keys = file.keySet();
        int cnt = 0;
        for (String key : keys) {
            strlst[cnt] = key;
            strlst[cnt + 1] = fileID.get(key);
            cnt += 2;
        }
        strlst[cnt++] = a;
        strlst[cnt++] = b;
        strlst[cnt++] = c;
        return strlst;
    }

    public String curTime() {
        Date date = new Date();
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return t.format(date);
    }

    public String getShaCode() {
        return shaCode;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranch() {

        return branch;
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

    public void printLog() {
        System.out.println("===");
        System.out.println("Commit " + this.shaCode);
        System.out.println(this.timeStamp);
        System.out.println(this.description);
        System.out.println();
    }
}
