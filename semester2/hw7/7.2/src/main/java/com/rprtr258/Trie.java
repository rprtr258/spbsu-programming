package com.rprtr258;

import java.io.*;

/**
 * Trie data structure.
 * <a href = "http://neerc.ifmo.ru/wiki/index.php?title=%D0%91%D0%BE%D1%80">Wiki</a>
 */
public class Trie {
    private Node root = new Node();

    /**
     * Empty constructor.
     */
    public Trie() {}

    /**
     * Adds string to trie.
     * @param element string to be added.
     * @return true if string wasn't in trie.
     */
    public boolean add(String element) {
        if ("".equals(element)) {
            if (root.isTerminal)
                return false;
            root.isTerminal = true;
            root.leaves++;
            return true;
        }
        return root.add(element, 0);
    }

    /**
     * Checks whether string is in trie or not.
     * @param element string to be checked.
     * @return true if string is in trie.
     */
    public boolean contains(String element) {
        if ("".equals(element))
            return root.isTerminal;
        return root.contains(element, 0);
    }

    /**
     * Removes string from trie.
     * @param element string to be removed.
     * @return true if it was in trie(don't know for what because it is returned by <i>contains</i> also).
     */
    public boolean remove(String element) {
        if ("".equals(element)) {
            if (root.isTerminal) {
                root.isTerminal = false;
                root.leaves--;
                return true;
            }
            return false;
        }
        boolean was = root.contains(element, 0);
        root.remove(element, 0);
        return was;
    }

    /**
     * Returns size of trie.
     * @return size of trie.
     */
    public int size() {
        return root.leaves;
    }

    /**
     * Counts how many strings in trie start with given prefix.
     * @param prefix given prefix.
     * @return number of such strings.
     */
    public int howManyStartWithPrefix(String prefix) {
        if ("".equals(prefix))
            return root.leaves;
        return root.howManyStartWithPrefix(prefix, 0);
    }

    /**
     * Puts serialized trie into stream.
     * @param out stream that will be used.
     * @throws IOException if stream throws it.
     */
    public void serialize(OutputStream out) throws IOException {
        String serialized = toString();
        out.write(serialized.getBytes());
    }

    /**
     * Restores trie from stream.
     * @param in stream that will be read.
     * @throws IOException if stream suddenly throws it.
     */
    public void deserialize(InputStream in) throws IOException {
        int bytesCount = in.available();
        byte[] bytes = new byte[bytesCount];
        assert in.read(bytes, 0, bytesCount) == bytesCount;
        String serialized = new String(bytes);
        root = new Node();
        root.restore(serialized, 0);
        root.fixLeaves();
    }

    /**
     * Converts trie to string in specific format.
     * @return string representation of trie.
     */
    @Override
    public String toString() {
        String result = root.asString();
        if (root.isTerminal)
            result = "(" + result + ")_";
        else
            result = "(" + result + ")";
        return result;
    }

    private class Node {
        private Node[] child = new Node[26];
        private int leaves = 0;
        private boolean isTerminal = false;

        private String asString() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (child[i] != null) {
                    result.append(String.format("%c", i + 'a'));
                    if (child[i].isTerminal)
                        result.append("_");
                    result.append("(");
                    result.append(child[i].asString());
                    result.append(")");
                }
            }
            return result.toString();
        }

        private Node() {
            for (int i = 0; i < child.length; i++)
                child[i] = null;
        }

        private boolean add(String element, int i) {
            if (i == element.length()) {
                leaves++;
                isTerminal = true;
                return true;
            }
            int code = element.charAt(i) - 'a';
            if (i == element.length() - 1 && child[code] != null && child[code].isTerminal)
                return false;
            if (child[code] == null)
                child[code] = new Node();
            boolean result = child[code].add(element, i + 1);
            if (result)
                leaves++;
            return result;
        }

        private boolean contains(String element, int i) {
            if (i == element.length())
                return isTerminal;
            int code = element.charAt(i) - 'a';
            return (child[code] != null) && child[code].contains(element, i + 1);
        }

        private int howManyStartWithPrefix(String prefix, int i) {
            if (i == prefix.length())
                return leaves;
            int code = prefix.charAt(i) - 'a';
            if (child[code] == null)
                return 0;
            return child[code].howManyStartWithPrefix(prefix, i + 1);
        }

        private void remove(String element, int i) {
            if (i == element.length()) {
                isTerminal = false;
                leaves--;
                return;
            }
            int code = element.charAt(i) - 'a';
            if (child[code] == null)
                return;
            child[code].remove(element, i + 1);
            leaves--;
            if (child[code].leaves == 0)
                child[code] = null;
        }

        private void fixLeaves() {
            leaves = (isTerminal ? 1 : 0);
            for (Node node : child)
                if (node != null) {
                    node.fixLeaves();
                    leaves += node.leaves;
                }
        }

        private int restore(String str, int i) {
            if (i == str.length())
                return -1;
            if (str.charAt(i) == '_') {
                isTerminal = true;
                i++;
            }
            if (str.charAt(i) == '(')
                i++;
            while (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                int code = str.charAt(i) - 'a';
                child[code] = new Node();
                i = child[code].restore(str, i + 1);
                if (i < str.length() && str.charAt(i) == ')') {
                    if (i + 1 < str.length() && str.charAt(i + 1) == '_')
                        isTerminal = true;
                    return i + 1;
                }
            }
            return i + 1;
        }
    }
}
