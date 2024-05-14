package com.example.testing.model;

public class Egg {

    private boolean isClick;

    private String eggType;

    private int clickIsClickCount = 0;
    public Egg(String eggType) {
        this.isClick = isClick = false;
        this.eggType = eggType;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getEggType() {
        return eggType;
    }

    public void setEggType(String eggType) {
        this.eggType = eggType;
    }

    public int getClickIsClickCount() {
        return clickIsClickCount;
    }

    public void setClickIsClickCount(int clickIsClickCount) {
        this.clickIsClickCount = clickIsClickCount;
    }
}
