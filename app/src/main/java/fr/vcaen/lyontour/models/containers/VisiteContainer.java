package fr.vcaen.lyontour.models.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.vcaen.lyontour.models.PointInteret;

/**
 * Created by vcaen on 28/04/2015.
 */
public class VisiteContainer {

    public static HashMap<String, PointInteret> PI_MAP = new HashMap<String, PointInteret>();
    public static List<PointInteret> PI_LIST = new ArrayList<PointInteret>();

    public static void addItem(PointInteret item) {
        PI_LIST.add(item);
        PI_MAP.put(item.getId(), item);
    }

    public static void addAll(List<PointInteret> list ) {
        PI_LIST.addAll(list);
        for(PointInteret pi : list) {
            PI_MAP.put(pi.getId(), pi);
        }
    }

    public static void clear() {
        PI_LIST.clear();
        PI_MAP.clear();
    }
}
