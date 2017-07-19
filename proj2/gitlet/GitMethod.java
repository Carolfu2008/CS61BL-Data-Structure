package gitlet;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by lifesaver on 16/07/2017.
 */
public class GitMethod implements Serializable {

    public static ArrayList<Blob> tracked;
    public static String  HEAD = null;


    protected static void startUp() {
        File gitlet = new File(".gitlet/");
        if (gitlet.exists()) {
            HEAD =serialRead("head").getShaCode();
        }
    }

    protected static void serialWrite(String name, Commit com) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(".gitlet/commits/" + name + ".ser"));
            output.writeObject(com);
            output.close();
        } catch (IOException e) {
            System.out.println("SerialWrite failed.");
        }
    }

    protected static Commit serialRead(String name) {
        Commit tmp = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(".gitlet/commits/" + name + ".ser"));
            tmp = (Commit) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("SerialRead failed.");
        }
        return tmp;
    }

    public static void init() {
        File repo = new File(".gitlet");
        boolean check = repo.mkdir();
        if (check) {
            Commit initCommit = new Commit("initial commit");
            tracked = new ArrayList<>();
            File commits = new File(".gitlet/commits/");
            File blobs = new File(".gitlet/blobs/");
            File stageArea = new File(".gitlet/staged/");
            commits.mkdir();
            blobs.mkdir();
            stageArea.mkdir();
            HEAD = initCommit.getShaCode();
            serialWrite(initCommit.getShaCode(),initCommit);
            serialWrite("head",initCommit);
        } else {
            System.out.println("A gitlet version control system already exists in the current directory.");
            System.exit(0);
        }
    }

    public static void add(String name) {
        File file = new File(name);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        Blob blob = new Blob(name);
    }
}
