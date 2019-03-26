package nl.tudelft.gogreen.api.servermodels;

import lombok.*;

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
