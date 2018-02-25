package edu.northwestern.socialmedia.hestondissertationtest;

/**
 * Created by matthewheston on 2/24/18.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class WebPosterTest {

    @Test
    public void anonymizeSingleWordName() {
        String name = "Mom";
        String anonymized = WebPoster.AnonymizeName(name);
        assertEquals(name, anonymized);
    }

    @Test
    public void anonymizeDoubleWordName() {
        String name = "Matthew Heston";
        String anonymized = WebPoster.AnonymizeName(name);
        assertEquals("Matthew H", anonymized);
    }

    @Test
    public void anonymizeTripleleWordName() {
        String name = "Matthew Robert Heston";
        String anonymized = WebPoster.AnonymizeName(name);
        assertEquals("Matthew R", anonymized);
    }
}
