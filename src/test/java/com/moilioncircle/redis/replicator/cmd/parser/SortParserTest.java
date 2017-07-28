package com.moilioncircle.redis.replicator.cmd.parser;

import com.moilioncircle.redis.replicator.cmd.impl.SortCommand;
import org.junit.Test;

import static com.moilioncircle.redis.replicator.cmd.impl.OrderType.DESC;
import static com.moilioncircle.redis.replicator.cmd.impl.OrderType.NONE;
import static org.junit.Assert.assertEquals;

/**
 * @author Leon Chen
 * @since 2.3.1
 */
public class SortParserTest extends AbstractParserTest {
    @Test
    public void parse() throws Exception {

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("SORT mylist".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(NONE, cmd.getOrder());
            assertEquals(0, cmd.getGetPatterns().length);
            assertEquals(null, cmd.getAlpha());
            System.out.println(cmd);
        }

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("SORT mylist DESC".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(DESC, cmd.getOrder());
            assertEquals(0, cmd.getGetPatterns().length);
            assertEquals(null, cmd.getAlpha());
            System.out.println(cmd);
        }

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("SORT mylist ALPHA DESC".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(DESC, cmd.getOrder());
            assertEquals(0, cmd.getGetPatterns().length);
            assertEquals(true, cmd.getAlpha());
            System.out.println(cmd);
        }

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("SORT mylist ALPHA DESC limit 0 10".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(DESC, cmd.getOrder());
            assertEquals(0, cmd.getGetPatterns().length);
            assertEquals(true, cmd.getAlpha());
            assertEquals(0L, cmd.getLimit().getOffset());
            assertEquals(10L, cmd.getLimit().getCount());
            System.out.println(cmd);
        }

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("sort mylist alpha desc limit 0 10 by weight_*".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(DESC, cmd.getOrder());
            assertEquals(0, cmd.getGetPatterns().length);
            assertEquals(true, cmd.getAlpha());
            assertEquals(0L, cmd.getLimit().getOffset());
            assertEquals(10L, cmd.getLimit().getCount());
            assertEquals("weight_*", cmd.getByPattern());
            System.out.println(cmd);
        }

        {
            SortParser parser = new SortParser();
            SortCommand cmd = parser.parse(toObjectArray("sort mylist alpha desc limit 0 10 by weight_* get object_* get #".split(" ")));
            assertEquals("mylist", cmd.getKey());
            assertEquals(DESC, cmd.getOrder());
            assertEquals(true, cmd.getAlpha());
            assertEquals(0L, cmd.getLimit().getOffset());
            assertEquals(10L, cmd.getLimit().getCount());
            assertEquals("weight_*", cmd.getByPattern());
            assertEquals(2, cmd.getGetPatterns().length);
            assertEquals("object_*", cmd.getGetPatterns()[0]);
            assertEquals("#", cmd.getGetPatterns()[1]);
            System.out.println(cmd);
        }

    }

}