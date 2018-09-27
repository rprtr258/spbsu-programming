package com.rprtr258;

import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * Main application class.
 */
public class Main {
    /**
     * Entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:");
            System.out.println("gradle run -Pargs=\"CLASS_FILE\"");
            System.out.println("Example:");
            System.out.println("gradle run -Pargs=\"build/classes/main/java/com.rprtr258.Main\"");
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

    /**
     * Returns code of given class.
     * @param clazz <b>Class</b> object.
     * @return class pseudo code.
     */
    public static String getClassCode(Class clazz) {
        String result = "";
        result += printClassDeclaration(clazz);
        result += "    //Fields\n";
        result += printClassFields(clazz);
        result += "    //Constructors\n";
        result += printClassConstructors(clazz);
        result += "    //Methods\n";
        result += printClassMethods(clazz);
        result += "}";
        return result;
    }

    /**
     * Tries to print class.
     * @param path path to .class file.
     * @throws MalformedURLException if couldn't convert it to URL.
     * @throws ClassNotFoundException if class was not found.
     */
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
        String classCode = getClassCode(loadedClass);
        System.out.println(classCode);
    }

    /**
     * Prints class's declaration.
     * @param clazz <b>Class</b> object.
     * @return class's declaration code.
     */
    private static String printClassDeclaration(Class clazz) {
        String className = clazz.getSimpleName();
        String parentClassName = clazz.getSuperclass().getSimpleName();
        String implementedList = Arrays.stream(clazz.getInterfaces()).map(Class::getSimpleName).collect(joining(", "));
        if (!"".equals(implementedList))
            implementedList = " implements " + implementedList;
        return String.format("class %s extends %s%s {\n", className, parentClassName, implementedList);
    }

    /**
     * Prints class's fields.
     * @param clazz <b>Class</b> object.
     * @return class's fields.
     */
    private static String printClassFields(Class clazz) {
        StringBuilder result = new StringBuilder();
        for (Field field : clazz.getDeclaredFields())
            result.append(String.format("    %s %s %s;\n", getPrivacyModifier(field.getModifiers()), field.getType().getSimpleName(), field.getName()));
        return result.toString();
    }

    /**
     * Prints class's constructors.
     * @param clazz <b>Class</b> object.
     * @return class's constructors.
     */
    private static String printClassConstructors(Class clazz) {
        StringBuilder result = new StringBuilder();
        String className = clazz.getSimpleName();
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            String privacyModifier = getPrivacyModifier(constructor.getModifiers());
            if (!"".equals(privacyModifier))
                privacyModifier += " ";
            String staticModifier = getStaticModifier(constructor.getModifiers());
            if (!"".equals(staticModifier))
                staticModifier += " ";
            String argsList = getArgsList(constructor);
            result.append(String.format("    %s%s%s(%s);\n", privacyModifier, staticModifier, className, argsList));
        }
        return result.toString();
    }

    /**
     * Prints class's methods.
     * @param clazz <b>Class</b> object.
     * @return class's methods.
     */
    private static String printClassMethods(Class clazz) {
        StringBuilder result = new StringBuilder();
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
            result.append(String.format("    %s%s%s %s(%s)%s;\n", privacyModifier, staticModifier, returnTypeName, methodName, argsList, throwList));
        }
        return result.toString();
    }

    /**
     * Returns argumentss list of given method/constructor.
     * @param executor executable thing.
     * @return list of arguments joined with comma.
     */
    private static String getArgsList(Executable executor) {
        return Arrays.stream(executor.getParameterTypes()).map(Class::getSimpleName).collect(joining(", "));
    }

    /**
     * Returns privacy modifier as string encoded in <i>mod</i>.
     * @param mod modifiers bitmask.
     * @return privacy modifier.
     */
    private static String getPrivacyModifier(int mod) {
        if (Modifier.isPublic(mod))
            return "public";
        if (Modifier.isProtected(mod))
            return "protected";
        if (Modifier.isPrivate(mod))
            return "private";
        return "";
    }

    /**
     * Returns static modifier as string encoded in <i>mod</i>.
     * @param mod modifiers bitmask.
     * @return static modifier as string.
     */
    private static String getStaticModifier(int mod) {
        if (Modifier.isStatic(mod))
            return "static";
        return "";
    }
}
