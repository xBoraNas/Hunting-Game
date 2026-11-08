package hunting;

/**
 * A model class representing a single square (field) on the game board.
 * It tracks whether the square is empty or occupied by a Fugitive or a Hunter.
 */
public class Field {
    
    /**
     * Enum representing the possible occupancy types of a field.
     */
    public enum Type { EMPTY, FUGITIVE, HUNTER }

    private Type type;

    /**
     * Constructs a default Field object with an EMPTY status.
     */
    public Field() {
        this.type = Type.EMPTY;
    }

    /**
     * Gets the current type of the field.
     *
     * @return the current {@link Type} of the field.
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the field.
     *
     * @param type the new {@link Type} to set for this field.
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Checks if the field is currently empty.
     *
     * @return {@code true} if the field type is EMPTY; {@code false} otherwise.
     */
    public boolean isEmpty() {
        return type == Type.EMPTY;
    }
}