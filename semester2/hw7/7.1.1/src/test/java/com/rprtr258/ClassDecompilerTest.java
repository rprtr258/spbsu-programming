package com.rprtr258;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class ClassDecompilerTest {
    @Test
    public void getClassCodeTest() {
        String[] mainCode = ClassDecompiler.getClassCode(ClassDecompiler.class).split("\n");
        String[] expected = {
            "class ClassDecompiler extends Object {",
            "    //Constructors",
            "    public ClassDecompiler();",
            "    //Methods",
            "    private static String getClassMethods(Class<T>);",
            "    private static String getClassConstructors(Class<T>);",
            "    private static String getClassDeclaration(Class<T>);",
            "    private static String getClassFields(Class<T>);",
            "    private static String getArgsList(Executable);",
            "    public static String getClassCode(Class<T>);",
            "    private static String getGenericClassName(Class<T>);",
            "}"
        };
        Arrays.sort(mainCode);
        Arrays.sort(expected);
        assertArrayEquals(expected, mainCode);
    }
}