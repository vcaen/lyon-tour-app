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


    static {
        // Add 3 sample items.
        addItem(new PointInteret("0", "Title 0", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));
        addItem(new PointInteret("1", "Title 1", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));
        addItem(new PointInteret("2", "Title 2", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));
        addItem(new PointInteret("3", "Title 3", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));
        addItem(new PointInteret("4", "Title 4", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));
        addItem(new PointInteret("5", "Title 5", "http://t3.gstatic.com/images?q=tbn:ANd9GcR6B4bu54I4uyWSaOwhzG0Mz--kY0EaoyqMtAknpi6JEo_004GXCi-4hIqN", "Desc"));

    }

    public static void addItem(PointInteret item) {
        PI_LIST.add(item);
        PI_MAP.put(item.getId(), item);
    }
}
