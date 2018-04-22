package com.rprtr258;

import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:");
            System.out.println("gradle run -Pargs=\"CLASS_FILE\"");
            System.out.println("Example:");
            System.out.println("gradle run -Pargs=\"test_files/com.rprtr258.AVLTree\"");
            return;
        }
        try {
            tryPrintClass(args[0]);
        } catch (MalformedURLException e) {
            System.out.println("Incorrect class directory.");
        } catch (ClassNotFoundException e) {
            System.out.println("Incorrect class file name.");
        }
    }

    private static void tryPrintClass(String path) throws MalformedURLException, ClassNotFoundException {
        int j = path.lastIndexOf('/');
        String className = path.substring(j + 1);
        String classDir = (j == -1 ? "." : path.substring(0, j));

        File classDirFile = new File(classDir);
        URL classDirURL = classDirFile.toURI().toURL();
        Class loadedClass = new URLClassLoader(new URL[]{classDirURL}).loadClass(className);

        System.out.printf("Class name: %s\n", className);
        System.out.printf("Class directory: %s\n", classDirFile.getAbsolutePath());
        System.out.print("\n");
        printClass(loadedClass);
    }

    private static void printClass(Class clazz) {
        printClassDeclaration(clazz);
        System.out.println("    //Fields");
        printClassFields(clazz);
        System.out.println("    //Constructors");
        printClassConstructors(clazz);
        System.out.println("    //Methods");
        printClassMethods(clazz);
        System.out.println("}");
    }

    private static void printClassDeclaration(Class clazz) {
        String className = clazz.getSimpleName();
        String parentClassName = clazz.getSuperclass().getSimpleName();
        String implementedList = Arrays.stream(clazz.getInterfaces()).map(Class::getSimpleName).collect(joining(", "));
        if (!"".equals(implementedList))
            implementedList = " implements " + implementedList;
        System.out.printf("class %s extends %s%s {\n", className, parentClassName, implementedList);
    }

    private static void printClassFields(Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            System.out.printf("    %s %s %s;\n", getPrivacyModifier(field.getModifiers()), field.getType().getSimpleName(), field.getName());
        }
    }

    private static void printClassConstructors(Class clazz) {
        String className = clazz.getSimpleName();
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            String privacyModifier = getPrivacyModifier(constructor.getModifiers());
            if (!"".equals(privacyModifier))
                privacyModifier += " ";
            String staticModifier = getStaticModifier(constructor.getModifiers());
            if (!"".equals(staticModifier))
                staticModifier += " ";
            String argsList = getArgsList(constructor);
            System.out.printf("    %s%s%s(%s);\n", privacyModifier, staticModifier, className, argsList);
        }
    }

    private static void printClassMethods(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            String privacyModifier = getPrivacyModifier(method.getModifiers());
            if (!"".equals(privacyModifier))
                privacyModifier += " ";
            String staticModifier = getStaticModifier(method.getModifiers());
            if (!"".equals(staticModifier))
                staticModifier += " ";
            String argsList = getArgsList(method);
            String throwList = Arrays.stream(method.getExceptionTypes()).map(Class::getSimpleName).collect(joining(", "));
            if (!"".equals(throwList))
                throwList = " throws " + throwList;
            System.out.printf("    %s%s%s %s(%s)%s;\n", privacyModifier, staticModifier, returnTypeName, methodName, argsList, throwList);
        }
    }

    private static String getArgsList(Executable constructor) {
        return Arrays.stream(constructor.getParameterTypes()).map(Class::getSimpleName).collect(joining(", "));
    }

    private static String getPrivacyModifier(int mod) {
        if (Modifier.isPublic(mod))
            return "public";
        if (Modifier.isProtected(mod))
            return "protected";
        if (Modifier.isPrivate(mod))
            return "private";
        return "";
    }

    private static String getStaticModifier(int mod) {
        if (Modifier.isStatic(mod))
            return "static";
        return "";
    }
}
