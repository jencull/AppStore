package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {

    private List<Developer> developer;
    private String appName = "No app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private List<Rating> ratings = new ArrayList<>();

    public App (Developer developer, String appName, double appSize, double appVersion, double appCost) {
        getDeveloper();
        this.appName = appName;
        this.appSize = appSize;
        this.appVersion = appVersion;
        this.appCost = appCost;

    }

    public String toString () {
        return developer + ", " + appName + ", " + appSize + ", " + appVersion + ". " + appCost + ". ";
    }

    public boolean addRating (Rating rating) { return ratings.add(rating); }

    public String appSummary () {
        return appName + " (V" + appVersion + ") by " + developer
                + ", €" + appCost + ". Rating: " + calculateRating();
    }

    public double calculateRating () {
        if (ratings.isEmpty()) {
            return 0;
        }
        else {
            double total = 0;
            int numRatings = 0;
            for (Rating rating: ratings) {
                if (rating.getNumberOfStars() != 0) {
                    total++;
                    numRatings++;
                }
            }
            return total/numRatings;
        }
    }

    public String listRatings () {
        if (ratings.isEmpty()) {
            return "No ratings added yet.";
        }
        else {
            String list = "";
            for (int i = 0; i< ratings.size(); i++) {
                list += "\n" + ratings.get(i);
            }
            return list;
        }
    }

    public abstract boolean isRecommendedApp ();

    public List<Developer> getDeveloper() {
        return developer;
    }

    public void setDeveloper(List<Developer> developer) {
        this.developer = developer;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize, 1,1000)) {
            this.appSize = appSize;
        }
    }

    public double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(double appVersion) {
        if (appVersion >= 1.0) {
            this.appVersion = appVersion;
        }
    }

    public double getAppCost() {
        return appCost;
    }

    public void setAppCost(double appCost) {
        if (appCost >= 0) {
            this.appCost = appCost;
        }
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
