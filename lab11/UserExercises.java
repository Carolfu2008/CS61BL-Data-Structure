import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserExercises extends DBTable<User> {
    UserExercises() {
    }

    UserExercises(Collection<User> lst) {
        super(lst);
    }

    /**
     * Get an ordered List of Users, sorted first on age,
     * then on their id if the age is the same.
     */
    public List<User> getOrderedByAgeThenId() {
        return getOrderedBy(User::getAge).stream()
                .sorted((a, b) -> (a.getId() < b.getId() ? a.getId() : b.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Get the average age of all the users.
     * If there are no users, the average is 0.
     */
    public double getAverageAge() {
        return getEntries().stream()
                .mapToInt(a -> a.getAge())
                .average()
                .orElse(0);
        // HINT: You may find an IntStream helpful 
    }

    /**
     * Group usernames by user age, for all users that have an age greater than min_age.
     * Usernames with ages less than or equal to min_age are excluded.
     * Returns a Map from each age present to a list of the usernames that have that age.
     */
    public Map<Integer, List<String>> groupUsernamesByAgeOlderThan(int min_age) {
        return getEntries().stream()
                .filter(a -> a.getAge() > min_age)
                .collect(Collectors.groupingBy(a -> a.getAge(),
                        Collectors.mapping(a -> a.getUsername(), Collectors.toList())));
        // HINT: See the Additional Examples for a helpful Collector
        // HINT2: You will need to use Collectors.mapping. See the Javadocs for examples
    }
}
