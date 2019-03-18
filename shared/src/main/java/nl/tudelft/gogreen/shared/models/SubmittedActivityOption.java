package nl.tudelft.gogreen.shared.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmittedActivityOption {
    private Integer optionId;
    private String value;
}
