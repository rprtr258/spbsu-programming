package com.rprtr258;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

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
                    Class loadedClass = ClassFileLoader.loadClassFile(file);
                    String code = ClassDecompiler.getClassCode(loadedClass);
                    codeArea.setText(code);
                } catch (ClassNotFoundException | MalformedURLException e1) {
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
}
