
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author navee
 */
public interface aStore {
    
    //array lists holding cart items

    static ArrayList<inventoryItem> cart = new ArrayList<inventoryItem>();
    //array list of members
    static ArrayList<member> members = new ArrayList<member>() {
        {
            add(new premiumMember("David", false, 2720994985726875L));
            add(new member("Ryan"));
            add(new member("Jasper"));
            add(new premiumMember("Conner", false, 6011067478953477L));
            add(new premiumMember("Jake", false, 5531964776925061L));
            add(new member("Cameron"));
            add(new premiumMember("Naveen", true, 36790542558758L));
        }
    };
}
