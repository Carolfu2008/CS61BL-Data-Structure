package gitlet;

import java.io.File;

/**
 * Driver class for Gitlet, the tiny stupid version-control system.
 *
 * @author
 */

public class Main {

    public void check() {

    }

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
                } else {
                    System.out.println("Incorrect operands.");
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
                } else {
                    System.out.println("Incorrect operands.");
                }
                break;
            case "commit":
                if (args.length == 1) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                GitMethod.commit(args);
                break;
            default:
                System.out.println("No command with that name exists.");
                return;
        }
    }

}

