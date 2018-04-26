package com.rprtr258;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassDecompilerTest {
    @Test
    public void getClassCodeTest() {
        String[] mainCode = ClassDecompiler.getClassCode(ClassDecompiler.class).split("\n");
        String[] expected = {
                "class ClassDecompiler extends Object {",
                "    //Fields",
                "    //Constructors",
                "    public ClassDecompiler();",
                "    //Methods",
                "    public static void main(String[]);",
                "    private static String getStaticModifier(int);",
                "    private static String printClassMethods(Class);",
                "    private static String printClassConstructors(Class);",
                "    private static String getPrivacyModifier(int);",
                "    private static String printClassDeclaration(Class);",
                "    private static String printClassFields(Class);",
                "    private static String getArgsList(Executable);",
                "    public static String getClassCode(Class);",
                "    private static void tryPrintClass(String) throws MalformedURLException, ClassNotFoundException;",
                "}"
        };
        Arrays.sort(mainCode);
        Arrays.sort(expected);
        assertArrayEquals(expected, mainCode);
    }
}