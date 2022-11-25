package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {

    private Developer developer;
    private String appName = "No app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private List<Rating> ratings = new ArrayList<>();

    public App (Developer developer, String appName, double appSize, double appVersion, double appCost) {
        this.developer = new Developer(developer.getDeveloperName(), developer.getDeveloperWebsite());
        this.appName = appName;

        if (Utilities.validRange(appSize, 1,1000)) {
            this.appSize = appSize;
        }

        if (Utilities.validDoubleRange(appVersion, 1.0, 5.0)) {
            this.appVersion = appVersion;
        }

        this.appCost = appCost;

    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String toString () {
        return appName + " (Version " + appVersion + ") " + getDeveloper().toString()
                + " " + appSize + "MB Cost: " + appCost + " " + listRatings();
    }

    public String appSummary () {
        return developer + " " + appName + "(V" + appVersion + ")"
                + ", €" + appCost + ". Rating: " + calculateRating();
    }

    public boolean addRating (Rating rating) { return ratings.add(rating); }

    public double calculateRating () {
        if (ratings.isEmpty()) {
            return 0;
        }
        else {
            double totalStars = 0;
            int numRatings = 0;
            for (Rating rating: ratings) {
                if (rating.getNumberOfStars() != 0) {
                    totalStars += rating.getNumberOfStars();
                    numRatings++;
                }
            }
            return totalStars/numRatings;
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

    public boolean updateRating (int indexToUpdate, String raterName, String ratingComment) {
        Rating findRating = findRating(indexToUpdate);

        if (findRating != null) {
            findRating.setRaterName(raterName);
            findRating.setRatingComment(ratingComment);
        }
        return false;
    }

    public Rating deleteRating (int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return ratings.remove(indexToDelete);
        }
        return null;
    }

    public Rating findRating (int index) {
        if (isValidIndex(index)) {
            return ratings.get(index);
        }
        return null;
    }

    public boolean isValidIndex (int index) {
        return index >= 0 && index <= this.ratings.size();
    }

    public abstract boolean isRecommendedApp ();

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    //check this one, if statement may not be needed
    public double getAppSize() {
        if (appSize >= 1 && appSize <=1000) {
            return appSize;
        }
        else  return appSize = 0;
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
        if (Utilities.validDoubleRange(appVersion, 1.0, 5.0)) {
            this.appVersion = appVersion;
        }
    }

    public double getAppCost() {
        if (appCost > 0) {
            return appCost;
        }
        else return 0;
    }

    public void setAppCost(double appCost) {
        if (appCost >= 0) {
            this.appCost = appCost;
        }
    }

    public List<Rating> getRatings() {
        return ratings;
    }

}
