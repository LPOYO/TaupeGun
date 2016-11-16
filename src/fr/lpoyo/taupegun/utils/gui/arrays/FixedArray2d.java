package fr.lpoyo.taupegun.utils.gui.arrays;

/**
 * Created by Corentin on 15/08/2016.<br>
 * The size is given on creation and may not be changed.
 */
public class FixedArray2d<T> implements Array2d<T>{

    //first index is x pos, 2nd is y pos
    protected T[][] array;

    @SuppressWarnings("unchecked")
    public FixedArray2d(int sizeX, int sizeY) {
        this.array = (T[][]) new Object[sizeX][sizeY];
    }

    @Override
    public int getLengthX() {
        return array.length;
    }

    @Override
    public int getLengthY() {
        return array[0].length;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public XYPosition contains(T value) {
        for(int i = 0;i < array.length;i++) {
            for(int i1 = 0;i1 < array[i].length;i1++) {
                if(get(i, i1).equals(value))
                    return new XYPosition(i, i1);
            }
        }
        return null;
    }

    @Override
    public XYPosition fromOrdinal(int ordinal) {
        int quo = Double.valueOf((double) ordinal / (double) getLengthX()).intValue();
        int rem = ordinal % getLengthX();
        return new XYPosition(rem, quo);
    }

    @Override
    public int fromPosition(int x, int y) {
        Array2d.checkNotNegative(x, y);
        return y * getLengthX() + x;
    }

    @Override
    public T get(int x, int y) {
        if(!inBounds(x, y))
            throw new ArrayIndexOutOfBoundsException("Cannot get value from an out of bounds position !");
        return array[x][y];
    }

    @Override
    public T getOrdinal(int ordinal) {
        XYPosition pos = fromOrdinal(ordinal);
        return get(pos.x, pos.y);
    }

    @Override
    public boolean inBounds(int x, int y) {
        Array2d.checkNotNegative(x, y);
        return array.length > x && array[x].length > y;
    }

    @Override
    public XYPosition remove(T value) {
        XYPosition pos = contains(value);
        if(pos == null)
            return null;
        set(pos.x, pos.y, null);

        return pos;
    }

    @Override
    public T set(int x, int y, T value) {
        if(!inBounds(x, y))
            throw new ArrayIndexOutOfBoundsException("Cannot set value on an out of bounds position !");
        array[x][y] = value;
        return value;
    }

    @Override
    public Array2d setValue(int x, int y, T value) {
        set(x, y, value);
        return this;
    }

    @Override
    public T[][] getArray() {
        return array;
    }
}
