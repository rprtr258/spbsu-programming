package com.rprtr258;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

/**
 * Class that controls all elements of main window by defining reactions on sygnals and changing view.
 */
public class MainWindow {
    /** Slider which manipulates progress. */
    public Slider slider;
    /** Scrollbar which manipulates progress. */
    public ScrollBar scrollBar;
    /** Displays progress. */
    public ProgressBar progressBar;
    /** Image which is controlled with progress. */
    public ImageView image;
    /** Displays slider value. */
    public Label sliderValueText;
    /** Displays scrollbar value. */
    public Label scrollBarValueText;
    /** Displays progress value. */
    public Label progressLabel;

    /**
     * Initializer function. Sets listeners to window objects' events.
     */
    public void initialize() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> updateView());
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> updateView());
        slider.valueProperty().addListener((observable, oldValue, newValue) -> sliderValueText.setText(String.format("Slider value: %.6f", newValue.doubleValue())));
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> scrollBarValueText.setText(String.format("Slider value: %.6f", newValue.doubleValue())));
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> progressLabel.setText(String.format("Progress: %.6f", calculateProgress())));
    }

    /**
     * Updates view recording to progress.
     */
    private void updateView() {
        image.setTranslateY((1 - calculateProgress() * 2) * image.getFitHeight());
        progressBar.setProgress(calculateProgress());
    }

    /**
     * Calculates summary value of slider and scrollbar.
     * @return sum of controllers values.
     */
    private double getSummaryValues() {
        return slider.getValue() + scrollBar.getValue();
    }

    /**
     * Calculates progress and returns it.
     * @return progress.
     */
    private double calculateProgress() {
        return getSummaryValues() / 2;
    }
}
