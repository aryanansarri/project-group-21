package View;

public enum CommandTypes {
    INFO,

    SELECT_COMBAT_UNIT,
    SELECT_NONCOMBAT_UNIT,
    SELECT_CITY_BY_NAME,
    SELECT_CITY_BY_POSITION,

    UNIT_MOVE,
    UNIT_SLEEP,
    UNIT_ALERT,
    UNIT_FORTIFY,
    UNIT_FORTIFY_AND_HEAL,
    UNIT_GARRISON,
    UNIT_SETUP_FOR_RANGED,
    UNIT_ATTACK,
    UNIT_FOUND_CITY,
    UNIT_CANCEL_MISSION,
    UNIT_WAKE,
    UNIT_DELETE,
    UNIT_BUILD_ROAD,
    UNIT_BUILD_RAILROAD,
    UNIT_BUILD_IMPROVEMENT,
    UNIT_REMOVE,
    UNIT_REPAIR,

    MAP_SHOW_BY_POSITION,
    MAP_SHOW_BY_CITY_NAME,
    MAP_MOVE,

    CITY_PURCHASE_UNIT,
    CITY_PURCHASE_TILE,
    CITY_BANNER,
    CITY_LOCK_CITIZEN,
    CITY_REMOVE_FROM_WORK,
    CITY_DESTROY,
    CITY_GET_OUTPUT;

}
