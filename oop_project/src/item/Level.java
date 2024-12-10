package item;

import gui.TicTacToeCell;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Level {
    private String name;
    private String author;
    private int stars;
    private int height;

    private int width;

    private ArrayList<String> pieces;


    public Level(String name, String author, int stars, int width, int height, ArrayList<String> pieces) {
        setName(name);
        setAuthor(author);
        setStars(stars);
        setWidth(width);
        setHeight(height);
        setPieces(pieces);
    }

    public Level(String name, String author, String stars, String description) {
        setName(name);
        setAuthor(author);
        try {
            int rating = Integer.parseInt(stars);
            setStars(rating);
        } catch (NumberFormatException ignored) {
            setStars(0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.isBlank() ? "Untitled" : name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.isBlank() ? "Anonymous" : author;
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
