package nl.tudelft.gogreen.coapi.services;

import nl.tudelft.gogreen.coapi.API;
import nl.tudelft.gogreen.coapi.models.food.Localproduce;
import nl.tudelft.gogreen.coapi.models.food.Vegmeal;
import nl.tudelft.gogreen.coapi.models.misc.GreenHotel;
import nl.tudelft.gogreen.coapi.models.transportation.Train;
import nl.tudelft.gogreen.coapi.models.transportation.Transport;
import nl.tudelft.gogreen.coapi.models.utilities.LEDLights;
import nl.tudelft.gogreen.coapi.models.utilities.LowerTemp;
import nl.tudelft.gogreen.coapi.models.utilities.SolarPanels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.methodsDeclaredIn;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({API.class, RestTemplate.class, ResponseEntity.class})
public class APIfallbackTest {

    @InjectMocks
    private APIservice api;

    private HttpEntity entity;


    @Test
    public void testVegmealFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        Vegmeal veg = new Vegmeal("2019-04-08", 1, 300);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.vegmeal(veg);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testLocalProduceFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        Localproduce local = new Localproduce("2019-03-29");

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.localproduce(local);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testBikeInsteadOfCarFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        Transport car = new Transport("2019-03-2019", 500);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.carjourney(car);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testBusInsteadOfCarFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        Transport car = new Transport("2019-03-2019", 500);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.busjourney(car);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testTrainInsteadOfCarFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        Train car = new Train("2019-03-2019", 500);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.trainjourney(car);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testLowerTempFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        LowerTemp lowTemp = new LowerTemp(3, 4);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.lowtemp(lowTemp);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }


    @Test
    public void testSolarPanelsFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        SolarPanels solar = new SolarPanels(3);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.solar(solar);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

    @Test
    public void testGreenHotelFallback() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        api = new APIservice(restTemplate);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);
        GreenHotel greenHotel = new GreenHotel(3);

        when(restTemplate.postForEntity(any(), any(), any(), any(Object.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        suppress(methodsDeclaredIn(API.class));

        Double result = api.greenhotel(greenHotel);
        Double epsilon = 0.01;

        // float bad
        assertTrue(result >= result - epsilon && result <= result + epsilon);
    }

}
