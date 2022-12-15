package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StagingArea implements Serializable {
    private HashMap<String, String> addedFiles;
    private ArrayList<String> removedFiles;

    public StagingArea() {
        addedFiles = new HashMap<>();
        removedFiles = new ArrayList<>();
    }

    public Collection<String> getRemovedFiles() {
        return removedFiles;
    }

    public void add(String fileName, String blobHash) {
        addedFiles.put(fileName, blobHash);
    }

    public HashMap<String, String> getAddedFiles() {
        return addedFiles;
    }

    public void addToRemovedFiles(String fileName) {
        removedFiles.add(fileName);
    }

    public void clear() {
        addedFiles = new HashMap<>();
        removedFiles = new ArrayList<>();
    }
}
