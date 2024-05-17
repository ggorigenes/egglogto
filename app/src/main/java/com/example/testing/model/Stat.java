package com.example.testing.model;


public class Stat {

    private int highScore;
    private int difficultyLimit;
    private int difficulty = 7;
    private int spawnSpeeder;
    private int fallSpeeder;
    private int spawnSpeederIncrease;
    private int fallSpeederIncrease;
    private int doubleEggSpawnStart;
    private int normalEggScore;
    private int goldEggScore;
    private int chickenClicked = 0;
    private int chickenToLife = 3;
    private int randomEggDropScore = 150;
    private int hatScore = 100;

    public Stat(String gameMode) {

        switch (gameMode) {
            case "Medium":
                this.highScore = 2000;
                this.difficultyLimit = 100;
                this.spawnSpeeder = 80;
                this.spawnSpeederIncrease = 80;
                this.fallSpeeder = 160;
                this.fallSpeederIncrease = 60;
                this.doubleEggSpawnStart = 900;
                this.normalEggScore = 20;
                this.goldEggScore = 80;
                break;

            case "Hard":
                this.highScore = 2500;
                this.difficultyLimit = 200;
                this.spawnSpeeder = 160;
                this.spawnSpeederIncrease = 80;
                this.fallSpeeder = 320;
                this.fallSpeederIncrease = 80;
                this.doubleEggSpawnStart = 900;
                this.normalEggScore = 30;
                this.goldEggScore = 100;
                break;

            default:
                this.highScore = 1500;
                this.difficultyLimit = 200;
                this.spawnSpeeder = 0;
                this.spawnSpeederIncrease = 80;
                this.fallSpeeder = 100;
                this.fallSpeederIncrease = 40;
                this.doubleEggSpawnStart = 900;
                this.normalEggScore = 10;
                this.goldEggScore = 50;
                break;

        }

        this.highScore = highScore;
        this.difficultyLimit = difficultyLimit;
        this.spawnSpeeder = spawnSpeeder;
        this.fallSpeeder = fallSpeeder;
        this.doubleEggSpawnStart = doubleEggSpawnStart;
        this.normalEggScore = normalEggScore;
        this.goldEggScore = goldEggScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getDifficultyLimit() {
        return difficultyLimit;
    }

    public void setDifficultyLimit(int difficultyLimit) {
        this.difficultyLimit = difficultyLimit;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSpawnSpeeder() {
        return spawnSpeeder;
    }

    public void setSpawnSpeeder(int spawnSpeeder) {
        this.spawnSpeeder = spawnSpeeder;
    }

    public int getFallSpeeder() {
        return fallSpeeder;
    }

    public void setFallSpeeder(int fallSpeeder) {
        this.fallSpeeder = fallSpeeder;
    }

    public int getDoubleEggSpawnStart() {
        return doubleEggSpawnStart;
    }

    public void setDoubleEggSpawnStart(int doubleEggSpawnStart) {
        this.doubleEggSpawnStart = doubleEggSpawnStart;
    }

    public int getNormalEggScore() {
        return normalEggScore;
    }

    public void setNormalEggScore(int normalEggScore) {
        this.normalEggScore = normalEggScore;
    }

    public int getGoldEggScore() {
        return goldEggScore;
    }

    public void setGoldEggScore(int goldEggScore) {
        this.goldEggScore = goldEggScore;
    }

    public int getSpawnSpeederIncrease() {
        return spawnSpeederIncrease;
    }

    public void setSpawnSpeederIncrease(int spawnSpeederIncrease) {
        this.spawnSpeederIncrease = spawnSpeederIncrease;
    }

    public int getFallSpeederIncrease() {
        return fallSpeederIncrease;
    }

    public void setFallSpeederIncrease(int fallSpeederIncrease) {
        this.fallSpeederIncrease = fallSpeederIncrease;
    }

    public int getChickenClicked() {
        return chickenClicked;
    }

    public void setChickenClicked(int chickenClicked) {
        this.chickenClicked = chickenClicked;
    }

    public int getChickenToLife() {
        return chickenToLife;
    }

    public void setChickenToLife(int chickenToLife) {
        this.chickenToLife = chickenToLife;
    }

    public int getRandomEggDropScore() {
        return randomEggDropScore;
    }

    public void setRandomEggDropScore(int randomEggDropScore) {
        this.randomEggDropScore = randomEggDropScore;
    }

    public int getHatScore() {
        return hatScore;
    }

    public void setHatScore(int hatScore) {
        this.hatScore = hatScore;
    }
}
