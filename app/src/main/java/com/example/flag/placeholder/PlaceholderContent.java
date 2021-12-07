package com.example.flag.placeholder;

import com.example.flag.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {
    static String [] country={"China","America","Germany","England","France"};

    static int [] imageName={R.drawable.country1,R.drawable.country2,R.drawable.country3,R.drawable.country4,R.drawable.country5};

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {
        return new PlaceholderItem(String.valueOf(position),country[position-1], imageName[position-1]);

    }
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String id;
        public final String countryName;
        public final int countryImage;

        public PlaceholderItem(String id, String countryName, int countryImage) {
            this.id = id;
            this.countryName = countryName;
            this.countryImage = countryImage;
        }

        @Override
        public String toString() {
            return  countryName;
        }
    }
}