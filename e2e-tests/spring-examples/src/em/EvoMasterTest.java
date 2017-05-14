

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import static org.evomaster.clientJava.controllerApi.EMTestUtils.*;


/**
 * This file was automatically generated by EvoMaster on 2017-05-01T14:18:28.879+02:00[Europe/Oslo]
 * <br>
 * The generated test suite contains 2 tests
 */
public class EvoMasterTest {

    
    private static com.foo.rest.examples.spring.positiveinteger.PIController controller = new com.foo.rest.examples.spring.positiveinteger.PIController();
    private static String baseUrlOfSut;
    
    
    @BeforeClass
    public static void initClass() {
        baseUrlOfSut = controller.startSut();
        assertNotNull(baseUrlOfSut);
    }
    
    
    @AfterClass
    public static void tearDown() {
        controller.stopSut();
    }
    
    
    @Before
    public void initTest() {
        controller.resetStateOfSUT();
    }
    
    
    
    
    @Test
    public void test0() throws Exception {
        
        given().accept("*/*")
                .get(baseUrlOfSut + "/api/pi/288617323")
                .then()
                .statusCode(200);
    }
    
    
    @Test
    public void test1() throws Exception {
        
        given().accept("*/*")
                .contentType("application/json")
                .body("{\"value\":-179}")
                .post(baseUrlOfSut + "/api/pi")
                .then()
                .statusCode(200);
    }


}