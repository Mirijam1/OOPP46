package nl.tudelft.gogreen.shared.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.control.Control;

public enum InputType {
    @JsonProperty("FLOAT")
    FLOAT(0);

    private Integer controlId;

    InputType(Integer controlId) {
        this.controlId = controlId;
    }

    /**
     * get Options based on Activity.
     * @return Control
     */
    public Control getControl() {
        switch (this.controlId) {
            case 0:
                return new JFXSlider(0, 100, 50);
            case 1:
                return new JFXToggleButton();
            case 2:
                return null; //TODO
            default:
                return null;
        }
    }
}
