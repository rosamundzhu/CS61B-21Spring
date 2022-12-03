package capers;

import java.io.File;
import java.io.Serializable;

import static capers.Utils.*;

/** Represents a dog that can be serialized.
 * @author Jade Zhu
*/
public class Dog implements Serializable {

    /** Folder that dogs live in. */
    // joins together strings or files into a path.
    // E.g. Utils.join(".capers", "dogs") would give you a File object with the path of “.capers/dogs”
    static final File DOG_FOLDER = join(CapersRepository.CAPERS_FOLDER, "dogs");

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // loading a Dog from your filesystem instead of writing it
        return readObject(join(DOG_FOLDER, name), Dog.class);
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(this);
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        File dogFile = join(DOG_FOLDER, name);
        if (dogFile.exists()) { // don't forget dog names are unique
            dogFile.delete();
        }
        // Serializing the Model object
        writeObject(dogFile, this);
        // since Dogs aren’t Strings so we want to be able to serialize them.
        // Make sure you’re writing your dog to a File object that represents a file and not a folder!
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
