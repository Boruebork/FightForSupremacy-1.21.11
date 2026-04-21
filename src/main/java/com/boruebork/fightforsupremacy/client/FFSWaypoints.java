package com.boruebork.fightforsupremacy.client;

import dev.ftb.mods.ftbchunks.client.map.WaypointType;

public class FFSWaypoints {
    public static final WaypointType CAPITAL =
            WaypointType.builder().canChangeColor().build("capital");
    public static final WaypointType RADAR =
            WaypointType.builder().build("radar");

    public static void init() {} // just to trigger class loading
}
