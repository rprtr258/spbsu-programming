import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class MainWindow {
    public Slider slider;
    public ProgressBar progressBar;
    public ScrollBar scrollBar;
    public ImageView image;
    public Label sliderValueLabel;
    public Label scrollBarValueLabel;
    public Label progressLabel;

    public void initialize() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> updateView());
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> updateView());
        slider.valueProperty().addListener((observable, oldValue, newValue) -> sliderValueLabel.setText(String.format("Slider value: %.6f", newValue.doubleValue())));
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> scrollBarValueLabel.setText(String.format("Slider value: %.6f", newValue.doubleValue())));
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> progressLabel.setText(String.format("Progress: %.6f", getProgress())));
    }

    private void updateView() {
        image.setTranslateY((1 - getSummaryValues()) * image.getFitHeight());
        progressBar.setProgress(getSummaryValues() / 2);
    }

    private double getSummaryValues() {
        return slider.getValue() + scrollBar.getValue();
    }

    private double getProgress() {
        return getSummaryValues() / 2;
    }
}
