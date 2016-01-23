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
public class LogActivityContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<LogActivity> ITEMS = new ArrayList<LogActivity>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, LogActivity> ITEM_MAP = new HashMap<String, LogActivity>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(LogActivity item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static LogActivity createDummyItem(int position) {
        return new LogActivity(String.valueOf(position),
                "Tol "+position,
                "Tol 2 "+position,
                "Kendaraan "+position,
                System.currentTimeMillis(),
                100000);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class LogActivity {
        public final String id;
        public final String sourceTollName;
        public final String destTollName;
        public final int price;
        public final String vehicleName;
        public final long timestamp;

        public LogActivity(String id, String sourceTollName,String destTollName,String vehicleName,long timestamp,int price){
            this.id = id;
            this.sourceTollName = sourceTollName;
            this.destTollName = destTollName;
            this.vehicleName = vehicleName;
            this.timestamp = timestamp;
            this.price = price;
        }

        @Override
        public String toString() {
            return this.sourceTollName + " " + this.destTollName + " " + this.vehicleName + " " + this.price;
        }
    }
}
