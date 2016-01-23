package com.mandiriecash.ecashtoll.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListVehicleContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Vehicle> ITEMS = new ArrayList<Vehicle>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Vehicle> ITEM_MAP = new HashMap<String, Vehicle>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Vehicle item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Vehicle createDummyItem(int position) {
        return new Vehicle(String.valueOf(position), "Mobil " + position, makeRandomPlateNumber(position));
    }

    private static String makeRandomPlateNumber(int position) {
        return "A "+position+" B";
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Vehicle {
        public final String id;
        public final String name;
        public final String plateNo;

        public Vehicle(String id,String name,String plateNo){
            this.id = id;
            this.name = name;
            this.plateNo = plateNo;
        }


        @Override
        public String toString() {
            return id + " " + name + " " + plateNo;
        }
    }
}
