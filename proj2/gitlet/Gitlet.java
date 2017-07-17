package gitlet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lifesaver on 16/07/2017.
 */
public class Gitlet {

    public Commit HEAD;

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(date);
    }

    public static void init() {
        File gitlet = new File(".gitlet");
        if (!gitlet.exists()) {
            gitlet.mkdir();

            File files = new File(".gitlet/Files");
            File addedFiles = new File(".gitlet/Files/addedFiles");
            File removedFiles = new File(".gitlet/Files/removedFiles");
            File f = new File(".gitlet/Files/FileManager.ser");
            File committedFiles = new File(".gitlet/committedFiles");
            File initialCommit = new File(".gitlet/committedFiles/Commit0");
            File commitAction = new File(".gitlet/committedFiles/CommitAction.ser");
            File commitInfo = new File(".gitlet/committedFiles/CommitInfo.ser");
            File branches = new File(".gitlet/branches");

            files.mkdir();
            removedFiles.mkdir();
            addedFiles.mkdir();
            committedFiles.mkdir();
            initialCommit.mkdir();
            branches.mkdir();
            Commit initCommit = new Commit(0, getDate(), "initial commit");
//
//            FileManager fileManager = new FileManager();
//            CommitAction commitA = new CommitAction();
//            CommitInfo commitI = new CommitInfo();
//            Branch branch = new Branch();
//            commitI.totalCommits.add(initCommit);
//            branch.newBranch("master", initCommit);
//            branch.setCurrentBranch("master");
//            GitletMethod.serialize(".gitlet/committedFiles/CommitAction.ser", commitA);
//            GitletMethod.serialize(".gitlet/committedFiles/CommitInfo.ser", commitI);
//            GitletMethod.serialize(".gitlet/committedFiles/Commit0/Commit0.ser", initCommit);
//            GitletMethod.serialize(".gitlet/branches/branches.ser", branch);
//            GitletMethod.serialize(".gitlet/Files/FileManager.ser", fileManager);
        } else {
            System.out.println("A gitlet version control system already exists in the current directory.");
        }
        return;
    }
}
