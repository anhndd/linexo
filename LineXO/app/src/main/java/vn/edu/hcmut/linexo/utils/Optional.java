package vn.edu.hcmut.linexo.utils;

/**
 * A container object which may or may not contain a non-{@code null} value.
 * If a value is present, {@link #isPresent()} returns {@code true} and
 * {@link #get()} returns the value.
 * @param <T> the type of value.
 */

public final class Optional<T> {

    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> empty() {
        return new Optional<>(null);
    }

    public static <T> Optional<T> of(T value){
        if (value == null) {
            return Optional.empty();
        }
        return new Optional<>(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return value;
    }
}
