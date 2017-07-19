package gitlet;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author
 */

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        GitMethod.startUp();
        switch (args[0]) {
            case "init":
                if (args.length == 1) {
                    GitMethod.init();
                }
                break;
        }
    }
}
