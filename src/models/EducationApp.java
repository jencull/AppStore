package models;

import utils.Utilities;

public class EducationApp extends App {

    int level = 0;

    public EducationApp (Developer developer, String appName,
                        double appSize, double appVersion, double appCost, int level) {

        super(developer, appName, appSize, appVersion, appCost);

        if (Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    @Override
    public boolean isRecommendedApp() {

        if ((super.getAppCost() > 0.99) && (super.calculateRating() >= 3.5) && (level >= 3)) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getLevel() {

        if (Utilities.validRange(level, 1, 10)) {
            return level;
        }
        else {
            return level = 0;
        }
    }

    public void setLevel(int level) {
        if (Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    public String appSummary () {
        return super.appSummary() + " level " + getLevel();
    }

    public String toString () {
        return super.toString() + " Level: " + level + " Ratings (" + calculateRating() + ")";
    }


}
