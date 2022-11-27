package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;
import utils.ISerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer {

    private ArrayList<App> apps;

    /**
     * Constructor to create an ArrayList to hold apps
     */
    public AppStoreAPI () { apps = new ArrayList<App>(); }

    /**
     * Method returns the apps in the arrayList
     * @return object
     */
    public ArrayList<App> getApps() {
        return apps;
    }

    public void setApps(ArrayList<App> apps) {
        this.apps = apps;
    }


    //Basic CRUD on apps ArrayList
    /**
     * Method adds an app to the arrayList
     * @param app object to be added to arrayList
     */
    public boolean addApp (App app) {
        boolean isAdded = false;

        if (isAdded) {
            return apps.add(app);
        }
        else return isAdded;
    }

    /**
     * Method to locate an app in the arrayList
     * @param index int used to search for the index number and the object stored at that index number
     * @return the index number, if valid. Return null if there is no object at that location.
     */
    public App getAppByIndex (int index) {
        if (isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    public int numberOfApps() {
        return apps.size();
    }

    /**
     * Method to delete an app using the index number
     * @param indexToDelete int representing the index number of the app
     * @return the app object that has been deleted. Return null if no object deleted.
     */
    public App deleteAppByIndex (int indexToDelete) {

        if (isValidIndex(indexToDelete)) {
            App app = getAppByIndex(indexToDelete);

                apps.remove(indexToDelete);
                return app;
            }
        else {
            return null;
        }
    }

    /**
     * search for an app by name
     * @param name String representing the name of the app
     * @return the app if exists, otherwise null
     */
    public App getAppByName (String name) {

        if (isValidAppName(name)) {
            App app = getAppByName(name);
            return app;
        }
        else {
            return null;
        }
    }

    //Reporting methods

    /**
     * Method to list all the apps held in the arrayList
     * @return String built from the app objects. If no apps present return "no apps"
     */
    public String listAllApps () {
        String list = "";

        for (App app : apps) {
            list += "\n" + apps.indexOf(app) + ": " + app;
        }
        if (apps.isEmpty()) {
            return "No apps";
        }
        else {
            return list;
        }
    }

    /**
     * Method to return summary info of all apps in the arrayList
     * @return String representing summary object info. String is 'no apps' if arrayList empty
     */
    public String listSummaryOfAllApps () {
        String list = "";

        for (App app : apps) {
            list += "\n" + apps.indexOf(app) + ": " + app.appSummary();
        }
        if (apps.isEmpty()) {
            return "No apps";
        }
        else {
            return list;
        }
    }

    /**
     * Method to list all game apps held in the arrayList
     * @return String built from the game apps. Return 'no game apps' if no game apps present
     */
    public String listAllGameApps () {
        String list = "";

        for (App app : apps) {
            if (app instanceof GameApp) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
        if (list.isEmpty()) {
            return "No game apps";
        }
        else {
            return list;
        }
    }

    /**
     * Method to return education apps held in the arrayList
     * @return String built from the education apps. 'No education apps' return if no apps held
     */
    public String listAllEducationApps () {
        String list = "";

        for (App app : apps) {
            if (app instanceof EducationApp) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
        if (list.isEmpty()) {
            return "No Education apps";
        }
        else {
            return list;
        }
    }

    /**
     * Method to list all the productivity apps held in the arrayList
     * @return String built from apps. If none present return 'no productivity apps'
     */
    public String listAllProductivityApps () {
        String list = "";

        for (App app : apps) {
            if (app instanceof ProductivityApp) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
        if (list.isEmpty()) {
            return "No Productivity apps";
        }
        else {
            return list;
        }
    }

    /**
     * Method to return list of apps by name
     * @param name String representing the name of the app
     * @return String built of returned apps
     */
    public String listAllAppsByName (String name) {
        String list = "";

        for (App app : apps) {
            if (isValidAppName(name)) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
        if (list.isEmpty()) {
            return "No apps for " + name;
        }
        else {
            return list;
        }
    }

    public String listAllAppsAboveOrEqualAGivenStarRating (int rating) {
        String list = "";

       for (App app : apps) {
               if (rating >= app.calculateRating()) {
                   list += "\n" + apps.indexOf(app) + ": " + app;
               }
           }
       if (list.isEmpty()) {
           return "No apps have a rating of " + rating + " or above";
       }
       else {
           return list;
       }
    }

    /**
     * Method to list all recommended apps held in the arrayList
     * @return String built from the recommended apps, if none return 'no recommended apps'
     */
    public String listAllRecommendedApps () {
        String list = "";

        for (App app : apps) {
            if (app.isRecommendedApp()) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
        if (list.isEmpty()) {
            return "No recommended apps ";
        }
        else {
            return list;
        }
    }

    /**
     * Method to list all apps by a selected developer by comparing the developer object
     * with the apps in the arrayList
     * @param developer object to use for search comparison
     * @return string built from returned matching apps
     */

    public String listAllAppsByChosenDeveloper (Developer developer) {
        String list = "";

        for (App app : apps) {
            if (app.getDeveloper().equals(developer)) {
                list += "\n" + apps.indexOf(app) + ": " + app;
            }
        }
            if (list.isEmpty()) {
                return "No apps for developer: " + developer;
        }
            else {
                return list;
            }
    }

    /**
     * Method to count the number of apps by a specified developer
     * @param developer object passed in for comparison search
     * @return int representing the number of apps written by the specified developer
     */
    public int numberOfAppsByChosenDeveloper (Developer developer) {
        int total = 0;

        for (App app : apps) {
            if (app.getDeveloper().equals(developer)) {
                total++;
            }
        }
        if (total == 0) {
            return 0;
        }
        else {
            return total;
        }
    }

    /**
     * Method to generate a random number to select an app from the arrayList
     * @return App located at the random index number
     * credit to https://www.educative.io/answers/how-to-generate-random-numbers-in-java for
     * how to change a double to an int while using the Math.random method
     */
    public App randomApp () {
        int max = apps.size() - 1;
        int min = 0;
        int index = (int)Math.floor(Math.random()*(max-min+1)+min);

        if (apps.isEmpty()) {
            return null;
        }
        return apps.get(index);
    }

    /**
     * https://www.geeksforgeeks.org/how-to-swap-two-elements-in-an-arraylist-in-java
     * @param apps the arrayList
     * @param i int representing the index number of first object in swap
     * @param j int representing the index number of second object in swap
     */

    private void swapApps (List <App> apps, int i, int j) {
        Collections.swap(apps, i, j);
    }

    public void sortAppsByNameAscending(){
        int j = 1;
        int i = 0;

        while (j <= apps.size()){
            apps.get(i).getAppName().compareToIgnoreCase(apps.get(j).getAppName());
            swapApps (apps, i, j);
            j++; i++;
        }
    }



    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }

    /**
     * Method to check that the name of an app is valid
     * @param appName string with the name of the app
     * @return boolean, true if the name is valid, otherwise false
     */
    public boolean isValidAppName (String appName) {

        boolean isValidName = false;

        if (apps.isEmpty()) {
            return isValidName;
        }
        else {
            for (int i = 0; i<apps.size(); i++) {
                if (apps.get(i).getAppName().toLowerCase().contains(appName.toLowerCase())) {
                    return isValidName;
                }
            }
        }
        return isValidName;
    }

    /**
     * Method to check index number of an app in the arrayList is valid
      * @param index int representing the potential index in the arrayList
     * @return true if the number is a valid index number, false otherwise
     */
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }


    //---------------------
    // Persistence methods
    //---------------------

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (ArrayList<App>) in.readObject(); //cast from List<App> to ArrayList<App> by Jen
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }

}
