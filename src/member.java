/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author navee
 */
public class member {

    boolean premium;
    
    String name;
    

    
    //constructor
    public member(String name) {
        this.name = name;
    }

    //prints out name and membership type of a member when called
    void viewProfile(){
        System.out.println("Name: " + this.name);
        System.out.println("Membership: Regular");
    }
    
    
    

}
