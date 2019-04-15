package nl.tudelft.gogreen.api.servermodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Options {
    private Integer id;
    private String description;
    private String inputType;
    private Options[] options;

}
