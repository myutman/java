import java.util.function.Function;

/**
 * Created by myutman on 10/17/17.
 */
@SuppressWarnings("ALL")
public class Maybe<T> {

    private T value;
    boolean present;

    /**
     * @param value - just value
     *
     * Maybe.just constructor
     */
    public Maybe(T value){
        this.value = value;
        present = true;
    }

    /**
     * Maybe.nothing constructor
     */
    public Maybe(){
        value = null;
        present = false;
    }

    /**
     * @param t - value
     * @param <T> - value type
     * @return - present Maybe value
     *
     * Returns just a value.
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<T>(t);
    }

    /**
     * @param <T> - value type
     * @return - not present Maybe value
     *
     * Retunrs nothing.
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<T>();
    }

    /**
     * @return - present value of Maybe
     * @throws Exception - if value is not present
     *
     * Returns present value of Maybe or throws exception if value is not present.
     */
    public T get() throws Exception{
        if (!present)
            throw new NotPresentException();
        return value;
    }

    /**
     * @return - true if value is present and false otherwise
     *
     * Checks if value is present.
     */
    public boolean isPresent() { return present; }

    /**
     * @param mapper - function to be applied
     * @param <U> - type of function result
     * @return - Maybe with applied value
     *
     * Applies fucntion to present value of Maybe or returns nothing.
     */
    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper)  {
        if (!present) return new Maybe<U>();
        return new Maybe<U>(mapper.apply(this.value));
    }

}
