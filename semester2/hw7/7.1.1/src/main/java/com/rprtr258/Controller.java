package com.rprtr258;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Window elements controller class.
 */
public class Controller {
    public TextArea codeArea;
    public Button loadButton;
    private Stage myStage = null;

    /**
     * Sets inner reference to current stage.
     * @param myStage stage to set.
     */
    public void setStage(Stage myStage) {
        this.myStage = myStage;
    }

    /**
     * Sets listeners on controller's load.
     */
    public void initialize() {
        loadButton.setOnAction(e -> {
            File file = chooseFile();
            if (file != null) {
                try {
                    String className = loadClassName(file);
                    String packageDirectory = className.replace('.', '\\');
                    String filePath = file.getPath();
                    filePath = filePath.substring(0, filePath.indexOf(packageDirectory));
                    URL dirURL = new File(filePath).toURI().toURL();
                    Class loadedClass = new URLClassLoader(new URL[]{dirURL}).loadClass(className);
                    String code = ClassDecompiler.getClassCode(loadedClass);
                    codeArea.setText(code);
                } catch (ClassNotFoundException | MalformedURLException e1) {
                    e1.printStackTrace();
                    codeArea.setText("Incorrect .class file.");
                }
            }
        });
    }

    /**
     * Returns file chosen in FileChooser.
     * @return file chose.
     */
    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java class file", "*.class"));
        fileChooser.setInitialDirectory(new File("."));
        return fileChooser.showOpenDialog(myStage);
    }

    /**
     * Loads class name from file.
     * @param classFile java .class file.
     * @return class's name.
     */
    private String loadClassName(File classFile) {
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
