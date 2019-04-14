package nl.tudelft.gogreen.coapi.services;

import nl.tudelft.gogreen.coapi.CarbonServer;
import nl.tudelft.gogreen.coapi.models.food.Localproduce;
import nl.tudelft.gogreen.coapi.models.food.Vegmeal;
import nl.tudelft.gogreen.coapi.models.misc.GreenHotel;
import nl.tudelft.gogreen.coapi.models.misc.PlantTrees;
import nl.tudelft.gogreen.coapi.models.transportation.Train;
import nl.tudelft.gogreen.coapi.models.transportation.Transport;
import nl.tudelft.gogreen.coapi.models.utilities.LEDLights;
import nl.tudelft.gogreen.coapi.models.utilities.LowerTemp;
import nl.tudelft.gogreen.coapi.models.utilities.SolarPanels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CarbonServer.class)
public class APIserviceTest {
    @Autowired
    APIservice apiService;

    @Test
    public void testVegMeal() throws Exception {
        Double points = apiService.vegmeal(new Vegmeal("2019-03-29", 1, 400));
        Double testpoint = 16.0;
        assertEquals(testpoint, points);
    }

    @Test
    public void testLocalProduce() throws Exception {
        Double points = apiService.localproduce(new Localproduce("2019-03-29"));
        Double testpoint = 75.0;
        assertEquals(testpoint, points);
    }

    @Test
    public void testBikeInsteadOfCar() throws Exception {
        Double points = apiService.carjourney(new Transport("2019-03-2019", 500));
        Double testpoint = 1508.4;
        assertEquals(testpoint, points);
    }

    @Test
    public void testBusInsteadOfCar() throws Exception {
        Double points = apiService.busjourney(new Transport("2019-03-2019", 500));
        Double testpoint = 790.0;
        assertEquals(testpoint, points);
    }

    @Test
    public void testTrainInsteadOfCar() throws Exception {
        Double points = apiService.trainjourney(new Train("2019-03-2019", 400));
        Double testpoint = 1023.7;
        assertEquals(testpoint, points);
    }

    @Test
    public void testLowerTemp() throws Exception {
        Double points = apiService.lowtemp(new LowerTemp(3, 4));
        Double testpoint = 233.32;
        assertEquals(testpoint, points);
    }

    @Test
    public void testLEDLights() throws Exception {
        Double points = apiService.ledlights(new LEDLights(2));
        Double testpoint = 25.0;
        assertEquals(testpoint, points);
    }

    @Test
    public void testSolarPanels() throws Exception {
        Double points = apiService.solar(new SolarPanels(3));
        Double testpoint = 1674.39;
        assertEquals(testpoint, points);
    }

    @Test
    public void testPlantingTrees() throws Exception {
        Double points = apiService.plantTrees(new PlantTrees(1));
        Double testpoint = 217.7;
        assertEquals(testpoint, points);
    }

    @Test
    public void testGreenHotel() throws Exception {
        Double points = apiService.greenhotel(new GreenHotel(3));
        Double testpoint = 80.53;
        assertEquals(testpoint, points);
    }
}
