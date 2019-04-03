package nl.tudelft.gogreen.coapi.services;

import nl.tudelft.gogreen.coapi.API;
import nl.tudelft.gogreen.coapi.config.Endpoints;
import nl.tudelft.gogreen.coapi.models.Food.Vegmeal;
import nl.tudelft.gogreen.coapi.models.Food.localproduce;
import nl.tudelft.gogreen.coapi.models.Misc.GreenHotel;
import nl.tudelft.gogreen.coapi.models.Misc.PlantTrees;
import nl.tudelft.gogreen.coapi.models.Transportation.Transport;
import nl.tudelft.gogreen.coapi.models.Transportation.train;
import nl.tudelft.gogreen.coapi.models.Utilities.LEDLights;
import nl.tudelft.gogreen.coapi.models.Utilities.LowerTemp;
import nl.tudelft.gogreen.coapi.models.Utilities.SolarPanels;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@RestController
@RequestMapping("co-api")
public class APIservice {
    @Autowired
    private RestTemplate restTemplate;
    private HttpEntity<String> entity;
    private String url;
    private HttpHeaders headers;

    @Autowired
    public APIservice() {
        headers = API.createHttpHeaders();
    }


    @RequestMapping(value = "/food/vegmeal", method = RequestMethod.POST)
    private double vegmeal(@RequestBody Vegmeal meal) throws Exception {
        url = API.buildURL(Endpoints.foodActivity);
        entity = new HttpEntity<>(Vegmeal.XtoJson(meal), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 20 * meal.getSize()/300;
        }
        return getPoints(result.getBody());
    }

    @RequestMapping(value = "/food/localproduce", method = RequestMethod.POST)
    private double localproduce(@RequestBody localproduce local) throws Exception {
        url = API.buildURL(Endpoints.foodActivity);
        entity = new HttpEntity<>(localproduce.XtoJson(local), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 200;
        }
        return getPoints(result.getBody());
    }


    @RequestMapping(value = "/transport/train", method = RequestMethod.POST)
    private double trainjourney(@RequestBody train trainjourney) throws Exception {
        url = API.buildURL(Endpoints.trainActivity);
        entity = new HttpEntity<>(train.XtoJson(trainjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 2.5 * (trainjourney.getDistance());
        }
        Double trainCO2 = getPoints(result.getBody());
        Double carCO2 = car(new Transport(trainjourney.getDate(), trainjourney.getDistance()));
        return carCO2 - trainCO2;
    }

    @RequestMapping(value = "/transport/bike", method = RequestMethod.POST)
    private double car(@RequestBody Transport carjourney) throws Exception {
        url = API.buildURL(Endpoints.carActivity);
        entity = new HttpEntity<>(Transport.XtoJson(carjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 3 * carjourney.getDistance();
        }
        return getPoints(result.getBody());
    }

    @RequestMapping(value = "/transport/bus", method = RequestMethod.POST)
    private double busjourney(@RequestBody Transport busjourney) throws Exception {
        url = API.buildURL(Endpoints.busActivity);
        entity = new HttpEntity<>(Transport.XtoJson(busjourney), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 1.5 * busjourney.getDistance();
        }
        Double busCO2 = getPoints(result.getBody());
        Double carCO2 = car(busjourney);
        return carCO2 - busCO2;
    }

    @RequestMapping(value = "/utilities/lowertemp", method = RequestMethod.POST)
    private double lowtemp(@RequestBody LowerTemp lowtemp) throws Exception {
        url = API.buildURL(Endpoints.energyActivity);
        entity = new HttpEntity<>(LowerTemp.XtoJson(lowtemp), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 50 * lowtemp.getDegrees();
        }
        double res = getPoints(result.getBody()) / 100;
        return res;
    }


    @RequestMapping(value = "/utilities/solarpanels", method = RequestMethod.POST)
    private double solar(@RequestBody SolarPanels solarPanels) throws Exception {
        url = API.buildURL(Endpoints.energyActivity);
        entity = new HttpEntity<>(SolarPanels.XtoJson(solarPanels), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 1700;
        }
        double res = getPoints(result.getBody()) / 10;
        return res;
    }

    @RequestMapping(value = "/utilities/LED", method = RequestMethod.POST)
    private double ledlights(@RequestBody LEDLights ledLights) throws Exception {
        url = API.buildURL(Endpoints.electricityActivity);
        entity = new HttpEntity<>(LEDLights.XtoJson(ledLights), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 20 * ledLights.getBulbsReplaced();
        }
        double res = getPoints(result.getBody()) * 10;
        return res;
    }

    @RequestMapping(value = "/misc/green-hotel", method = RequestMethod.POST)
    private double greenhotel(@RequestBody GreenHotel greenHotel) throws Exception {
        url = API.buildURL(Endpoints.lodgingActivity);
        entity = new HttpEntity<>(GreenHotel.XtoJson(greenHotel), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return 25 * greenHotel.getNights();
        }
        double res = getPoints(result.getBody()) / 10;
        return res;
    }


    @RequestMapping(value = "/misc/plant-trees", method = RequestMethod.POST)
    private double plantTrees(@RequestBody PlantTrees trees) throws Exception {
        double treeCO2 = trees.getTrees() * 21.77; //each tree consumes 48LBS online research
        return round(treeCO2);
    }


    private double getPoints(String result) {
        JSONObject json = new JSONObject(result);
        Double val = json.getJSONObject("decisions").getJSONObject("carbon").getJSONObject("object").getDouble("value");
        Double points = round(val);
        return points;
    }

    private double round(Double val) {
        DecimalFormat df = new DecimalFormat("0.00");
        Double points = Double.valueOf(df.format(val));
        System.out.println(points);
        return points * 10;
    }
}
