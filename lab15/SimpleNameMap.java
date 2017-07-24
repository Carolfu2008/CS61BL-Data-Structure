import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple mapping from string names to string values backed by an array.
 * Supports only A-Z for the first character of the key name. Values can be
 * any valid string.
 *
 * @author ZihengNi
 */
public class SimpleNameMap {

    /**
     * A wrapper class for holding each (KEY, VALUE) pair.
     */
    ArrayList<LinkedList<Entry>> mapArray;
    int size;
    int count;

    public SimpleNameMap() {
        size = 10;
        mapArray = new ArrayList<>(size);
        count = 0;
    }

    /**
     * Returns true if the map contains the KEY.
     */
    boolean containsKey(String key) {
        if (isValidName(key)) {
            int index = (key.hashCode() & 0x7FFFFFFF) % size;
            for (Entry e : mapArray.get(index)) {
                if (e._key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns the value for the specified KEY.
     */
    String get(String key) {
        if (isValidName(key)) {
            int index = (key.hashCode() & 0x7FFFFFFF) % size;
            for (Entry e : mapArray.get(index)) {
                if (e._key.equals(key)) {
                    return e._value;
                }
            }
        }
        return null;
    }

    /**
     * Put a (KEY, VALUE) pair into this map.
     */
    void put(String key, String value) {
        if (isValidName(key)) {
            if (count / size < size) {
                int index = (key.hashCode() & 0x7FFFFFFF) % size;
                mapArray.get(index).add(new Entry(key, value));
                count++;
            } else {
                resize();
                int index = (key.hashCode() & 0x7FFFFFFF) % size;
                mapArray.get(index).add(new Entry(key, value));
                count++;
            }
        }
    }

    void resize() {
        ArrayList<LinkedList<Entry>> newMapArray = new ArrayList<>(size * 2);
        for (int i = 0; i < mapArray.size(); i++) {
            newMapArray.set(i, mapArray.get(i));
        }
        mapArray = newMapArray;
        size *= 2;
    }

    /**
     * Remove a single entry, KEY, from this table and returns true if successful.
     */
    String remove(String key) {
        if (isValidName(key)) {
            int index = (key.hashCode() & 0x7FFFFFFF) % size;
            for (Entry e : mapArray.get(index)) {
                if (e._key.equals(key)) {
                    mapArray.get(index).remove(e);
                    return "true";
                }
            }
        }
        return "false";
    }

    private static class Entry {

        /**
         * The key used for lookup.
         */
        private String _key;
        /**
         * The associated value.
         */
        private String _value;

        /**
         * Create a new (KEY, VALUE) pair.
         */
        public Entry(String key, String value) {
            _key = key;
            _value = value;
        }

        /**
         * Returns true if this key matches with the OTHER's key.
         */
        public boolean keyEquals(Entry other) {
            return _key.equals(other._key);
        }

        /**
         * Returns true if both the KEY and the VALUE match.
         */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry &&
                    _key.equals(((Entry) other)._key) &&
                    _value.equals(((Entry) other)._value));
        }

    }

    /**
     * Returns true if the given KEY is a valid name that starts with A-Z.
     */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

}