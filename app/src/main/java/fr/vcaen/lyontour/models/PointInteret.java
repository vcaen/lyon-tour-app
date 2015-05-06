package fr.vcaen.lyontour.models;

import java.util.Date;

/**
 * Created by vcaen on 28/04/2015.
 */
public class PointInteret {

    public static enum Meteo {
        SUNNY("sunny"),
        CLOUDLY("cloudly"),
        MOSTY_CLOUDLY("partly_cloudly"),
        RAINY("rainy");

        public final String text;

        private Meteo(String s) {
            text = s;
        }
    }

    String id;
    String title;
    String imageURL;
    String description;
    Date date;
    String meteo;
    Double latitude;
    Double longitude;

    public PointInteret(String id) {
        this.id = id;
    }

    public PointInteret(String id, String title, String imageURL, String description, Date date, String meteo, Double latitude, Double longitude) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
        this.date = date;
        this.meteo = meteo;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }


    public Date getDate() {
        return date;
    }

    public String getMeteo() {
        return meteo;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
