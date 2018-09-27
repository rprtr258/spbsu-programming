package com.rprtr258;

import java.io.*;
import java.net.*;

public class ClassFileLoader {
    /**
     * Returns class loaded from given file.
     * @param file given file.
     * @return loaded class.
     * @throws MalformedURLException if file path as url is incorrect.
     * @throws ClassNotFoundException if class was not found.
     */
    public static Class loadClassFile(File file) throws MalformedURLException, ClassNotFoundException {
        String className = loadClassName(file);
        String packageDirectory = className.replace('.', '\\');
        String filePath = file.getPath();
        filePath = filePath.substring(0, filePath.indexOf(packageDirectory));
        URL dirURL = new File(filePath).toURI().toURL();
        return new URLClassLoader(new URL[]{dirURL}).loadClass(className);
    }

    /**
     * Loads class name from file.
     * @param classFile java .class file.
     * @return class's name.
     */
    private static String loadClassName(File classFile) {
        try {
            FileReader fileReader = new FileReader(classFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String className = null;
            String line;
            while(null != (line = bufferedReader.readLine())) {
                if(line.contains("this")) {
                    className = line.substring(line.indexOf("this") + "this".length() + 4);
                    className = className.substring(0, className.indexOf(';'));
                    className = className.replace('/', '.');
                    break;
                }
            }
            bufferedReader.close();
            fileReader.close();
            return className;
        } catch (IOException e1) {
            return "";
        }
    }
}
