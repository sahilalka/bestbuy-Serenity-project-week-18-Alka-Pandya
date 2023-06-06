package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTestWithSteps extends TestBase {
    static String name = "Minnetonka" + TestUtils.getRandomValue();
    static String type = "BigBox" + TestUtils.getRandomValue();
    static String address = "13513 Ridgedale Dr";
    static String address2 = "";
    static String city = "Hopkins";
    static String state = "MN";
    static String zip = "55305";
    static Double lat = 44.969658;
    static Double lng =  -93.449539;
    static String hours =  "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;
    @Steps
    StoreSteps storeSteps;
    @Title("This will create a new store")
    @Test
    public void test001(){
        ValidatableResponse response=storeSteps.createANewStore(name,type,address,address2,city,zip,state,lat,lng,hours);
        storeId = response.log().all().extract().path("id");
        response.log().all().statusCode(201);

    }

    @Title("Verify If the Store was Added to the application")
    @Test
    public void test002(){
        HashMap<String,Object> storeMap= storeSteps.getStoreInfoById(storeId);
        Assert.assertThat(storeMap, hasValue(storeId));
        System.out.println(storeMap);

    }
    @Title("Update the Store information and Verify the Updated information")
    @Test
    public  void test003(){
        name=name + "_Updated";
        storeSteps.updateStore(name,type,address,address2,city,zip,state,lat,lng,hours,storeId)
                .statusCode(200);
        HashMap<String,Object> storeMap = storeSteps.getStoreInfoById(storeId);
        Assert.assertThat(storeMap, hasValue(storeId));
    }
    @Title("Deleting product information and verify if the product is deleted")
    @Test
    public void test004() {
        storeSteps.deleteAStore(storeId)
                .statusCode(200);


    }


}
