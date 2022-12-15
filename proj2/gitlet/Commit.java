package gitlet;

// any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Jade Zhu
 */
public class Commit implements Serializable {
    /**
     * treating string as a pointer (blob 2)
     */
    // read from my computer the head commit object and the staging area


    // clone the HEAD commit
    // modify its message and timestamp according to user input
    // use the staging area in order to modify the files tracked by the new commit

    // write back any new object made or any modified objects read earlier


    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** For commits
     * Combinations of log messages, other metadata (commit date, author, etc.),
     * a reference to a tree, and references to parent commits.
     * The repository also maintains a mapping from branch heads to references to commits,
     * so that certain important commits have symbolic names.
     */

    /** The message of this Commit. */
    private String message;
    private String timeStamp;
    private String ownHash; // create a unique identifier for each commit
    private String parentHash;
    private HashMap<String, String> blobs = new HashMap<>(); // <fileName, SHA1>
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");

    /** sth that keeps track of what files this commit is tracking */
    public Commit(String msg, HashMap<String, String> blobMap, String parent) {
        Date date = new Date();
        timeStamp = formatter.format(date);
        message = msg;
        parentHash = parent;
        blobs = blobMap;
        ownHash = calcHash();
        SerializeUtils.writeStringToFile(globalLog(), GLOBALLOG, true);
    }

    public String calcHash() {
        byte[] commitObj = SerializeUtils.toByteArray(this);
        return sha1(commitObj);
    }

    public String getOwnHash() {
        return ownHash;
    }

    public String getParentHash() {
        return parentHash;
    }


    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timeStamp;
    }

    public String getParent() {
        return this.parentHash;
    }
    public HashMap<String, String> getBlobs() {
        return blobs;
    }
    public String globalLog() {
        String firstLine = "===\n";
        String secondLine = "Commit " + ownHash + "\n";
        String thirdLine = timeStamp + "\n";
        String fourthLine = message + "\n";
        String fifthLine = "\n";
        return firstLine + secondLine + thirdLine + fourthLine + fifthLine;
    }
}
