package nl.tudelft.gogreen.coapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class response {
    private String response;



////
////    {
////        "more_efficient_vehicle":1.83, "telecommute_to_work":0.98, "ride_my_bike":
////        0.53, "take_public_transportation":0.42, "practice_eco_driving":0.85, "maintain_my_vehicles":
////        0.67, "carpool_to_work":0.85, "reduce_air_travel":0.16, "offset_transportation":
////        13.47, "switch_to_cfl":0.11, "thermostat_winter":0.36, "thermostat_summer":
////        0.08, "energy_star_fridge":0.03, "purchase_green_electricity":1.44, "line_dry_clothing":
////        0.13, "offset_housing":7.83, "low_carbon_diet":1.72, "go_organic":0.17, "offset_shopping":
////        19.28
////    }
//

//
////    {
////        "more_efficient_vehicle":490.06, "telecommute_to_work":501.82, "ride_my_bike":
////        141.82, "take_public_transportation":141.82, "practice_eco_driving":
////        229.3, "maintain_my_vehicles":179.54, "carpool_to_work":323.35, "reduce_air_travel":
////        35.6, "offset_transportation":0, "switch_to_cfl":62.78, "thermostat_winter":
////        78.47, "thermostat_summer":47.75, "energy_star_fridge":16.58, "purchase_green_electricity":
////        0, "line_dry_clothing":75.5, "offset_housing":0, "low_carbon_diet":1047.27, "go_organic":
////        0, "offset_shopping":0
////    }
//

//
////    {
////        "more_efficient_vehicle":2000, "telecommute_to_work":0, "ride_my_bike":
////        0, "take_public_transportation":0, "practice_eco_driving":0, "maintain_my_vehicles":
////        0, "carpool_to_work":0, "reduce_air_travel":0, "offset_transportation":
////        269.41, "switch_to_cfl":10, "thermostat_winter":0, "thermostat_summer":
////        0, "energy_star_fridge":30, "purchase_green_electricity":28.79, "line_dry_clothing":
////        0, "offset_housing":156.66, "low_carbon_diet":0, "go_organic":380.17, "offset_shopping":
////        385.63
////    }
//

////
////    {
////        "utility_id":"14328", "company":"Pacific Gas &amp; Electric Co", "utility_number":
////        "1", "weighted_emission_rate":"217", "percent_coal":"0.05221", "percent_oil":
////        "0.00749", "percent_natgas":"0.36639", "percent_hydro":"0.1722", "percent_fossil":
////        "0.43245", "percent_renewables":"0.06588", "percent_nuclear":"0.3288", "percent_other":
////        "0.00067", "percent_biomass":"0.01654", "percent_wind":"0.01745", "percent_solar":
////        "0.00174", "percent_geothermal":"0.03014", "percent_otherfossil":
////        "0.00637", "percent_oil_other_fossil":"0.01385"
////    }
//


}
