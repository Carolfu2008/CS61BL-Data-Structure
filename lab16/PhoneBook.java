import java.util.*;

public class PhoneBook {
    // TODO Add any instance variables necessary

    HashMap<Person, PhoneNumber> phoneBook = new HashMap();

    /*
     * Adds a person with this name to the phone book and associates 
     * with the given PhoneNumber.
     */
    public void addEntry(Person personToAdd, PhoneNumber numberToAdd) {
        if (personToAdd == null)
            return;
        phoneBook.put(personToAdd, numberToAdd);
    }

    /*
     * Access an entry in the phone book. 
     */
    public PhoneNumber getNumber(Person personToLookup) {
        if (personToLookup == null)
            return null;
        return phoneBook.get(personToLookup);
    }

}
