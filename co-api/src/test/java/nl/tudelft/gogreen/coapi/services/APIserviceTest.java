package nl.tudelft.gogreen.coapi.services;

import nl.tudelft.gogreen.coapi.API;
import nl.tudelft.gogreen.coapi.CarbonServer;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class APIserviceTest {
    private APIservice apiService;

    private RestTemplate template;

    @Before
    public void setUp() {
        template = Mockito.mock(RestTemplate.class);
        apiService = new APIservice(template);
    }

    @Test
    public void testVegMeal() throws Exception {
        Double val = 1.6;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");

        Double points = apiService.vegmeal(new Vegmeal("2019-03-29", 1, 400));
        Double testpoint = 16.0;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testLocalProduce() throws Exception {
        Double val = 7.5;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");

        Double points = apiService.localproduce(new Localproduce("2019-03-29"));
        Double testpoint = 75.0;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testBikeInsteadOfCar() throws Exception {
        Double val = 150.84;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");

        Double points = apiService.carjourney(new Transport("2019-03-2019", 500));
        Double testpoint = 1508.4;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testBusInsteadOfCar() throws Exception {
        Double val = 79D;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        ResponseEntity entityBase = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(template.postForEntity(eq(API.buildURL(Endpoints.carActivity)), any(), any(), any(Object.class))).thenReturn(entityBase);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        when(entityBase.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"0\"}}}}");
        Double points = apiService.busjourney(new Transport("2019-03-2019", 500));
        Double testpoint = 710D;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testTrainInsteadOfCar() throws Exception {
        Double val = 102.37;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        ResponseEntity entityBase = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(template.postForEntity(eq(API.buildURL(Endpoints.carActivity)), any(), any(), any(Object.class))).thenReturn(entityBase);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        when(entityBase.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"0\"}}}}");
        Double points = apiService.trainjourney(new Train("2019-03-2019", 400));
        Double testpoint = 176.3;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testLowerTemp() throws Exception {
        Double val = 2333.2;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        Double points = apiService.lowtemp(new LowerTemp(3, 4));
        Double testpoint = 233.32;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testLEDLights() throws Exception {
        Double val = 0.25;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        Double points = apiService.ledlights(new LEDLights(2));
        Double testpoint = 25.0;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testSolarPanels() throws Exception {
        Double val = 1674.39;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        Double points = apiService.solar(new SolarPanels(3));
        Double testpoint = 1674.39;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testPlantingTrees() throws Exception {
        Double val = 21.77;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        Double points = apiService.plantTrees(new PlantTrees(1));
        Double testpoint = 217.7;
        assertEquals(testpoint, points, 0.1);
    }

    @Test
    public void testGreenHotel() throws Exception {
        Double val = 80.53;
        ResponseEntity entity = Mockito.mock(ResponseEntity.class);
        when(template.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(entity);
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("{\"decisions\":{\"carbon\":{\"object\":{\"value\":\"" + val + "\"}}}}");
        Double points = apiService.greenhotel(new GreenHotel(3));
        Double testpoint = 80.53;
        assertEquals(testpoint, points, 0.1);
    }
}
