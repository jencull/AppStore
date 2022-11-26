package models;

public class GameApp extends App {

    boolean isMultiplayer = false;

    public GameApp (Developer developer, String appName, double appSize,
                    double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;

    }

    public boolean isRecommendedApp() {

        if ((isMultiplayer) && (super.calculateRating()>=4.0)) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public String appSummary () {
        return super.appSummary() + "Multiplayer: " +  isMultiplayer;
    }

    public String toString () {
        return super.toString() + " Multiplayer: " +  isMultiplayer +
                " Ratings (" + calculateRating() + ")";
    }

}
