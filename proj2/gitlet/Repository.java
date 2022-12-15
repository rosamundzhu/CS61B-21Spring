package gitlet;

import edu.princeton.cs.algs4.ST;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    private String HEAD = "master";
    private StagingArea stage;

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir")); // get the cwd, absolute path
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File BRANCHES = join(GITLET_DIR, "branches");
    public static final File BLOBS = join(GITLET_DIR, "blobs");
    public static final File STAGING = join(GITLET_DIR, "staging");
    public static final File LOGS = join(GITLET_DIR, "logs");
    public static final File COMMITS = join(GITLET_DIR, "commits");

    /** two files under branches directory */
    public static final File HEADPATH = join(BRANCHES, "HEAD.txt");
    public static final File MASTERPATH = join(BRANCHES, "master.txt");
    public static final File STAGE = join(STAGING, "stage.txt");
    public static final File GLOBALLOG = join(STAGING, "gl.txt");

    public Repository() {
        if (HEADPATH.exists()) {
            HEAD = SerializeUtils.readStringFromFile(HEADPATH);
        }
        if (STAGE.exists()) {
            stage = SerializeUtils.deserialize(STAGE, StagingArea.class);
        }
    }

    public void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        } else {
            GITLET_DIR.mkdir();
            BRANCHES.mkdir();
            BLOBS.mkdir();
            STAGING.mkdir();
            LOGS.mkdir();
            COMMITS.mkdir();
        }

        // Initializes default commit saved to /commits directory with SHA1 as name.
        Commit initialCommit = new Commit("initial commit", new HashMap<>(), null);
        File output = join(COMMITS, initialCommit.getOwnHash() + ".txt");
        SerializeUtils.storeObjectToFile(initialCommit, output);

        // Makes a master branch file in /branches with initial commit SHA1 String as contents.
        SerializeUtils.writeStringToFile(initialCommit.getOwnHash(), MASTERPATH, false);

        // Makes a HEAD text file in /branches, with the name of branch as contents.
        SerializeUtils.writeStringToFile("master", HEADPATH, false);

        // Makes a StagingArea Object with an empty HashMap of added and changed files,
        // as well as an empty ArrayList of removed files.
        stage = new StagingArea();
        SerializeUtils.storeObjectToFile(stage, STAGE);
    }

    public String getHEAD() {
        return HEAD;
    }

    public StagingArea getStage() {
        return stage;
    }

    //
    public void add(String fileName) { // while in reality it can add several files
        gitInitializedCheck();
        File toAdd = new File(fileName);
        File findFile = findFile(fileName, CWD);
        if (toAdd.exists()) {
            byte[] blob = Utils.readContents(toAdd);
            String blobHash = Utils.sha1(blob);
            if (getCurrentCommit().getBlobs().get(fileName) != null
                    && getCurrentCommit().getBlobs().get(fileName).equals(blobHash)) {
                if (stage.getRemovedFiles().contains(fileName)) {
                    stage.getRemovedFiles().remove(fileName);
                    SerializeUtils.storeObjectToFile(stage, STAGE);
                }
                return;
            }
            if (stage.getRemovedFiles().contains(fileName)) {
                stage.getRemovedFiles().remove(fileName);
            }
            Utils.writeContents(new File(BLOBS + blobHash + ".txt"), blob);
            stage.add(fileName, blobHash);
            SerializeUtils.storeObjectToFile(stage, STAGE);
        } else {
            System.out.print("File does not exist.");
        }
    }

    public void commitment(String msg) {
        if (stage.getAddedFiles().isEmpty() && stage.getRemovedFiles().isEmpty()) {
            System.out.print("No changes added to the commit.");
            return;
        } else if (msg.equals("")) {
            System.out.print("Please enter a commit message.");
            return;
        }
        Commit curr = getCurrentCommit();
        HashMap<String, String> copiedBlobs = (HashMap<String, String>) curr.getBlobs().clone();
        ArrayList<String> filesToAdd = new ArrayList<>(stage.getAddedFiles().keySet());
        for (String fileName : filesToAdd) {
            copiedBlobs.put(fileName, stage.getAddedFiles().get(fileName));
        }
        for (String fileToRemove : stage.getRemovedFiles()) {
            copiedBlobs.remove(fileToRemove);
        }
        Commit newC = new Commit(msg, copiedBlobs, curr.getOwnHash());

        SerializeUtils.writeStringToFile(newC.getOwnHash(), join(BRANCHES, HEAD + ".txt"), false);
        SerializeUtils.storeObjectToFile(newC, join(COMMITS, newC.getOwnHash() + ".txt"));
        stage.clear();
        SerializeUtils.storeObjectToFile(stage, STAGE);
    }

    private Commit getCurrentCommit() {
        String hash = SerializeUtils.readStringFromFile(join(BRANCHES,HEAD + ".txt"));
        return SerializeUtils.deserialize(join(COMMITS, hash + ".txt"), Commit.class);
    }

    private File findFile(String fileName, File CWD) {
        File[] fileList = CWD.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                if (f.getName().equals(fileName)) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * If a user inputs a command with the wrong number or format of operands
     *
     */
    private static void gitInitializedCheck() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    public void rm(String fileName) {
        boolean isStaged = stage.getAddedFiles().containsKey(fileName);
        Commit curr = getCurrentCommit();
        boolean isTracked = false;
        ArrayList<String> committedFiles = new ArrayList<>(curr.getBlobs().keySet());
        for (String f : committedFiles) {
            if (f.equals(fileName)) {
                isTracked = true;
            }
        }
        if (isTracked) {
            Utils.restrictedDelete(fileName);
            stage.addToRemovedFiles(fileName);
            if (isStaged) {
                stage.getAddedFiles().remove(fileName);
            }
            SerializeUtils.storeObjectToFile(stage, STAGE);
        } else if (isStaged) {
            stage.getAddedFiles().remove(fileName);
            SerializeUtils.storeObjectToFile(stage, STAGE);
        } else {
            System.out.print("No reason to remove the file.");
        }
    }

    public void log() {
        Commit curr = getCurrentCommit();
        while (curr != null) {
            System.out.println("===");
            System.out.println("Commit " + curr.getOwnHash());
            System.out.println(curr.getTimestamp());
            System.out.println(curr.getMessage());
            System.out.println();
            if (curr.getParentHash() != null) {
                curr = SerializeUtils.deserialize(join(COMMITS,curr.getParentHash() + ".txt"), Commit.class);
            } else {
                break;
            }
        }
    }

    public void status() {
        List<String> branches = new ArrayList<String>();
        File[] files1 = BRANCHES.listFiles();
        for (File file : files1) {
            branches.add(file.getName().substring(0, file.getName().length() - 4));
        }
        branches.remove("HEAD");
        branches.remove(HEAD);
        branches.add("*" + HEAD);
        Collections.sort(branches);
        List<String> stagedFiles = new ArrayList<String>();
        for (Map.Entry<String, String> entry : stage.getAddedFiles().entrySet()) {
            stagedFiles.add(entry.getKey());
        }
        Collections.sort(stagedFiles);
        List<String> remFiles = new ArrayList<String>();
        for (String file : stage.getRemovedFiles()) {
            remFiles.add(file);
        }
        Collections.sort(remFiles);

        System.out.println("=== Branches ===");
        for (String branch : branches) {
            System.out.println(branch);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String staged : stagedFiles) {
            System.out.println(staged);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String removed : remFiles) {
            System.out.println(removed);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
    }

    public void checkout(String... args) {
        if (args.length == 2) {
            String branchName = args[1];
            File branchPath = join(BRANCHES, branchName + ".txt");
            if (!branchPath.exists()) {
                System.out.println("No such branch exists.");
                return;
            }
            String newCommitID = SerializeUtils.readStringFromFile(branchPath);
            File commitPath = join(COMMITS, newCommitID + ".txt");
            Commit newCommit = SerializeUtils.deserialize(commitPath, Commit.class);
            ArrayList<File> fileList = new ArrayList<>();
            for (File f : CWD.listFiles()) {
                if (f.getName().endsWith(".txt")) {
                    fileList.add(f);
                }
            }
            Commit commitToCheckout = newCommit;
            Commit currCommit = getCurrentCommit();
            for (File f : fileList) {
                if (!currCommit.getBlobs().containsKey(f.getName())
                        && commitToCheckout.getBlobs().containsKey(f.getName())) {
                    System.out.println("There is an untracked file in the way; "
                            + "delete it or add it first.");
                    return;
                }
            }
            ArrayList<String> fileNames = new ArrayList<>(commitToCheckout.getBlobs().keySet());
            for (File f : fileList) {
                if (!commitToCheckout.getBlobs().containsKey(f.getName())
                        && currCommit.getBlobs().containsKey(f.getName())) {
                    Utils.restrictedDelete(f);
                }
            }
            for (String f : fileNames) {
                String blobHash = commitToCheckout.getBlobs().get(f);
                File newFile = join(BLOBS, blobHash + ".txt");
                byte[] blobBytes = Utils.readContents(newFile);
                Utils.writeContents(new File(f), blobBytes);
            }
            stage.clear();

            SerializeUtils.storeObjectToFile(stage, STAGE);
            SerializeUtils.writeStringToFile(branchName, HEADPATH, false);
        } else if (args.length == 3) {
            String fileName = args[2];
            Commit headCommit = getCurrentCommit();
            HashMap<String, String> commitMap = headCommit.getBlobs();
            if (!commitMap.containsKey(fileName)) {
                System.out.println("File does not exist in that commit.");
                return;
            }
            if ((new File(CWD.getPath() + fileName)).exists()) {
                Utils.restrictedDelete(CWD.getPath() + fileName);
            }

            File blob = join(BLOBS, headCommit.getBlobs().get(fileName) + ".txt");
            byte[] storeRFile = Utils.readContents(blob);
            File newFile = new File(CWD.getPath(), fileName);
            Utils.writeContents(newFile, storeRFile);
        } else if (args.length == 4) {
            String commitID = args[1];
            String fileName = args[3];
            String[] possibleCommit = COMMITS.list();
            for (String identifier : possibleCommit) {
                if (identifier.contains(commitID)) {
                    commitID = identifier;
                    commitID = commitID.substring(0, commitID.length() - 4);
                    break;
                }
            }
            Commit currCommit = SerializeUtils.deserialize(join(COMMITS,commitID + ".txt"), Commit.class);
            if (currCommit == null) {
                System.out.println("No commit with that id exists.");
            } else if (!currCommit.getBlobs().containsKey(fileName)) {
                System.out.println("File does not exist in that commit.");
            } else {
                if ((new File(CWD.getPath() + fileName)).exists()) {
                    Utils.restrictedDelete(CWD.getPath() + fileName);
                }
                File newFile = new File(CWD.getPath(), fileName);

                File blob = join(BLOBS, currCommit.getBlobs().get(fileName) + ".txt");
                byte[] storeRFile = Utils.readContents(blob);
                Utils.writeContents(newFile, storeRFile);
            }
        }
    }

    public void branch(String branchName) {
        File branchFile = join(BRANCHES, branchName + ".txt");
        if (branchFile.exists()) {
            System.out.print("A branch with that name already exists.");
            return;
        }
        String sha1 = SerializeUtils.readStringFromFile(join(BRANCHES, HEAD + ".txt"));
        SerializeUtils.writeStringToFile(sha1, branchFile, false);
    }

    public void rmb(String branchName) {
        if (branchName.equals(SerializeUtils.readStringFromFile(HEADPATH))) {
            System.out.print("Cannot remove the current branch.");
            return;
        }
        File branchFile = join(BRANCHES, branchName + ".txt");
        if (!branchFile.delete()) {
            System.out.print("A branch with that name does not exist.");
        }
    }

    public void merge(String bName) {
        boolean conflict = false;
        if (mergeHelper1(bName)) {
            return;
        }
        ArrayList<File> fileList = new ArrayList<>();
        for (File f : CWD.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                fileList.add(f);
            }
        }
        File bNamePath = join(BRANCHES, bName + ".txt");
        String bCommitID = SerializeUtils.readStringFromFile(bNamePath);
        File bCommitIDPath = join(COMMITS, bCommitID + ".txt");
        Commit cCom = getCurrentCommit();
        Commit bCom = SerializeUtils.deserialize(bCommitIDPath, Commit.class);
        HashMap<String, Commit> cCommitTree = new HashMap<>();
        Commit cComPtr = cCom;
        Commit bComPtr = bCom;
        Commit sPnt = null;
        for (File f : fileList) {
            if (!cCom.getBlobs().containsKey(f.getName())
                    && bCom.getBlobs().containsKey(f.getName())) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it or add it first.");
                return;
            }
        }
        File cComPtrPath = join(COMMITS, cComPtr.getOwnHash() + ".txt");
        File cComPtrParePath = join(COMMITS, cComPtr.getParentHash() + ".txt");
        File bComPtrPath = join(COMMITS, bComPtr.getOwnHash() + ".txt");
        File bComPtrParePath = join(COMMITS, bComPtr.getParentHash() + ".txt");
        while (cComPtr != null && SerializeUtils.deserialize(cComPtrPath, Commit.class) != null) {
            cCommitTree.put(cComPtr.getOwnHash(), SerializeUtils.deserialize(cComPtrPath, Commit.class));

            cComPtr = SerializeUtils.deserialize(cComPtrParePath, Commit.class);
        }
        while (bComPtr != null && SerializeUtils.deserialize(bComPtrPath, Commit.class) != null) {
            if (cCommitTree.containsKey(bComPtr.getOwnHash())) {
                sPnt = cCommitTree.get(bComPtr.getOwnHash());
                break;
            }
            bComPtr = SerializeUtils.deserialize(bComPtrParePath, Commit.class);
        }
        if (sPnt == null) {
            System.out.println("There was an error finding the split point.");
        }
        if (sPnt.getOwnHash().equals(cCom.getOwnHash())) {
            File currBranch = join(BRANCHES, HEAD + ".txt");
            SerializeUtils.writeStringToFile(bCommitID, currBranch, false);
            System.out.println("Current branch fast-forwarded.");
            return;
        } else if (cCommitTree.containsKey(bCommitID)) {
            System.out.print("Given branch is an ancestor of the current branch.");
            return;
        }
        for (String fName : cCom.getBlobs().keySet()) {
            String bBH = bCom.getBlobs().get(fName);
            String cBH = cCom.getBlobs().get(fName);
            if (sPnt.getBlobs().containsKey(fName) && bCom.getBlobs().containsKey(fName)) {
                String sPBH = sPnt.getBlobs().get(fName);
                if (!sPBH.equals(bBH) && sPBH.equals(cBH)) {
                    checkout("checkout", bCommitID, "--", fName);
                    add(fName);
                    SerializeUtils.storeObjectToFile(stage, STAGE);
                }
                if (!sPBH.equals(bBH) || !sPBH.equals(cBH) || !bBH.equals(cBH)) {
                    mergeHelper3(cBH, fName, bBH);
                    conflict = true;
                }
            } else if (!bCom.getBlobs().containsKey(fName) && sPnt.getBlobs().containsKey(fName)
                    && !sPnt.getBlobs().get(fName).equals(cCom.getBlobs().get(fName))) {
                mergeHelper2(cBH, fName, bBH);
                conflict = true;
            }
        }
        ArrayList<String> splitFiles = new ArrayList<>(sPnt.getBlobs().keySet());
        ArrayList<String> givenBranchFiles = new ArrayList<>(bCom.getBlobs().keySet());
        for (String theFile : givenBranchFiles) {
            if (!splitFiles.contains(theFile)) {
                checkout("checkout", bCommitID, "--", theFile);
                add(theFile);
            }
        }
        for (String theFile : splitFiles) {
            if (cCom.getBlobs().containsKey(theFile)) {
                if (sPnt.getBlobs().get(theFile).equals(cCom.getBlobs().get(theFile))
                        && !givenBranchFiles.contains(theFile)) {
                    rm(theFile);
                }
            }
        }
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
            return;
        } else {
            commitment("Merged " + HEAD + " with " + bName + ".");
        }
    }

    public boolean mergeHelper1(String bName) {
        File bPath = join(BRANCHES, bName + ".txt");
        if (!stage.getAddedFiles().isEmpty() || !stage.getRemovedFiles().isEmpty()) {
            System.out.println("You have uncommitted changes.");
            return true;
        } else if (!bPath.exists()) {
            System.out.println("A branch with that name does not exist.");
            return true;
        } else if (bName.equals(HEAD)) {
            System.out.println("Cannot merge a branch with itself.");
            return true;
        }
        return false;
    }

    public void mergeHelper2(String cBH, String fName, String bBH) {
        File merge = new File(CWD.getPath() + "/" + fName);
        File cBHPath = join(BLOBS, cBH + ".txt");
        byte[] everything = addStuff("<<<<<<< HEAD\n".getBytes(StandardCharsets.UTF_8), Utils.readContents(cBHPath));
        everything = addStuff(everything, "=======\n".getBytes(StandardCharsets.UTF_8));
        everything = addStuff(everything, ">>>>>>>\n".getBytes(StandardCharsets.UTF_8));
        Utils.writeContents(merge, everything);
    }

    private byte[] addStuff(byte[] addThisStuff, byte[] newStuffs) {
        byte[] endResult = new byte[addThisStuff.length + newStuffs.length];
        System.arraycopy(addThisStuff, 0, endResult, 0, addThisStuff.length);
        System.arraycopy(newStuffs, 0, endResult, addThisStuff.length, newStuffs.length);
        return endResult;
    }

    public void mergeHelper3(String cBH, String fName, String bBH) {
        File merge = new File(CWD.getPath() + "/" + fName);
        File cBHPath = join(BLOBS, cBH + ".txt");
        byte[] everything = addStuff("<<<<<<< HEAD\n".getBytes(StandardCharsets.UTF_8),
                Utils.readContents(cBHPath));
        everything = addStuff(everything, "=======\n".getBytes(StandardCharsets.UTF_8));
        everything = addStuff(everything, Utils.readContents(
                new File(".gitlet/blobs/" + bBH + ".txt")));
        everything = addStuff(everything, ">>>>>>>\n".getBytes(StandardCharsets.UTF_8));
        Utils.writeContents(merge, everything);
    }

    public void reset(String commitID) {

        Commit commitToCheckout = SerializeUtils.deserialize(join(COMMITS, commitID + ".txt"), Commit.class);
        if (commitToCheckout == null) {
            System.out.println("No commit with that id exists.");
            return;
        }
        ArrayList<File> fileList = new ArrayList<>();
        for (File f : CWD.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                fileList.add(f);
            }
        }
        Commit currCommit = getCurrentCommit();
        for (File f : fileList) {
            if (!currCommit.getBlobs().containsKey(f.getName())
                    && commitToCheckout.getBlobs().containsKey(f.getName())) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it or add it first.");
                return;
            }
        }
        ArrayList<String> fileNames = new ArrayList<>(commitToCheckout.getBlobs().keySet());
        for (File f : fileList) {
            if (!commitToCheckout.getBlobs().containsKey(f.getName())
                    && currCommit.getBlobs().containsKey(f.getName())) {
                Utils.restrictedDelete(f);
            }
        }
        for (String f : fileNames) {
            String blobHash = commitToCheckout.getBlobs().get(f);
            File newFile = join(BLOBS, blobHash + ".txt");
            byte[] blobBytes = Utils.readContents(newFile);
            Utils.writeContents(new File(f), blobBytes);
        }
        stage.clear();
        SerializeUtils.storeObjectToFile(stage, STAGE);
        SerializeUtils.writeStringToFile(commitID, join(BRANCHES, HEAD + ".txt"), false);
    }
}
