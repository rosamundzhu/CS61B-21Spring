package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     *  java gitlet.Main add hell0.txt
     */
    public static void main(String[] args) {

        // what if args is empty?
        if (args == null) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        // TODO:
        //  If a user inputs a command that requires being in an initialized Gitlet working directory
        //  (i.e., one containing a .gitlet subdirectory),
        //  but is not in such a directory, print the message Not in an initialized Gitlet directory.

        Repository r = new Repository();
        int inputLength = args.length;
        if (inputLength == 0) {
            System.out.println("Please enter a command.");
        } else {
            String firstArg = args[0];
            switch(firstArg) {
                case "init":
                    validateNumArgs(args, 1);
                    r.init();
                    break;
                case "add":
                    validateNumArgs(args, 2);
                    r.add(args[1]);
                    break;
                case "commit":
                    validateNumArgs(args, 2);
                    r.commitment(args[1]);
                    break;
                case "rm":
                    validateNumArgs(args, 2);
                    r.rm(args[1]);
                    break;
                case "log":
                    validateNumArgs(args, 1);
                    r.log();
                    break;
                case "status":
                    validateNumArgs(args, 1);
                    r.status();
                    break;
                case "checkout": {
                    if (args.length != 2 && args.length != 3 && args.length != 4) {
                        System.out.println("Incorrect Operands");
                    } else if ((args.length == 4 && !args[2].equals("--"))
                            || (args.length == 3 && !args[1].equals("--"))) {
                        System.out.println("Incorrect Operands");
                    } else {
                        r.checkout(args);
                    }
                    break;
                }
                case "branch": {
                    validateNumArgs(args, 2);
                    r.branch(args[1]);
                    break;
                }
                case "rm-branch": {
                    validateNumArgs(args, 2);
                    r.rmb(args[1]);
                    break;
                }
                case "merge": {
                    validateNumArgs(args, 2);
                    r.merge(args[1]);
                    break;
                }
                case "reset": {
                    validateNumArgs(args, 2);
                    r.reset(args[1]);
                    break;
                }
                default:
                    System.out.println("No command with that name exists.");
                    break;
            }
        }
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}
