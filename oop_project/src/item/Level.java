package item;

import java.util.ArrayList;

public class Level {
    private String name;
    private int stars;
    private int height;

    private int width;

    private ArrayList<String> pieces;


    public Level(String name, int stars, int width, int height, ArrayList<String> pieces) {
        setName(name);
        setAuthor();
        setStars(stars);
        setWidth(width);
        setHeight(height);
        setPieces(pieces);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.isBlank() ? "Untitled" : name;
    }

    public void setAuthor() {
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = Math.max(Math.min(stars, 5), 0);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }
}
