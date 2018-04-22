package com.rprtr258;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void serializeTest() throws IOException {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        OutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        assertEquals("(a(b_(b_()c_())))_", out.toString());
    }

    @Test
    public void deserializeWithEmptyStringTest() throws IOException {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        InputStream in = new ByteArrayInputStream("(a(b_(b_()c_())))_".getBytes());
        trie.deserialize(in);
        assertTrue(trie.contains(""));
        assertTrue(trie.contains("abc"));
        assertTrue(trie.contains("abb"));
        assertTrue(trie.contains("ab"));
        assertEquals(4, trie.size());
    }

    @Test
    public void deserializeWithoutEmptyStringTest() throws IOException {
        Trie trie = new Trie();
        InputStream in = new ByteArrayInputStream("(a(b_(b_()c_())))".getBytes());
        trie.deserialize(in);
        assertTrue(trie.contains("abc"));
        assertTrue(trie.contains("abb"));
        assertTrue(trie.contains("ab"));
        assertEquals(3, trie.size());
    }

    @Test
    public void toStringTest() {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        assertEquals("(a(b_(b_()c_())))_", trie.toString());
        trie.remove("");
        assertEquals("(a(b_(b_()c_())))", trie.toString());
    }

    @Test
    public void addTest() {
        Trie trie = new Trie();
        assertTrue(trie.add(""));
        assertFalse(trie.add(""));
        assertFalse(trie.add(""));
        assertTrue(trie.add("abc"));
        assertTrue(trie.add("abb"));
        assertTrue(trie.add("ab"));
        assertFalse(trie.add("ab"));
        assertEquals(4, trie.size());
    }

    @Test
    public void containsTest() {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        assertTrue(trie.contains(""));
        assertTrue(trie.contains("ab"));
        assertTrue(trie.contains("abc"));
        assertTrue(trie.contains("abb"));
        assertFalse(trie.contains("a"));
    }

    @Test
    public void howManyStartsWithPrefixTest() {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        trie.add("ac");
        assertEquals(5, trie.howManyStartWithPrefix(""));
        assertEquals(4, trie.howManyStartWithPrefix("a"));
        assertEquals(3, trie.howManyStartWithPrefix("ab"));
        assertEquals(1, trie.howManyStartWithPrefix("abc"));
        assertEquals(0, trie.howManyStartWithPrefix("b"));
    }

    @Test
    public void removeTest() {
        Trie trie = new Trie();
        trie.add("");
        trie.add("abc");
        trie.add("abb");
        trie.add("ab");
        trie.add("ac");
        assertTrue(trie.remove("abb"));
        assertFalse(trie.remove("bozheUdoliTelegram"));
        assertTrue(trie.remove(""));
        assertFalse(trie.remove(""));
        assertEquals(3, trie.size());
    }
}
