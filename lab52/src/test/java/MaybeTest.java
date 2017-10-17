import static org.junit.Assert.*;

/**
 * Created by myutman on 10/17/17.
 */
public class MaybeTest {

    /**
     * @throws Exception
     *
     * Checks that get returns correct values.
     */
    @org.junit.Test
    public void get() throws Exception {
        assertEquals(Maybe.just(5).get(), 5);
        assertEquals(Maybe.just("abacaba").get(), "abacaba");
    }

    /**
     * @throws Exception
     *
     * Checks that get returns correct values.
     */
    @org.junit.Test
    public void isPresent() throws Exception {
        assertEquals(Maybe.just(5).isPresent(), true);
        assertEquals(Maybe.just("abacaba").isPresent(), true);
        assertEquals(Maybe.nothing().isPresent(), false);
        assertEquals(Maybe.just(Maybe.nothing()).isPresent(), true);
    }

    /**
     * @throws Exception
     *
     * Checks that map returns correct values.
     */
    @org.junit.Test
    public void map() throws Exception {
        assertEquals(Maybe.just(5).map(x -> x * x * x).get(), 125);
        assertEquals(Maybe.just(8).map(x -> x * x * x).get(), 512);
        assertEquals(Maybe.just("abacaba").map(s -> s.substring(2, 4)).get(), "ac");
        Maybe<Integer> maybe = Maybe.nothing();
        assertEquals(maybe.map(x -> x * x * x).isPresent(), false);
        assertEquals(Maybe.just(Maybe.just(5)).map(x -> x.isPresent()).get(), true);
        assertEquals(Maybe.just(Maybe.nothing()).map(x -> x.isPresent()).get(), false);
    }

}