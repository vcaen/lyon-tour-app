package fr.vcaen.lyontour.models;

/**
 * Created by vcaen on 28/04/2015.
 */
public class PointInteret {

    String id;
    String title;
    String imageURL;
    String description;

    public PointInteret(String id) {
        this.id = id;
    }

    public PointInteret(String id, String title, String imageURL, String description) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
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
}
