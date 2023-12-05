package com.example.memorycardgame;

public class Card {

    public boolean flipped = false;
    private final String imagePath;


    private int[] position = new int[2];

    public Card(String imagePath, int[] position){
        this.imagePath = imagePath;
        this.position = position;

    }
    public void flip(){
        flipped = !flipped;
    }
    public int[] getPosition(){
        return position;
    }

    public String getImagePath() {
        return imagePath;
    }
}
