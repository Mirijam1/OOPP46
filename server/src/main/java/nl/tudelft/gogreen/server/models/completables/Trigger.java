package nl.tudelft.gogreen.server.models.completables;

public enum Trigger {
    // General triggers
    SUBMITTED_ACTIVITY,
    GAINED_POINT,
    TOP_10_WORLD,
    TOP_3_WORLD,
    TOP_1_WORLD,

    // Social triggers
    MADE_FRIEND,
    JOINED_GROUP,

    // Specific triggers
    // -----[ Category: Food (0) ]-----
    COMPLETED_FOOD_ACTIVITY,

    //   --[[ Activity: Eat vegetarian meal ]]--
    ATE_VEGETARIAN_MEAL,

    //   --[[ Activity: Buy local produce ]]--
    BOUGHT_LOCAL_PRODUCE,

    // -----{ Category: Transport (1) ]-----
    COMPLETED_TRANSPORT_ACTIVITY,

    //  --[[ Activity: Use bike instead of bus ]]--
    USED_BIKE_INSTEAD_BUS,

    //  --[[ Activity: Use public transport instead of your car ]]--
    USED_PT_INSTEAD_OF_CAR,

    // -----{ Category: Utilities (2) ]-----
    COMPLETED_UTILITIES_ACTIVITY,

    //  --[[ Activity: Lower the temperature of your home ]]--
    LOWERED_TEMP,

    //  --[[ Activity: Install solar panels ]]--
    INSTALLED_SOLAR_PANELS,

    // -----{ Category: Misc (3) ]-----
    COMPLETED_MISC_ACTIVITY,

    //  --[[ Activity: Use bike instead of bus ]]--
    PLANTED_TREE,


    // Badge triggers
    ACHIEVED_BADGE,
}
