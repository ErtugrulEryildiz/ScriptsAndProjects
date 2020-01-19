/**
 * @author ErtugrulEryildiz
 * SBU ID: 112495660
 */

package RFID_Store;
import java.util.Scanner;

public class DepartmentStore {
    /**
     * The main method runs a menu driven application which first creates
     * an ItemList object and then prompts the user for a menu command
     * selecting the operation along with menu options. Based on user's
     * selection, switch statement determines which part of the program
     * should execute next.
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String menuChoice;
        ItemList storeProductsList = new ItemList();

        System.out.println("Welcome!\n");
        do {
            System.out.println(
                    "\nI - Insert an item into the list \n" +
                    "M - Move an item in the store \n"      +
                    "L - List by location \n"               +
                    "P - Print all items in store \n"       +
                    "O - Checkout  \n"                      +
                    "C - Clean store \n"                    +
                    "R - Print by RFID tag number\n"        +
                    "U - Update inventory system   \n"      +
                    "Q - Exit the program.\n"               +
                    "Please select an option: ");
            menuChoice = keyboard.nextLine().toLowerCase();


            switch (menuChoice) {
                case "i":
                    ItemInfo newItem = new ItemInfo();
                    try {
                        System.out.println("Enter the name of product: ");
                        newItem.setProductName(keyboard.nextLine().toLowerCase());
                        System.out.println("Enter the RFID: ");
                        newItem.setRfidTagNum(keyboard.nextLine());
                        System.out.println("Enter the original location: ");
                        newItem.setOriginalLocation(keyboard.nextLine().toLowerCase());
                        newItem.setCurrentLocation(newItem.getOriginalLocation().toLowerCase());
                        System.out.println("Enter the price: ");
                        newItem.setProductPrice(keyboard.nextDouble());
                        keyboard.nextLine(); // white space eater.
                        storeProductsList.insertItem(newItem);
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                case "m":
                    System.out.println("Enter item RFID number: ");
                    String rfidNum = keyboard.nextLine();
                    System.out.println("Enter the current location: ");
                    String cLocation = keyboard.nextLine().toLowerCase();
                    System.out.println("Enter the new location: ");
                    String dLocation = keyboard.nextLine().toLowerCase();
                    try {
                        if(storeProductsList.moveItem(rfidNum, cLocation, dLocation))
                            System.out.println("Success.");
                        else
                            System.out.println("Item cannot be moved.");
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                case "l":
                    System.out.println("Enter the location: ");
                    try {
                        storeProductsList.printByLocation(keyboard.nextLine().toLowerCase());
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                case "p":
                    storeProductsList.printAll();
                    break;
                case "o":
                    System.out.println("Enter cart number: ");
                    try {
                        storeProductsList.checkOut(keyboard.nextLine().toLowerCase());
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                case "c":
                    storeProductsList.cleanStore();
                    break;
                case "r":
                    System.out.println("Enter RFID number: ");
                    try {
                        storeProductsList.printByRFID(keyboard.nextLine());
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                case "u":
                    storeProductsList.removeAllPurchased();
                    break;
            }
        } while (!menuChoice.equals("q"));
    }
}
