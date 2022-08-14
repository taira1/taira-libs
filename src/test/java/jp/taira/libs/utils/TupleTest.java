package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {

    @Test
    public void tuple3Test() throws Exception {
        { /* null */
            Tuple3<String, String, String> tuple = new Tuple3<>(null, null, null);
            assertNull(tuple.getValue1());
            assertNull(tuple.getValue2());
            assertNull(tuple.getValue3());
        }
        { /* String */
            Tuple3<String, String, String> tuple = new Tuple3<>("1", "2", "3");
            assertEquals(tuple.getValue1(), "1");
            assertEquals(tuple.getValue2(), "2");
            assertEquals(tuple.getValue3(), "3");
        }
        { /* 一部がnull */
            Tuple3<String, String, String> tuple = new Tuple3<>(null, "2", "3");
            assertNull(tuple.getValue1());
            assertEquals(tuple.getValue2(), "2");
            assertEquals(tuple.getValue3(), "3");
        }
        { /* 一部がnull */
            Tuple3<String, String, String> tuple = new Tuple3<>("1", null, "3");
            assertEquals(tuple.getValue1(), "1");
            assertNull(tuple.getValue2());
            assertEquals(tuple.getValue3(), "3");
        }
        { /* 一部がnull */
            Tuple3<String, String, String> tuple = new Tuple3<>("1", "2", null);
            assertEquals(tuple.getValue1(), "1");
            assertEquals(tuple.getValue2(), "2");
            assertNull(tuple.getValue3());
        }
    }
}
