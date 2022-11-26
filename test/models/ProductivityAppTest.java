package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductivityAppTest {

    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private Developer developerCal = new Developer("Calendar", "www.calendar.com");
    private Developer developerList = new Developer("ToDo", "www.todo.com");


    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0).
        prodAppBelowBoundary = new ProductivityApp(developerCal, "Calendar", 1, 1.0, 0);
        prodAppOnBoundary = new ProductivityApp(developerCal, "Calendar1", 1000, 2.0, 1.99);
        prodAppAboveBoundary = new ProductivityApp(developerCal, "Calendar2", 1001, 3.5, 2.99);
        prodAppInvalidData = new ProductivityApp(developerCal, "", -1, 0, -1.00);
    }

    @AfterEach
    void tearDown() {
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerCal = developerList = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerCal, prodAppBelowBoundary.getDeveloper());
            assertEquals(developerCal, prodAppOnBoundary.getDeveloper());
            assertEquals(developerCal, prodAppAboveBoundary.getDeveloper());
            assertEquals(developerCal, prodAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("Calendar", prodAppBelowBoundary.getAppName());
            assertEquals("Calendar1", prodAppOnBoundary.getAppName());
            assertEquals("Calendar2", prodAppAboveBoundary.getAppName());
            assertEquals("", prodAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, prodAppBelowBoundary.getAppSize());
            assertEquals(1000, prodAppOnBoundary.getAppSize());
            assertEquals(0, prodAppAboveBoundary.getAppSize());
            assertEquals(0, prodAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, prodAppBelowBoundary.getAppVersion());
            assertEquals(2.0, prodAppOnBoundary.getAppVersion());
            assertEquals(3.5, prodAppAboveBoundary.getAppVersion());
            assertEquals(1.0, prodAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, prodAppBelowBoundary.getAppCost());
            assertEquals(1.99, prodAppOnBoundary.getAppCost());
            assertEquals(2.99, prodAppAboveBoundary.getAppCost());
            assertEquals(0, prodAppInvalidData.getAppCost());
        }

        @Nested
        class Setters {

            @Test
            void setDeveloper() {
                //no validation in models
                assertEquals(developerCal, prodAppBelowBoundary.getDeveloper());
                prodAppBelowBoundary.setDeveloper(developerList);
                assertEquals(developerList, prodAppBelowBoundary.getDeveloper());
            }

            @Test
            void setAppName() {
                //no validation in models
                assertEquals("Calendar", prodAppBelowBoundary.getAppName());
                prodAppBelowBoundary.setAppName("Mindstorms");
                assertEquals("Mindstorms", prodAppBelowBoundary.getAppName());
            }

            @Test
            void setAppSize() {
                //Validation: appSize(1-1000)
                assertEquals(1, prodAppBelowBoundary.getAppSize());

                prodAppBelowBoundary.setAppSize(1000);
                assertEquals(1000, prodAppBelowBoundary.getAppSize()); //update

                prodAppBelowBoundary.setAppSize(1001);
                assertEquals(1000, prodAppBelowBoundary.getAppSize()); //no update

                prodAppBelowBoundary.setAppSize(2);
                assertEquals(2, prodAppBelowBoundary.getAppSize()); //update

                prodAppBelowBoundary.setAppSize(0);
                assertEquals(2, prodAppBelowBoundary.getAppSize()); //no update
            }

            @Test
            void setAppVersion() {
                //Validation: appVersion(>=1.0)
                assertEquals(1.0, prodAppBelowBoundary.getAppVersion());

                prodAppBelowBoundary.setAppVersion(2.0);
                assertEquals(2.0, prodAppBelowBoundary.getAppVersion()); //update

                prodAppBelowBoundary.setAppVersion(0.0);
                assertEquals(2.0, prodAppBelowBoundary.getAppVersion()); //no update

                prodAppBelowBoundary.setAppVersion(1.0);
                assertEquals(1.0, prodAppBelowBoundary.getAppVersion()); //update
            }

            @Test
            void setAppCost() {
                //Validation: appCost(>=0)
                assertEquals(0.0, prodAppBelowBoundary.getAppCost());

                prodAppBelowBoundary.setAppCost(1.0);
                assertEquals(1.0, prodAppBelowBoundary.getAppCost()); //update

                prodAppBelowBoundary.setAppCost(-1);
                assertEquals(1.0, prodAppBelowBoundary.getAppCost()); //no update

                prodAppBelowBoundary.setAppCost(0.0);
                assertEquals(0.0, prodAppBelowBoundary.getAppCost()); //update
            }

            @Nested
            class ObjectStateMethods {

                @Test
                void appSummaryReturnsCorrectString() {
                    ProductivityApp prodApp = setupProductivityAppWithRating(3, 4);
                    String stringContents = prodApp.appSummary();

                    assertTrue(stringContents.contains(prodApp.getAppName() + "(V" + prodApp.getAppVersion()));
                    assertTrue(stringContents.contains(prodApp.getDeveloper().toString()));
                    assertTrue(stringContents.contains("€" + prodApp.getAppCost()));
                    assertTrue(stringContents.contains("Rating: " + prodApp.calculateRating()));
                }

                @Test
                void toStringReturnsCorrectString() {
                    ProductivityApp prodApp = setupProductivityAppWithRating(3, 4);
                    String stringContents = prodApp.toString();

                    assertTrue(stringContents.contains(prodApp.getAppName()));
                    assertTrue(stringContents.contains("(Version " + prodApp.getAppVersion()));
                    assertTrue(stringContents.contains(prodApp.getDeveloper().toString()));
                    assertTrue(stringContents.contains(prodApp.getAppSize() + "MB"));
                    assertTrue(stringContents.contains("Cost: " + prodApp.getAppCost()));
                    assertTrue(stringContents.contains("Ratings (" + prodApp.calculateRating()));

                    //contains list of ratings too
                    assertTrue(stringContents.contains("John Doe"));
                    assertTrue(stringContents.contains("Very Good"));
                    assertTrue(stringContents.contains("Jane Doe"));
                    assertTrue(stringContents.contains("Excellent"));
                }

                @Nested
                class RecommendedApp {

                    @Test
                    void appIsNotRecommendedWhenInAppCostIs99c() {
                        //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
                        ProductivityApp prodApp = setupProductivityAppWithRating(3, 4);

                        //now setting appCost to 0.99 so app should not be recommended now
                        prodApp.setAppCost(0.99);
                        assertFalse(prodApp.isRecommendedApp());
                    }

                    @Test
                    void appIsNotRecommendedWhenRatingIsLessThan2() {
                        //setting all conditions to true with ratings of 2 and 2 (i.e. 2.0)
                        ProductivityApp prodApp = setupProductivityAppWithRating(2, 2);
                        //verifying recommended app returns false (rating not high enough
                        assertFalse(prodApp.isRecommendedApp());
                    }

                    @Test
                    void appIsNotRecommendedWhenNoRatingsExist() {
                        //setting all conditions to true with no ratings
                        ProductivityApp prodApp = new ProductivityApp(developerList, "ToDo", 1,
                                1.0, 1.99);
                        //verifying recommended app returns true
                        assertFalse(prodApp.isRecommendedApp());
                    }

                    @Test
                    void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
                        //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
                        ProductivityApp prodApp = setupProductivityAppWithRating(3, 4);

                        //verifying recommended app returns true
                        assertTrue(prodApp.isRecommendedApp());
                    }

                }

                ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
                    //setting all conditions to true
                    ProductivityApp prodApp = new ProductivityApp(developerCal, "Calendar", 1,
                            1.0, 1.99);
                    prodApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
                    prodApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

                    //verifying all conditions are true for a recommended educational app]
                    assertEquals(2, prodApp.getRatings().size());  //two ratings are added
                    assertEquals(1.99, prodApp.getAppCost(), 0.01);
                    assertEquals(((rating1 + rating2) / 2.0), prodApp.calculateRating(), 0.01);


                    return prodApp;
                }
            }
        }
    }
}


