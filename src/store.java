
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.util.InputMismatchException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author navee
 */
public class store implements aStore {

    static String name;
    static int memberIndex;
    static ArrayList<inventoryItem> books = new ArrayList<inventoryItem>();
    static ArrayList<inventoryItem> cds = new ArrayList<inventoryItem>();
    static ArrayList<inventoryItem> dvds = new ArrayList<inventoryItem>();
    static double TOTAL = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //member counter for EOD report
        int newMembers = 0;

        
        try {
            
            Scanner fileScanner = new Scanner(new File("ProductInventory-1.csv"));
            String[] data;
            while (fileScanner.hasNext()) {
                data = fileScanner.nextLine().split(",");
                if (data[1].equals("book")) {
                    books.add(new Book(data[2], Integer.valueOf(data[3]), Double.valueOf(data[4])));
                } else if (data[1].equals("cd")) {
                    cds.add(new CD(data[2], Integer.valueOf(data[3]), Double.valueOf(data[4])));
                } else if (data[1].equals("dvd")) {
                    dvds.add(new DVD(data[2], Integer.valueOf(data[3]), Double.valueOf(data[4])));
                }
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("An error occurred.");
        }
        
        
        
        // TODO code application logic here
        try {
            boolean repeat = false;
            do {
                Scanner myObj = new Scanner(System.in);
                System.out.println("Hello welcome to Naveen's Bookstore. What would you like to do? (enter number):");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Make Purchase as guest");
                System.out.println("4. Exit");

                int input = myObj.nextInt();
                switch (input) {
                    case 1:                     //login
                        int type = login();
                        if (type == 1) {
                            loggedInPurchase(1);
                            repeat = false;
                        }
                        if (type == 2) {
                            if ((!((premiumMember) members.get(memberIndex)).monthlyFeePaid) && (members.get(memberIndex).premium)) {
                                System.out.println("Hi " + name + " you need to pay your monthly fee. It has been added to your cart.");
                                cart.add(new cartItem("Membership Fee", 1, 25));
                            }

                            loggedInPurchase(2);
                            repeat = false;
                        }
                        if (type == 3) {
                            repeat = true;
                        }
                        break;
                    case 2:                     //register
                        Scanner myObj2 = new Scanner(System.in);
                        System.out.println("What is your name?");
                        String newMemberName = myObj2.nextLine();
                        System.out.println("Would you like a premium membership for $25? (yes or no)");
                        String response = myObj2.nextLine().toLowerCase();
                        if (response.equals("no")) {
                            members.add(new member(newMemberName));
                            System.out.println("\nYour acount has been created. Try logging in.\n");
                            newMembers += 1;
                        } else if (response.equals("yes")) {
                            members.add(new premiumMember(newMemberName, true, 0));
                            cart.add(new cartItem("Membership Fee", 1, 25));
                            boolean cardCheck;
                            do {
                                Scanner myObj3 = new Scanner(System.in);
                                System.out.println("What is you card number?");
                                long card = myObj.nextLong();
                                cardCheck = luhnsAlgorithm(card);
                                if (cardCheck) {
                                    ((premiumMember) members.get(members.size() - 1)).setCardNum(card);
                                } else {
                                    System.out.println("Invalid card Number.(if you're afraid to enter a real number enter in a fake one like: 4024007103939509)");
                                }
                            } while (!cardCheck);
                            System.out.println("\nYour acount has been created. Try logging in.\n");
                            newMembers += 1;
                        } else {
                            System.out.println("Invalid Input. Registration cancelled.");
                        }
                        repeat = true;
                        break;
                    case 3:                     //purchase as guest
                        while (chooseItem()) {
                        }
                        presentCart(3);
                        repeat = false;
                        break;
                    case 4:
                        repeat = false;
                        presentCart(3);
                        break;
                }
            } while (repeat);
        }catch (InputMismatchException e){
            System.out.println("Wrong input type");
        }
        
        //End of day Report
        try {
            FileWriter writer = new FileWriter("EndOfDayReport.txt");
            writer.write("Purchases: \n");
            for (int i = 0; i < cart.size(); i++) {
                writer.write("- " + cart.get(i).name + " x " + String.valueOf(cart.get(i).stock) + "  = $" + cart.get(i).cost * cart.get(i).stock + "\n");
            }
            
            writer.write("New members registered: " + String.valueOf(newMembers));
            
            writer.write("\nTotal sales/Revenue after discounts: $" + String.valueOf(TOTAL));
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        //generating day 2 inventory 
        try{
            FileWriter day2Writer = new FileWriter("ProductInventory-2.csv");
            day2Writer.write("productID,type,title,numInStock,price\n");
            int id =1;
            for (int i = 0; i < books.size(); i++){
                day2Writer.write(String.valueOf(id) + ",book,");
                day2Writer.write(books.get(i).name +","+ String.valueOf(books.get(i).stock) +","+ String.valueOf(books.get(i).cost) + "\n");
                id++;
            }
            for (int i = 0; i < cds.size(); i++){
                day2Writer.write(String.valueOf(id) + ",cd,");
                day2Writer.write(cds.get(i).name +","+ String.valueOf(cds.get(i).stock) +","+ String.valueOf(cds.get(i).cost) + "\n");
                id++;
            }
            for (int i = 0; i < dvds.size(); i++){
                day2Writer.write(String.valueOf(id) + ",dvd,");
                day2Writer.write(dvds.get(i).name +","+ String.valueOf(dvds.get(i).stock) +","+ String.valueOf(dvds.get(i).cost) + "\n");
                id++;
            }
            
            day2Writer.close();
                    
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //completes a purchase for logged in customers
    public static void loggedInPurchase(int type) {
        try {
            Scanner myObj1 = new Scanner(System.in);
            System.out.println("Would you now like to make a purchase(1), view your profile(2), or checkout(3)?");
            String answer = myObj1.nextLine();
            if (answer.toLowerCase().equals("1")) {
                while (chooseItem()) {
                }
                presentCart(type);
            }
            if (answer.toLowerCase().equals("3")) {
                presentCart(type);
            }
            if (answer.toLowerCase().equals("2")) {
                (members.get(memberIndex)).viewProfile();
                loggedInPurchase(type);
            }
            
        }catch (Exception e){
            System.out.println("An error occurred.");
        }
    }

    //logs in a customer
    public static int login() {
        try{
            int input;
            do {
                Scanner myObj = new Scanner(System.in);
                System.out.println("What type of member are you?");
                System.out.println("1. Regular");
                System.out.println("2. Premium");
                System.out.println("3. Return");
                input = myObj.nextInt();
                if (input == 1) {
                    Scanner myObj1 = new Scanner(System.in);
                    System.out.println("What is your name");
                    name = myObj1.nextLine();
                    for (int i = 0; i < members.size(); i++) {
                        if (name.toLowerCase().equals(members.get(i).name.toLowerCase())) {
                            if (members.get(i).premium == false) {
                                memberIndex = i;
                                return 1;
                            }
                        }
                    }
                    System.out.println("Invalid Name. \nReturning to home.");
                    return 3;
                } else if (input == 2) {
                    Scanner myObj1 = new Scanner(System.in);
                    System.out.println("What is your name");
                    name = myObj1.nextLine();
                    for (int i = 0; i < members.size(); i++) {
                        if (name.toLowerCase().equals(members.get(i).name.toLowerCase())) {
                            if (members.get(i) instanceof premiumMember) {
                                memberIndex = i;
                                System.out.println("Hello " + members.get(i).name);
                                return 2;
                            }

                        }
                    }
                    System.out.println("Invalid Name. \nReturning to home.");
                    return 3;
                } else if (input == 3) {
                    return 3;
                } else {
                    System.out.println("Invalid input. Try again.");
                }
            } while (input < 1 || input > 3);
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type");
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        return 3;
    }

    //lets a customer choose which type of item they are purchasing
    public static boolean chooseItem() {
        try{
        int input;
        do{
            Scanner myObj = new Scanner(System.in);
            System.out.println("What type of item would you like to purchase now?");
            System.out.println("1. Book");
            System.out.println("2. CD");
            System.out.println("3. DVD");
            System.out.println("4. I am done with my purchasing.");

            input = myObj.nextInt();
            switch (input) {
                case 1:
                    System.out.println("Here are the book's we have in stock:");
                    makePurchase(books);
                    break;
                case 2:
                    System.out.println("Here are the CD's we have in stock:");
                    makePurchase(cds);
                    break;
                case 3:
                    System.out.println("Here are the DVD's we have in stock:");
                    makePurchase(dvds);
                    break;
                case 4:
                    return false;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }while(input<1||input>4);
        }catch (InputMismatchException e){
            System.out.println("Wrong input type");
        }
        
        return true;
    }

    //helps customer make a purchase
    public static void makePurchase(ArrayList<inventoryItem> item) {
        try{
        for (int i = 0; i < item.size(); i++) {
            System.out.print("- " + item.get(i).name);
            System.out.print(" x ");
            System.out.println(item.get(i).stock);
        }
        Scanner myObj1 = new Scanner(System.in);
        int a = -1;
        do {
            System.out.println("Enter the name of the item you would like to purchase: ");
            String input1 = myObj1.nextLine();
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).name.toLowerCase().equals(input1.toLowerCase())) {
                    a = i;
                }
            }
            if (a == -1) {
                System.out.println("Invalid input. Get better at typing and try again.\n");
            }
        } while (a == -1);

        int amount;
        int record = item.get(a).stock;
        do {
            System.out.println("How many would you like to purchase?");
            amount = myObj1.nextInt();
            if (amount <= item.get(a).stock && amount >= 0) {
                cart.add(new cartItem(item.get(a).name, amount, item.get(a).cost));
                item.get(a).stock = item.get(a).stock - amount;
            } else {
                System.out.println("Invalid amount..try again\n");
                System.out.println(amount);
            }
        } while (amount > record || amount < 0);
        }catch (Exception e){
            System.out.println("An error occurred.");
        }

    }


    //presents the cart and proceeds to checkout for customer
    public static void presentCart(int type) {
        System.out.println("Here are the items in your cart:");
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            System.out.println("- " + cart.get(i).name + " x " + String.valueOf(cart.get(i).stock) + "  = $" + cart.get(i).cost * cart.get(i).stock);
            total += cart.get(i).cost * cart.get(i).stock;
        }
        if (total != 0) {
            System.out.println("SubTotal: $" + String.valueOf(total));
            if (type == 1) {
                System.out.print("Money saved from 10% membership discount: $");
                System.out.println(total * .1);
                total *= .9;
                System.out.print("Total: $");
                System.out.println(total);
                TOTAL += total;
                cardCheck();
            } else if (type == 2) {
                System.out.print("Money saved from 20% premium membership discount: ");
                System.out.println(total * .2);
                total *= .8;
                System.out.print("Total: $");
                System.out.println(total);
                TOTAL += total;
                System.out.println("##Transaction complete##");
                System.out.println("Thankyou for shopping at Naveen's BookStore!");
            } else {
                System.out.print("Total: $");
                System.out.println(total);
                TOTAL += total;
                cardCheck();
            }


        }
        else{
            System.out.println("Thankyou for visiting my Bookstore! I hope you have a great day!");
        }
        System.out.println();
        System.out.println("       .--.                   .---.");
        System.out.println("   .---|__|           .-.     |~~~|");
        System.out.println(".--|===|--|_          |_|     |~~~|--.");
        System.out.println("|  |===|  |'\\     .---!~|  .--|   |--|");
        System.out.println("|%%|   |  |.'\\    |===| |--|%%|   |  |");
        System.out.println("|%%|   |  |\\.'\\   |   | |__|  |   |  |");
        System.out.println("|  |   |  | \\  \\  |===| |==|  |   |  |");
        System.out.println("|  |   |__|  \\.'\\ |   |_|__|  |~~~|__|");
        System.out.println("|  |===|--|   \\.'\\|===|~|--|%%|~~~|--|");
        System.out.println("^--^---'--^    `-'`---^-^--^--^---'--'");
    }
    
    //checks if credit card number is real
    static boolean luhnsAlgorithm(long number)  
        {
            int intLength = String.valueOf(number).length();   //length of number
            long numLength = (long)intLength;
            long[] numberList = new long[intLength];
            for(int i = 0; i < numberList.length; i++)
            {
                numberList[i] = number%10;
                number/=10;
            }
            long[] numberList1 = new long[intLength];
            long j = numLength-1;
            for(int i = 0; i < numberList.length; i++)
            {
                numberList1[i] = numberList[(int)j];
                j-=1;
            }

            int sum = 0;
            for(int i = 2; i<=numberList1.length; i+=2)
            {
                long tmpNum = numberList1[numberList1.length - i] * 2;
                if (tmpNum > 9)
                {
                    sum += tmpNum%10;
                    sum += tmpNum/10;
                }
                else
                {
                    sum += tmpNum;
                }
            }
            for(int i = 1; i<=numberList1.length; i+=2)
            {
                sum += numberList1[numberList1.length - i];
            }

            if ((sum % 10) == 0)
            {
                return true;
            }
            return false;
        }
    //checks card
    static void cardCheck(){
        System.out.println();
        Scanner myObj = new Scanner(System.in);
        boolean cardCheck;
        do {
            System.out.println("Please enter your credit card number: ");
            long cardNum = myObj.nextLong();
            cardCheck = luhnsAlgorithm(cardNum);
            if (cardCheck) {
                System.out.println("##Transaction complete##");
                System.out.println("Thankyou for shopping at Naveen's BookStore!");
            } else {
                System.out.println("Invalid card number. (if you're afraid to enter a real number enter in a fake one like: 4024007103939509)");
            }
        } while (!cardCheck);
    }

}

