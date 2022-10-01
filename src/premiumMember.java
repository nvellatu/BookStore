/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author navee
 */
public class premiumMember extends member {
    long cardNum;
    boolean monthlyFeePaid;
    boolean premium = true;
    //constructor
    public premiumMember(String name, boolean monthlyFeePaid, long cardNum) {
        super(name);
        this.cardNum = cardNum;
        this.monthlyFeePaid = monthlyFeePaid;
    }

    //returns the card number of a premium member
    public long getCardNum() {
        return cardNum;
    }
    //sets the card number of a premium member
    public void setCardNum(long cardNum) {
        this.cardNum = cardNum;
    }
    
    //prints out the name, whether or not the monthly fee is paid, the card number, and the membership type of a premium member
    @Override
    void viewProfile(){
        System.out.println("Name: " + this.name);
        System.out.println("Monthly Fee Paid: " + monthlyFeePaid);
        System.out.println("Card Number: " + String.valueOf(this.cardNum));
        System.out.println("Membership: Premium");
    }
}
