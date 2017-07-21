package gitlet;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;


/**
 * Created by lifesaver on 16/07/2017.
 */
public class GitMethod implements Serializable {

    protected static ArrayList<Blob> staged;
    protected static TreeSet<String> staged1 = new TreeSet<String>();
    protected static String HEAD = null;
    protected static ArrayList<Blob> unstaged = new ArrayList<Blob>();
    protected static ArrayList<String> removed = new ArrayList<String>();

    protected static void checkPreviously() {
        File gitlet = new File(".gitlet/");
        if (gitlet.exists()) {
            HEAD = serialRead("head").getShaCode();
        }
    }

    protected static void serialWrite(String name, Commit com) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/commits/" + name + ".ser"));
            output.writeObject(com);
            output.close();
        } catch (IOException e) {
            System.out.println("SerialWrite failed.");
        }
    }

    protected static void sWStageList(ArrayList staged) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/staged/staged.ser"));
            output.writeObject(staged.get(0));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SerialWrite failed.");
        }
    }

    protected static void sWStageSet(TreeSet staged) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/staged/staged1.ser"));
            output.writeObject(staged);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SerialWrite failed.");
        }
    }

    protected static Commit serialRead(String name) {
        Commit tmp = null;
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(".gitlet/commits/" + name + ".ser"));
            tmp = (Commit) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("SerialRead failed.");
        }
        return tmp;
    }

    protected static ArrayList sRStageList() {
        ArrayList<Blob> tmp = new ArrayList<Blob>();
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(".gitlet/staged/staged.ser"));
            tmp = (ArrayList) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("SerialRead failed.");
        }
        return tmp;
    }

    protected static TreeSet sRStageSet() {
        TreeSet<String> staged1 = new TreeSet<String>();
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(".gitlet/staged/staged1.ser"));
            staged1 = (TreeSet) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("SerialRead failed.");
        }
        return staged1;
    }

    static boolean diffVersion(File current, String currentName) {
        String curSha = Utils.sha1(Utils.readContents(current));
        Commit head = serialRead(HEAD);
        if (head.getFileID() == null) {
            return true;
        }
        if (head.getFileID().containsKey(currentName)) {
            if (head.getFileID().get(currentName).equals(curSha)) {
                return false;
            } else {
                unstaged.add(new Blob(currentName));
                return true;
            }
        }
        return true;
    }

    public static void init() {
        File repo = new File(".gitlet");
        boolean check = repo.mkdir();
        if (check) {
            Commit initCommit = new Commit();
            staged = new ArrayList<>();
            File commits = new File(".gitlet/commits/");
            File blobs = new File(".gitlet/blobs/");
            File stageArea = new File(".gitlet/staged/");
            commits.mkdir();
            blobs.mkdir();
            stageArea.mkdir();
            HEAD = initCommit.getShaCode();
            serialWrite(initCommit.getShaCode(), initCommit);
            serialWrite("head", initCommit);
        } else {
            System.out.println("A gitlet version control "
                    + "system already exists in the current directory.");
            System.exit(0);
        }
    }

    public static void add(String name) {
        File file = new File(name);
        File stagedList = new File(".gitlet/commits/staged.ser");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        staged = new ArrayList<Blob>();
        if (diffVersion(file, name)) {
            if (stagedList.exists()) {
                staged = sRStageList();
            }
            staged1.add(name);
            staged.add(new Blob(name));
            for (int j = 0; j < staged.size(); j++) {
                System.out.println(staged.get(j));
            }
            try {
                Files.copy(Paths.get(name), Paths.get(".gitlet/staged/" + name));
            } catch (IOException e) {
                System.out.println(e);
            }
            sWStageSet(staged1);
        }
        if (removed != null) {
            if (removed.contains(name)) {
                removed.remove(name);
            }
        }
    }

    public static void commit(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb = sb.append(args[i]);
        }
        String des = sb.toString();
        staged1 = sRStageSet();
        if (unstaged.size() == 0 && staged1.size() == 0) {
            System.out.println("No changes added to the commit");
            return;
        }
        Commit curr = new Commit(des);
        for (Blob name : staged) {
            try {
                Files.copy(Paths.get(".gitlet/staged/" + name),
                        Paths.get(".gitlet/blobs/" + curr.getFileID().get(name)));
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                Files.delete(Paths.get(".gitlet/staged/" + name));
            } catch (IOException e) {
                System.out.println(e);
                return;
            }
        }
        HEAD = curr.getShaCode();
        staged = new ArrayList<Blob>();
        unstaged = new ArrayList<Blob>();
        removed = new ArrayList<String>();
        serialWrite(curr.getShaCode(), curr);
        serialWrite("head", curr);
        File stagedList = new File(".gitlet/commits/staged.ser");
        stagedList.delete();
    }
}
