package com.rprtr258;

import java.lang.reflect.*;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * Class that decompiles java class and makes parts of code of it.
 */
public class ClassDecompiler {
    /**
     * Returns code of given class.
     * @param clazz <b>Class</b> object.
     * @return class pseudo code.
     */
    public static String getClassCode(Class clazz) {
        String result = "";
        result += getClassDeclaration(clazz);
        String classFields = getClassFields(clazz);
        if (!"".equals(classFields)) {
            result += "    //Fields\n";
            result += classFields;
        }
        result += "    //Constructors\n";
        result += getClassConstructors(clazz);
        String classMethods = getClassMethods(clazz);
        if (!"".equals(classMethods)) {
            result += "    //Methods\n";
            result += classMethods;
        }
        result += "}";
        return result;
    }

    /**
     * Prints class's declaration.
     * @param clazz <b>Class</b> object.
     * @return class's declaration code.
     */
    private static String getClassDeclaration(Class clazz) {
        String className = getGenericClassName(clazz);
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
    private static String getClassFields(Class clazz) {
        StringBuilder result = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            result.append(String.format("    %s %s %s;\n", Modifier.toString(field.getModifiers()), getGenericClassName(field.getType()), field.getName()));
        }
        return result.toString();
    }

    /**
     * Prints class's constructors.
     * @param clazz <b>Class</b> object.
     * @return class's constructors.
     */
    private static String getClassConstructors(Class clazz) {
        StringBuilder result = new StringBuilder();
        String className = clazz.getSimpleName();
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            String modifiers = Modifier.toString(constructor.getModifiers());
            if (!"".equals(modifiers))
                modifiers += " ";
            String argsList = getArgsList(constructor);
            result.append(String.format("    %s%s(%s);\n", modifiers, className, argsList));
        }
        return result.toString();
    }

    /**
     * Prints class's methods.
     * @param clazz <b>Class</b> object.
     * @return class's methods.
     */
    private static String getClassMethods(Class clazz) {
        StringBuilder result = new StringBuilder();
        for (Method method : clazz.getDeclaredMethods()) {
            String returnTypeName = getGenericClassName(method.getReturnType());
            String methodName = method.getName();
            String modifiers = Modifier.toString(method.getModifiers());
            if (!"".equals(modifiers))
                modifiers += " ";
            String argsList = getArgsList(method);
            String throwList = Arrays.stream(method.getExceptionTypes()).map(Class::getSimpleName).collect(joining(", "));
            if (!"".equals(throwList))
                throwList = " throws " + throwList;
            result.append(String.format("    %s%s %s(%s)%s;\n", modifiers, returnTypeName, methodName, argsList, throwList));
        }
        return result.toString();
    }

    /**
     * Returns argumentss list of given method/constructor.
     * @param executor executable thing.
     * @return list of arguments joined with comma.
     */
    private static String getArgsList(Executable executor) {
        return Arrays.stream(executor.getParameterTypes()).map(ClassDecompiler::getGenericClassName).collect(joining(", "));
    }

    /**
     * Returns class name with generics if there is any.
     * @param clazz given class.
     * @return class name with generyc types in format "ClassName<T1, T2>"
     */
    private static String getGenericClassName(Class clazz) {
        String genericTypes = Arrays.stream(clazz.getTypeParameters()).map(Type::getTypeName).collect(joining(", "));
        if (!"".equals(genericTypes))
            genericTypes = "<" + genericTypes + ">";
        return clazz.getSimpleName() + genericTypes;
    }
}
