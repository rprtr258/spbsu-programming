package com.rprtr258.util;

import org.junit.Test;

public class UniqueListTest {
    @Test(expected = UniquenessException.class)
    public void appendTest() throws ListAppendException {
        UniqueList<String> myList = new UniqueList<>();
        myList.append("Sayonara");
        myList.append("Sayonara");
    }
}