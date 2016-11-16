package fr.lpoyo.taupegun.utils.gui.arrays;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An array in 2 dimensions with x;y positions, useful for inventory (or gui) representations.<br>
 * The (0;0) point is the top right corner, like that:<br><br>
 * 0-------------->+x<br>
 * |<br>
 * |<br>
 * |<br>
 * |<br>
 * +y
 */
public interface Array2d<T> {
    /**
     *
     * @return The X length of the 2d array
     */
    int getLengthX();

    /**
     *
     * @return The Y length of the 2d array
     */
    int getLengthY();

    /**
     *
     * @param value The value to search
     * @return The position of the element searching as a {@link XYPosition}, or null if not contained in the array
     */
    XYPosition contains(T value);

    /**
     * Example:<br><br>
     * 0-X-X-X-X<br>
     * X-X-V-X-X<br>
     * X-X-X-X-X<br><br>
     * The value V comes at the 7th position in ordinal value.<br>
     * This method here would return <b>(2;1)</b>
     * @param ordinal The ordinal value
     * @return The xy position (coordinates) of the element which comes at the given ordinal
     */
    XYPosition fromOrdinal(int ordinal);

    /**
     *
     * @return The ordinal value of the element at the given position
     */
    int fromPosition(int x, int y);

    /**
     *
     * @return The object at the given (x;y) position in the array
     */
    T get(int x, int y);

    /**
     *
     * @return The element at the given ordinal value.
     * @see #fromOrdinal(int) More informations on difference between ordinal and position
     */
    T getOrdinal(int ordinal);

    /**
     *
     * @return Whether the given position is in the bounds of this array
     */
    boolean inBounds(int x, int y);

    /**
     * Removes a value from this array
     * @param value The value to remove
     * @return The position of the removed element, or null if the array did not contain it
     */
    XYPosition remove(T value);

    /**
     * Sets the value at the given (x;y) position in the array
     * @return The value which was set
     */
    T set(int x, int y, T value);

    /**
     * Useful for several set().set()..
     * @return The array itself
     * @see #set(int, int, Object)
     */
    Array2d setValue(int x, int y, T value);

    T[][] getArray();

    static void checkNotNegative(int... ints) {
        for (int i : ints) {
            if(i < 0)
                throw new IllegalArgumentException("Integer value cannot be negative !");
        }
    }

    static void checkEquivalentLength(Object[]... arrays) {
        for(int i = 0;i < arrays.length - 1;i++) {
            if(arrays[i].length != arrays[i + 1].length)
                throw new IllegalArgumentException("Arrays length must be equivalent !");
        }
    }

    /**
     * Unnecessary for most usages (unnecessary memory consumption really), except for return types
     */
    class XYPosition {
        public int x;
        public int y;

        public XYPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(421, 71).append(x).append(y).toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof XYPosition && ((XYPosition) obj).x == x && ((XYPosition) obj).y == y;
        }
    }
}
