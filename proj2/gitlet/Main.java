package gitlet;

import java.io.File;

/**
 * Driver class for Gitlet, the tiny stupid version-control system.
 *
 * @author
 */

public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND> Not in an initialized gitlet directory.....
     */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        GitMethod.checkPreviously();
        switch (args[0]) {
            case "init":
                if (args.length == 1) {
                    GitMethod.init();
                }
                break;
            case "add":
                File repo = new File(".gitlet");
                if (!repo.exists()) {
                    System.out.println("Not in an initialized gitlet directory.");
                    return;
                }
                if (args.length == 2) {
                    GitMethod.add(args[1]);
                }
                break;
            case "commit":
                if (args.length == 1 || args[1].trim().equals("")) {
                    System.out.println("Please enter a commit message.");
                    return;
                } else if (args.length == 2) {
                    GitMethod.commit(args[1]);
                }
                break;
            case "rm":
                if (args.length == 2) {
                    GitMethod.rm(args[1]);
                }
                break;
            case "log":
                if (args.length == 1) {
                    GitMethod.log();
                }
                break;
            case "global-log":
                if (args.length == 1) {
                    GitMethod.glog();
                }
                break;
            case "find":
                if (args.length == 2) {
                    GitMethod.find(args[1]);
                } else {
                    System.out.println("Incorrect operands.");
                    return;
                }
                break;
            case "status":
                GitMethod.status();
                break;
            case "checkout":
                GitMethod.checkout(args);
                break;
            case "branch":
                GitMethod.branch(args[1]);
                break;
            case "rm-branch":
                GitMethod.rmb(args[1]);
                break;
            case "reset":
                GitMethod.reset(args[1]);
                break;
            case "merge":
                GitMethod.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                return;
        }
        GitMethod.checkAfterwards();
    }

}

