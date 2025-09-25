package storefront;

import org.testng.asserts.SoftAssert;


public class CollectAssertMessages {

    public CollectAssertMessages(){super();}

    private static final ThreadLocal<SoftAssert> threadLocal = new ThreadLocal<>();

    public static SoftAssert getSoftAssertions() {
        return threadLocal.get();
    }

    public static void setSoftAssertions(SoftAssert softAssert) {
        threadLocal.set(softAssert);
    }
}