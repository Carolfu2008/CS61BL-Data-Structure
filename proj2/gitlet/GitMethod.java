package gitlet;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * Created by lifesaver on 16/07/2017.
 */
public class GitMethod implements Serializable {

    protected static String HEAD = null;
    protected static String thisbranch = null;
    protected static TreeSet<String> staged = new TreeSet<String>();
    protected static TreeSet<String> untrack = new TreeSet<String>();
    protected static TreeSet<String> removed = new TreeSet<String>();
    protected static TreeMap<String, String> allCom = new TreeMap<String, String>();
    protected static TreeMap<String, String> branch = new TreeMap<String, String>();

    protected static void checkPreviously() {
        File gitlet = new File(".gitlet/");
        if (gitlet.exists()) {
            HEAD = serialRead("head").getShaCode();
            thisbranch = serialRead("head").getBranch();
            staged = sRSet("staged");
            removed = sRSet("removed");
            allCom = sRMap("allCom");
            branch = sRMap("branch");

        }
    }

    protected static void checkAfterwards() {
        sWSet("staged", staged);
        sWSet("removed", removed);
        sWMap("allCom", allCom);
        sWMap("branch", branch);
    }

    protected static void serialWrite(String name, Commit com) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/commits/" + name + ".ser"));
            output.writeObject(com);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return tmp;
    }

    protected static void sWSet(String name, TreeSet set) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/staged/" + name + ".ser"));
            output.writeObject(set);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static TreeSet sRSet(String name) {
        TreeSet<String> tmp = new TreeSet<String>();
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(".gitlet/staged/" + name + ".ser"));
            tmp = (TreeSet) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    protected static void sWMap(String name, TreeMap map) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(".gitlet/staged/" + name + ".ser"));
            output.writeObject(map);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static TreeMap sRMap(String name) {
        TreeMap<String, String> tmp = new TreeMap<String, String>();
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(".gitlet/staged/" + name + ".ser"));
            tmp = (TreeMap) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    static boolean diffVersion(File current, String currentName) {
        String curSha = Utils.sha1(Utils.readContents(current));
        Commit head = serialRead("head");
        if (head.getFileID() == null) {
            return true;
        }
        if (head.getFileID().containsKey(currentName)) {
            if (head.getFileID().get(currentName).equals(curSha)) {
                return false;
            }
        }
        return true;
    }

    static void scan() {
        Commit head = serialRead(HEAD);
        File dir = new File(System.getProperty("user.dir"));
        File[] allFile = dir.listFiles();
        for (File file : allFile) {
            if ((file.isDirectory() || file.isHidden() || staged.contains(file.getName())
                    || removed.contains(file.getName()))) {
                continue;
            }
            if (head.fileID != null) {
                if (head.fileID.containsKey(file.getName())) {
                    continue;
                }
                if (serialRead(head.former).getFileID() != null) {
                    if (serialRead(head.former).getFileID().containsKey(file.getName())) {
                        continue;
                    }
                }
            }
            untrack.add(file.getName());
        }
    }

    public static void init() {
        File repo = new File(".gitlet");
        boolean check = repo.mkdir();
        if (check) {
            Commit initCommit = new Commit();
            File commits = new File(".gitlet/commits/");
            File blobs = new File(".gitlet/blobs/");
            File stageArea = new File(".gitlet/staged/");
            commits.mkdir();
            blobs.mkdir();
            stageArea.mkdir();
            HEAD = initCommit.getShaCode();
            serialWrite(initCommit.getShaCode(), initCommit);
            serialWrite("head", initCommit);
            allCom.put("master", HEAD);
            branch.put("master", HEAD);
            sWMap("allCom", allCom);
            sWMap("branch", branch);
        } else {
            System.out.println("A gitlet version control "
                    + "system already exists in the current directory.");
            System.exit(0);
        }
    }

    public static void add(String name) {
        File file = new File(name);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        File formerStaged = new File(".gitlet/staged/staged.ser");
        if (formerStaged.exists()) {
            staged = sRSet("staged");
        }
        if (diffVersion(file, name)) {
            staged.add(name);
            try {
                Files.copy(Paths.get(name), Paths.get(".gitlet/staged/" + name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sWSet("staged", staged);
        if (removed != null) {
            if (removed.contains(name)) {
                removed.remove(name);
            }
        }
    }

    public static void commit(String des) {
        if (removed.size() == 0 && staged.size() == 0) {
            System.out.println("No changes added to the commit");
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (String name : staged) {
            String shaCode = Utils.sha1(Utils.readContents(new File(".gitlet/staged/" + name)));
            map.put(name, shaCode);
        }
        Commit curr = new Commit(des, map);
        for (String name : staged) {
            try {
                Files.copy(Paths.get(".gitlet/staged/" + name),
                        Paths.get(".gitlet/blobs/" + name + curr.getFileID().get(name)));
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
        curr.setBranch(serialRead("head").getBranch());
        HEAD = curr.getShaCode();
        staged = new TreeSet<>();
        removed = new TreeSet<>();
        allCom.put(thisbranch, curr.getShaCode());
        branch.put(thisbranch, curr.getShaCode());
        serialWrite(curr.getShaCode(), curr);
        serialWrite("head", curr);
    }

    public static void rm(String name) {
        Commit pre = serialRead("head");
        if (staged.contains(name) && pre.fileID == null) {
            staged.remove(name);
            try {
                Files.delete(Paths.get(".gitlet/staged/" + name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (pre.fileID != null && pre.fileID.containsKey(name)) {
            try {
                if (staged.contains(name)) {
                    staged.remove(name);
                    Files.delete(Paths.get(".gitlet/staged/" + name));
                }
                removed.add(name);
                Files.delete(Paths.get(name));
            } catch (IOException e) {
                removed.add(name);
            }
        } else if (pre.fileID != null && !pre.fileID.containsKey(name)) {
            staged.remove(name);
            try {
                Files.delete(Paths.get(".gitlet/staged/" + name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reason to remove the file.");
        }
    }

    public static void log() {
        Commit pre = serialRead("head");
        String tmp = null;
        while (pre.getFormer() != null) {
            pre.printLog();
            tmp = pre.getFormer();
            pre = serialRead(tmp);
        }
        pre.printLog();
    }

    public static void glog() {
        HashSet<String> done = new HashSet<String>();
        for (String shatmp : allCom.values()) {
            if (done.contains(shatmp)) {
                continue;
            }
            Commit curr = serialRead(shatmp);
            String tmp = shatmp;
            while (curr.getFormer() != null && !done.contains(tmp)) {
                curr.printLog();
                done.add(tmp);
                tmp = curr.getFormer();
                curr = serialRead(tmp);
            }
            if (!done.contains(tmp)) {
                curr.printLog();
                done.add(tmp);
            }
        }
    }

    public static void find(String args) {
        HashSet<String> done = new HashSet<String>();
        int found = 0;
        for (String shatmp : allCom.values()) {
            if (done.contains(shatmp)) {
                continue;
            }
            Commit curr = serialRead(shatmp);
            String tmp = shatmp;
            while (curr.getFormer() != null && !done.contains(tmp)) {
                if (args.equals(curr.getDescription())) {
                    System.out.println(curr.getShaCode());
                    found += 1;
                }
                done.add(tmp);
                tmp = curr.getFormer();
                curr = serialRead(tmp);
            }
            if (!done.contains(tmp)) {
                if (args.equals(curr.getDescription())) {
                    System.out.println(curr.getShaCode());
                    found += 1;
                }
                done.add(tmp);
            }
        }
        if (found == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void status() {
        System.out.println("=== Branches ===");
        for (String tmp : branch.keySet()) {
            if (tmp.equals(thisbranch)) {
                System.out.println("*" + tmp);
            } else {
                System.out.println(tmp);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String tmp : staged) {
            System.out.println(tmp);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String tmp : removed) {
            System.out.println(tmp);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void checkout(String... args) {
        if (args.length == 3 && args[1].equals("--")) {
            GitMethod.checkout1(args[2], GitMethod.serialRead(GitMethod.HEAD));
        } else if (args.length == 4 && (args[2].equals("--"))) {
            GitMethod.checkout2(args[1], args[3]);
        } else if (args.length == 2) {
            GitMethod.checkout3(args[1]);
        } else {
            System.out.println("Incorrect operands.");
        }
    }

    public static void checkout1(String name, Commit curr) {
        if (curr.fileID.containsKey(name)) {
            try {
                Files.copy(Paths.get(".gitlet/blobs/" + name + curr.fileID.get(name)),
                        Paths.get(name), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist in that commit.");
        }
    }

    public static void checkout2(String id, String name) {
        HashSet<String> done = new HashSet<String>();
        for (String shatmp : allCom.values()) {
            if (done.contains(shatmp)) {
                continue;
            }
            Commit curr = serialRead(shatmp);
            String tmp = shatmp;
            while (curr.getFormer() != null || !done.contains(tmp)) {
                if (curr.getShaCode().equals(id) || curr.getShaCode().
                        substring(0, id.length()).equals(id)) {
                    checkout1(name, curr);
                    return;
                }
                done.add(tmp);
                tmp = curr.getFormer();
                if (tmp == null) {
                    System.out.println("No commit with that id exists.");
                    return;
                }
                curr = serialRead(tmp);
            }
            done.add(tmp);
        }
        System.out.println("No commit with that id exists.");
    }

    public static void checkout3(String bname) {
        if (!branch.containsKey(bname)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (bname.equals(thisbranch)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        String sha = branch.get(bname);
        Commit nHead = serialRead(sha);
        scan();
        if (nHead.fileID != null) {
            for (String name : nHead.fileID.keySet()) {
                if (untrack.contains(name)) {
                    System.out.println("There is an untracked file in the way;"
                            + " delete it or add it first.");
                    return;
                }
            }
        }
        Commit oHead = serialRead(HEAD);
        if (oHead.fileID != null) {
            for (String name : oHead.fileID.keySet()) {
                try {
                    Files.delete(Paths.get(name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        nHead.setBranch(bname);
        serialWrite("head", nHead);
        if (nHead.fileID != null) {
            for (String name : nHead.fileID.keySet()) {
                try {
                    Files.copy(Paths.get(".gitlet/blobs/"
                                    + name + nHead.fileID.get(name)),
                            Paths.get(name), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        staged = new TreeSet<String>();
    }

    public static void branch(String bname) {
        if (branch.containsKey(bname)) {
            System.out.println("A branch with that name already exists.");
        } else {
            allCom.put(bname, HEAD);
            branch.put(bname, HEAD);
        }
    }

    public static void rmb(String bname) {
        if (!branch.containsKey(bname)) {
            System.out.println("A branch with that name does not exist.");
        } else if (thisbranch.equals(bname)) {
            System.out.println("Cannot remove the current branch.");
        } else {
            branch.remove(bname, HEAD);
        }
    }

    public static void reset(String id) {
        Commit found = null;
        String brn = null;
        HashSet<String> done = new HashSet<String>();
        for (String shatmp : allCom.values()) {
            if (done.contains(shatmp)) {
                continue;
            }
            Commit curr = serialRead(shatmp);
            String tmp = shatmp;
            while (curr.getFormer() != null || !done.contains(tmp)) {
                if (curr.getShaCode().equals(id) || curr.getShaCode().
                        substring(0, id.length()).equals(id)) {
                    found = curr;
                    brn = found.getBranch();
                    break;
                }
                done.add(tmp);
                tmp = curr.getFormer();
                if (tmp == null) {
                    break;
                }
                curr = serialRead(tmp);
            }
            done.add(tmp);
        }
        if (found == null) {
            System.out.println("No commit with that id exists.");
            return;
        }
        HEAD = found.getShaCode();
        scan();
        for (String name : found.fileID.keySet()) {
            if (untrack.contains(name)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it or add it first.");
                return;
            }
        }
        staged = new TreeSet<String>();
        removed = new TreeSet<String>();
        for (String name : found.fileID.keySet()) {
            checkout1(name, found);
            if (!found.fileID.containsKey(name)) {
                try {
                    Files.delete(Paths.get(name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        branch.put(brn, found.getShaCode());
        serialWrite("head", found);
    }

    public static void merge(String bname) {
        if (!branch.containsKey(bname)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        String splitPointSha = split(bname);
        if (branch.get(bname).equals(splitPointSha)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        } else if (bname.equals(thisbranch)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        } else if (removed.size() != 0 || staged.size() != 0) {
            System.out.println("You have uncommitted changes.");
            return;
        }
        scan();
        String branchSha = allCom.get(bname);
        Commit branchCom = serialRead(branchSha);
        String thisSha = HEAD;
        Commit thisCom = serialRead(HEAD);
        String currBranch = thisbranch;
        for (String name : branchCom.fileID.keySet()) {
            if (untrack.contains(name)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it or add it first.");
                return;
            }
        }
        if (thisSha.equals(splitPointSha)) {
            checkout3(bname);
            serialWrite("head", thisCom);
            HEAD = thisSha;
            thisbranch = currBranch;
            System.out.println("Current branch fast-forwarded.");
        }
        HashSet<String> difference = theDiff(serialRead(splitPointSha), thisCom, branchCom);
        for (String diff : difference) {
            mergeTheDiff(diff, thisCom, branchCom);
        }
        if (difference.size() > 0) {
            System.out.println("Encountered a merge conflict.");
        } else {
            GitMethod.commit("Merged " + currBranch + " with " + bname + ".");
        }
    }

    public static String split(String a) {
        HashSet<String> done = new HashSet<String>();
        String shaa = branch.get(a);
        Commit coma;
        while (shaa != null) {
            coma = serialRead(shaa);
            done.add(shaa);
            shaa = coma.getFormer();
        }
        String shab = branch.get(thisbranch);
        Commit comb;
        while (shab != null) {
            comb = serialRead(shab);
            if (done.contains(shab)) {
                return comb.getShaCode();
            }
            shab = comb.getFormer();
        }
        return null;
    }

    public static HashSet theDiff(Commit splitPoint, Commit thisCom, Commit branchCom) {
        HashSet<String> difference = new HashSet<String>();
        for (String name : branchCom.fileID.keySet()) {
            if (!splitPoint.fileID.containsKey(name)) {
                if (!thisCom.fileID.containsKey(name)) {
                    checkout2(branchCom.getShaCode(), name);
                    add(name);
                } else if (thisCom.fileID.containsKey(name)) {
                    difference.add(name);
                }
            } else if (splitPoint.fileID.containsKey(name)) {
                if (!splitPoint.fileID.get(name).equals(branchCom.fileID.get(name))
                        && (!thisCom.fileID.containsKey(name)
                        || !branchCom.fileID.get(name).equals(
                        splitPoint.fileID.get(name)))) {
                    difference.add(name);
                }
            }
        }
        for (String name : thisCom.fileID.keySet()) {
            if (splitPoint.fileID.containsKey(name)) {
                if (!branchCom.fileID.containsKey(name)
                        && splitPoint.fileID.get(name).equals(
                        thisCom.fileID.get(name))) {
                    rm(name);
                } else if (branchCom.fileID.containsKey(name)
                        && !splitPoint.fileID.get(name).equals(
                        branchCom.fileID.get(name))) {
                    difference.add(name);
                } else if (!branchCom.fileID.containsKey(name)
                        && !splitPoint.fileID.get(name).equals(
                        thisCom.fileID.get(name))) {
                    difference.add(name);
                }
            } else if (!splitPoint.fileID.containsKey(name)
                    && branchCom.fileID.containsKey(name)
                    && !branchCom.fileID.get(name).equals(
                    thisCom.fileID.get(name))) {
                difference.add(name);
            }
        }
        return difference;
    }

    public static void mergeTheDiff(String diff, Commit thisCom,
                                    Commit branchCom) {
        File output = new File(diff);
        File currFile = new File(".gitlet/blobs/" + diff + thisCom.fileID.get(diff));
        File givenFile = new File(".gitlet/blobs/" + diff + branchCom.fileID.get(diff));
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] head = "<<<<<<< HEAD\n".getBytes();
            outputStream.write(head);
            if (currFile.exists()) {
                byte[] currToWrite = Utils.readContents(currFile);
                outputStream.write(currToWrite);
            }
            byte[] divide = "=======\n".getBytes();
            outputStream.write(divide);
            if (givenFile.exists()) {
                byte[] givenToWrite = Utils.readContents(givenFile);
                outputStream.write(givenToWrite);
            }
            byte[] end = ">>>>>>>\n".getBytes();
            outputStream.write(end);
            byte[] toWrite = outputStream.toByteArray();
            Utils.writeContents(output, toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}