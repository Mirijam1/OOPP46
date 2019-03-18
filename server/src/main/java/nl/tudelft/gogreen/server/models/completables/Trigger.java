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


    // -----{ Category: Transport (1) ]-----
    COMPLETED_TRANSPORT_ACTIVITY,

    //  --[[ Activity: Use bike instead of bus ]]--
    USED_BIKE_INSTEAD_BUS,


    // -----{ Category: Energy (2) ]-----
    COMPLETED_ENERGY_ACTIVITY,


    // -----{ Category: Misc (3) ]-----
    COMPLETED_MISC_ACTIVITY,

    //  --[[ Activity: Use bike instead of bus ]]--
    PLANTED_TREE,
}
