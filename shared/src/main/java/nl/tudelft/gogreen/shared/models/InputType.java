package nl.tudelft.gogreen.shared.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfoenix.controls.JFXSlider;
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
     *
     * @return Control
     */
    public Control getControl() {
        return new JFXSlider(0, 100, 50);
    }
}
