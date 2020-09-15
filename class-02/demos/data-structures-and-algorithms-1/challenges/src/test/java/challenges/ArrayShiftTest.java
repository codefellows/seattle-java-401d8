package challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayShiftTest {
    @Test
    public void testSomeLibraryMethod() {
        ArrayShift classUnderTest = new ArrayShift();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }

    @Test public void testReturnsTwo(){
        ArrayShift arrayShift = new ArrayShift();
        assertEquals(2, arrayShift.makeTwo());
    }
}
