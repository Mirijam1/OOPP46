package nl.tudelft.gogreen.coapi.services;

import nl.tudelft.gogreen.coapi.API;
import nl.tudelft.gogreen.coapi.config.Endpoints;
import nl.tudelft.gogreen.coapi.models.food.Localproduce;
import nl.tudelft.gogreen.coapi.models.food.Vegmeal;
import nl.tudelft.gogreen.coapi.models.misc.GreenHotel;
import nl.tudelft.gogreen.coapi.models.misc.PlantTrees;
import nl.tudelft.gogreen.coapi.models.transportation.Train;
import nl.tudelft.gogreen.coapi.models.transportation.Transport;
import nl.tudelft.gogreen.coapi.models.utilities.LEDLights;
import nl.tudelft.gogreen.coapi.models.utilities.LowerTemp;
import nl.tudelft.gogreen.coapi.models.utilities.SolarPanels;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@Service
@RestController
@RequestMapping("co-api")
public class APIservice {
    static HttpHeaders headers;
    private final RestTemplate restTemplate;
    private HttpEntity<String> entity;
    private String url;


    @Autowired
    public APIservice(RestTemplate restTemplate) {
        headers = API.createHttpHeaders();
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/food/vegmeal", method = RequestMethod.POST)
    protected double vegmeal(@RequestBody Vegmeal meal) throws Exception {
        url = API.buildURL(Endpoints.foodActivity);
        entity = new HttpEntity<>(Vegmeal.jsonmaker(meal), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            return 20 * meal.getSize() / 300;
        }
        return getPoints(result.getBody());
    }

    /**
     * returns points for purchasing localproduce based on user input.
     *
     * @param local - localproduce bought
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/food/localproduce", method = RequestMethod.POST)
    public double localproduce(@RequestBody Localproduce local) throws Exception {
        url = API.buildURL(Endpoints.foodActivity);
        entity = new HttpEntity<>(Localproduce.jsonmaker(local), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 200;
        }
        return getPoints(result.getBody());
    }

    /**
     * returns points for taking train based on user input.
     *
     * @param trainjourney - trainjourney
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/transport/train", method = RequestMethod.POST)
    public double trainjourney(@RequestBody Train trainjourney) throws Exception {
        url = API.buildURL(Endpoints.trainActivity);
        entity = new HttpEntity<>(Train.jsonmaker(trainjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 2.5 * (trainjourney.getDistance());
        }
        Double trainCO2 = getPoints(result.getBody());
        Double carCO2 = carjourney(new Transport(trainjourney.getDate(), trainjourney.getDistance()));
        return carCO2 - trainCO2;
    }


    /**
     * returns points for taking bike based on user input.
     *
     * @param carjourney - bike journey
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/transport/bike", method = RequestMethod.POST)
    public double carjourney(@RequestBody Transport carjourney) throws Exception {
        url = API.buildURL(Endpoints.carActivity);
        entity = new HttpEntity<>(Transport.jsonmaker(carjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 3 * carjourney.getDistance();
        }
        return getPoints(result.getBody());
    }


    /**
     * returns points for taking bus based on user input.
     *
     * @param busjourney - busjourney
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/transport/bus", method = RequestMethod.POST)
    public double busjourney(@RequestBody Transport busjourney) throws Exception {
        url = API.buildURL(Endpoints.busActivity);
        entity = new HttpEntity<>(Transport.jsonmaker(busjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 1.5 * busjourney.getDistance();
        }
        Double busCO2 = getPoints(result.getBody());
        Double carCO2 = carjourney(busjourney);
        return carCO2 - busCO2;
    }

    /**
     * returns points for lowering temperature based on user input.
     *
     * @param lowtemp - lower temperature
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/utilities/lowertemp", method = RequestMethod.POST)
    public double lowtemp(@RequestBody LowerTemp lowtemp) throws Exception {
        url = API.buildURL(Endpoints.energyActivity);
        entity = new HttpEntity<>(LowerTemp.jsonmaker(lowtemp), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 50 * lowtemp.getDegrees();
        }
        double res = getPoints(result.getBody()) / 100;
        return res;
    }

    /**
     * returns points for installing solarpanels based on user input.
     *
     * @param solarPanels - solar panels.
     * @return points.
     * @throws Exception if bad request.
     */
    @RequestMapping(value = "/utilities/solarpanels", method = RequestMethod.POST)
    public double solar(@RequestBody SolarPanels solarPanels) throws Exception {
        url = API.buildURL(Endpoints.energyActivity);
        entity = new HttpEntity<>(SolarPanels.jsonmaker(solarPanels), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 1700;
        }
        double res = getPoints(result.getBody()) / 10;
        return res;
    }

    /**
     * returns points for installing LED lights based on user input.
     *
     * @param ledLights - ledlights
     * @return points.
     * @throws Exception if bad request.
     */

    @RequestMapping(value = "/utilities/LED", method = RequestMethod.POST)
    public double ledlights(@RequestBody LEDLights ledLights) throws Exception {
        url = API.buildURL(Endpoints.electricityActivity);
        entity = new HttpEntity<>(LEDLights.jsonmaker(ledLights), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 20 * ledLights.getBulbsReplaced();
        }
        double res = getPoints(result.getBody()) * 10;
        return res;
    }

    /**
     * returns points for staying in carbon-friendly hotel based on user input.
     *
     * @param greenHotel - greenHotel night stays
     * @return points.
     * @throws Exception if bad request.
     */

    @RequestMapping(value = "/misc/green-hotel", method = RequestMethod.POST)
    public double greenhotel(@RequestBody GreenHotel greenHotel) throws Exception {
        url = API.buildURL(Endpoints.lodgingActivity);
        entity = new HttpEntity<>(GreenHotel.jsonmaker(greenHotel), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 25 * greenHotel.getNights();
        }
        double res = getPoints(result.getBody()) / 10;
        return res;
    }

    /**
     * returns points for planting trees based on user input.
     *
     * @param trees - no of trees planted
     * @return points.
     * @throws Exception if bad request.
     */

    @RequestMapping(value = "/misc/plant-trees", method = RequestMethod.POST)
    public double plantTrees(@RequestBody PlantTrees trees) throws Exception {
        double treeCO2 = trees.getTrees() * 21.77; //each tree consumes 48LBS online research
        return round(treeCO2) * 10;
    }


    protected static double getPoints(String result) {
        JSONObject json = new JSONObject(result);
        Double val = json.getJSONObject("decisions").getJSONObject("carbon").getJSONObject("object").getDouble("value");
        Double points = round(val);
        return points * 10;
    }

    protected static double round(Double val) {
        DecimalFormat df = new DecimalFormat("0.00");
        Double points = Double.valueOf(df.format(val));
        System.out.println(points);
        return points;
    }
}
