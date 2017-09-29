package decisiontrees;

/**
 * A generic class representing a pair of objects.
 * @param <T> The type of the first element
 * @param <S> The type of the second element
 *
 * @author gorosgobe
 */
public class Pair<T, S> {

    /** The first element*/
    private final T first;
    /** The second element*/
    private final S second;

    /**
     * Constructs a pair.
     * @param first the first element
     * @param second the second element
     */
    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first element
     * @return the first element
     */
    public T getFirst() {
        return first;
    }

    /**
     * Gets the second element
     * @return the second element
     */
    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
