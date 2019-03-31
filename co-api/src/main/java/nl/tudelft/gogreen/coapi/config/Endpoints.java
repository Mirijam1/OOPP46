package nl.tudelft.gogreen.coapi.config;

public class Endpoints {
    public static final String baseURL = "http://impact.brighterplanet.com/";

    //FOOD ENDPOINTS
    public static final String foodActivity = "diets.json";

    //TRANSPORT ENDPOINTS
    public static final String carActivity = "automobile_trips.json";
    public static final String trainActivity = "rail_trips.json";
    public static final String busActivity = "bus_trips.json";

    //ENERGY ENDPOINTS
    public static final String energyActivity = "residences.json";
    public static final String electricityActivity = "electricity_uses.json";
    //MISC ENDPOINTS
    public static final String lodgingActivity = "lodging.json";

    //API ENDPOINTS
    public static final String Vegmeal = "/co-api/food/vegmeal";
    public static final String LocalProduce = "/co-api/food/localproduce";
    public static final String BikeInsteadOfCar = "/co-api/transport/bike";
    public static final String BusInsteadOfCar = "/co-api/transport/bus";
    public static final String TrainInsteadOfCar = "/co-api/transport/train";
    public static final String LowerTemperature = "/co-api/utilities/lowertemp";
    public static final String InstallSolarPanel = "/co-api/utilities/solarpanels";
    public static final String LEDLights = "/co-api/utilities/LED";
    public static final String PlantingTrees = "/co-api/misc/plant-trees";
    public static final String CarbonFriendlyHotel = "/co-api/misc/green-hotel";


}
