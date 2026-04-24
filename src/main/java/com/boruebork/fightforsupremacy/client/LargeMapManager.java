package com.boruebork.fightforsupremacy.client;

import com.boruebork.fightforsupremacy.client.mapicon.MapIconEx;
import dev.ftb.mods.ftbchunks.api.client.event.MapIconEvent;

public class LargeMapManager {
   public static void init(){
       MapIconEvent.LARGE_MAP.register((event)->{
           event.add(new MapIconEx());
       });
   }
}