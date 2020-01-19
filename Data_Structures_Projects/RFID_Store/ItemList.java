/**
 * @author ErtugrulEryildiz
 * SBU ID: 112495660
 */
package RFID_Store;

public class ItemList {
    private ItemInfoNode head;
    private ItemInfoNode tail;
    private ItemInfoNode cursor;

    /**
     * Constructs ItemList object with all private instances set to null.
     */
    public ItemList () {
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }

    /**
     * Constructs new ItemInfoNode object with provided ItemInfo type variable called itemInfo.
     * This new ItemInfoNode type object can be inserted in various locations of item list
     * based on what is the current status of list and what does new ItemInfoNode elements
     * rfidTagNum is. Eventually list will be in increasing order of rfidTagNum.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * @param itemInfo
     *  ItemInfo type object, used to construct ItemInfoNode with provided information
     *  in itemInfo variable.
     *
     * Postconditions: New ItemInfoNode type element constructed based on itemInfo variable
     * and added to ItemList doubly linked list.
     *
     * Big-O Notation:
     *  Best case scenario: O(1).
     *   Item will be added to head of the list.
     *  Worst case scenario: O(n).
     *   Item will be added to somewhere in middle of list. We need to look through every
     *   element and check their rfidTagNumber. Specifically Case 2.3 is where this operation
     *   gets done. 'n' here represents amount of elements in our doubly liked list.
     */
    public void insertItem(ItemInfo itemInfo) {
        // Case 1: There is no element in list. Item will be head and tail.
        if(head == null) {
            head = new ItemInfoNode(itemInfo, head);
            tail = head; // set tail to new node as well.
        }
        // Case 2: There are elements in the list, we need to look through each element.
        else {
            // Case 2.1: new item going to be added before head of the list, as a new head.
            if (head.getItemInfo().getRfidTagNum().compareTo(itemInfo.getRfidTagNum()) >= 0) {
                head = new ItemInfoNode(itemInfo, head);
                head.getNextNode().setPrevNode(head);
            }
            // Case 2.2: new item going to be added after tail of the list, as a new tail.
            else if (tail.getItemInfo().getRfidTagNum().compareTo(itemInfo.getRfidTagNum()) <= 0) {
                tail = new ItemInfoNode(tail, itemInfo);
                tail.getPrevNode().setNextNode(tail);
            }
            // Case 2.3: new item going to be added in the middle of the list.
            else {
                boolean itemPlaced = false;
                this.cursor = head;
                while(!itemPlaced) {
                    if(cursor.getItemInfo().getRfidTagNum().compareTo(itemInfo.getRfidTagNum()) > 0) {
                        cursor = new ItemInfoNode(itemInfo, cursor);
                        cursor.setPrevNode(cursor.getNextNode().getPrevNode());
                        cursor.getPrevNode().setNextNode(cursor);
                        cursor.getNextNode().setPrevNode(cursor);
                        itemPlaced = true;
                    }
                    else
                        cursor = cursor.getNextNode();
                }
            }
        }
    }

    /**
     * Method to remove all ItemInfoNode type elements in ItemList object that has "out" statement
     * as currentLocation. This condition states that this specific element has been sold and needs
     * to be removed from ItemList object.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * Postconditions: ItemInfoNode objects that has "out" statement for currentLocation instance
     * has been removed from ItemList doubly linked list.
     *
     * Big-O Notation:
     *  Best case scenario: O(n).
     *  Worst case scenario: O(n).
     *   Since its not clear that where are the ItemInfoNodes with out statement are located at, we
     *   look through every element in our doubly linked list. 'n' here represents amount of
     *   elements in our doubly liked list.
     */
    public void removeAllPurchased() {
        for(this.cursor = head; cursor != null; cursor = cursor.getNextNode()) {
            if(cursor.getItemInfo().getCurrentLocation().equals("out")) {
                //Check if there is an element before cursor.
                if(cursor.getPrevNode() != null) {
                    //Check if there is an element after cursor.
                    if(cursor.getNextNode() != null) {
                        // Cursor is in the middle of the list. Remove it and connect other 2 nodes.
                        cursor.getNextNode().setPrevNode(cursor.getPrevNode());
                        cursor.getPrevNode().setNextNode(cursor.getNextNode());
                    } else {
                        // Cursor at the end of the list. Remove it and set tail to new position.
                        tail = cursor.getPrevNode();
                        cursor.setPrevNode(null);
                        tail.setNextNode(null);
                    }
                } else {
                    //Check if there is an element after cursor.
                    if (cursor.getNextNode() != null) {
                        // Cursor at the beginning of list. Remove it and set head to new position.
                        head = cursor.getNextNode();
                        head.setPrevNode(null);
                    } else {
                        // There is only 1 element in the list. Set head and tail to null.
                        this.head = null;
                        this.tail = null;
                    }
                }
            }
        }
    }

    /**
     * Method to move ItemInfoNode elements in ItemList doubly linked list.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * @param rfidTagNum
     *  String variable to find desired ItemInfoNode element in ItemList doubly linked list.
     * @param sourceLocation
     *  String variable to find desired ItemInfoNode element in ItemList doubly linked list.
     * @param destinationLocation
     *  String variable to place desired ItemInfoNode element in ItemList doubly linked list.
     *
     * @return
     *  Returns true if method could find and move the desired item to desired location. Returns
     *  false if method couldn't find the desired element in ItemList doubly linked list or couldn't
     *  place to desired location.
     *
     * @throws IllegalArgumentException
     *  If user wants to move and item that is already been sold or, wants to set current location of a
     *  element in ItemList to be sold by setting its current location "out", method throws
     *  IllegalArgumentException.
     *
     *  Big-O Notation:
     *   Best case scenario: O(1).
     *    Desired ItemInfoNode object was found in the head of ItemList doubly linked list.
     *   Worst case scenario: O(n)
     *    Desired item could never found in the ItemList doubly linked list. 'n' here represents amount of
     *   elements in our doubly liked list.
     */
    public boolean moveItem (String rfidTagNum, String sourceLocation, String destinationLocation)
            throws IllegalArgumentException {
        // source or destination locations cannot be "out".
        if (sourceLocation.equals("out"))
            throw new IllegalArgumentException("Item was sold. Cannot be moved.");
        if (destinationLocation.equals("out"))
            throw new IllegalArgumentException("Item cannot be moved to desired location.");

        boolean itemFound = false;
        cursor = head;
        while((!itemFound)&&(cursor!=null)) {
            if((cursor.getItemInfo().getRfidTagNum().equals(rfidTagNum)) &&
               (cursor.getItemInfo().getCurrentLocation().equals(sourceLocation))) {
                try {
                    cursor.getItemInfo().setCurrentLocation(destinationLocation);
                    itemFound = true;
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }
            }
            else
                cursor = cursor.getNextNode();
        }
        return itemFound;
    }

    /**
     * Method to print every ItemInfoNode type element in doubly linked list, ItemList object.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * Big-O Notation:
     *  Best scenario: O(n).
     *  Worst scenario: O(n).
     *   We need to look through every ItemInfoNode element in ItemList doubly linked list once
     *   in order to print every elements ItemInfo instances. Thus, both scenarios are O(n).
     *   'n' here represents amount of elements in our doubly liked list.
     */
    public void printAll() {
        System.out.println("Item Name      RFID      Original Location   Current Location    Price");
        System.out.println("---------      ----      -----------------   ----------------    -----");
        for (cursor = head; cursor != null; cursor = cursor.getNextNode()) {
            System.out.println(String.format(
                    "%-15s%-10s%-20s%-20s%.2f",
                    cursor.getItemInfo().getProductName(),
                    cursor.getItemInfo().getRfidTagNum(),
                    cursor.getItemInfo().getOriginalLocation(),
                    cursor.getItemInfo().getCurrentLocation(),
                    cursor.getItemInfo().getProductPrice()));
        }
    }

    /**
     * Method to print ItemInfoNode elements' ItemInfo instances that at the specific location.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * @param location
     *  String variable to determine which elements that we want to print.
     * @throws IllegalArgumentException
     *  location variable has to be in either shelf location format or cart location format.
     *  Otherwise method throws Illegal Argument Exception.
     *
     *  Big-O Notation:
     *   Best scenario: O(n).
     *   Worst scenario: O(n).
     *    Unless method throws Exception, then best scenario would be O(1), both scenarios are O(n).
     *    We need to look at every elements current location in order to decide whether print it or not.
     *    Thus, O(n). 'n' here represents amount of elements in our doubly liked list.
     */
    public void printByLocation(String location) throws IllegalArgumentException {
        // Testing if shelf location or cart number's length greater than 6.
        if(location.length()>6)
            throw new IllegalArgumentException("Invalid location.");
        // Testing whether shelf location starts with s or c.
        if (! ((location.charAt(0) == 's') || (location.charAt(0) == 'c')) )
            throw new IllegalArgumentException("Invalid location.");
        // Testing whether shelf location in correct format or not.
        for (int i=1; i<location.length(); i++) {
            if(!(location.charAt(i) >= '0' && location.charAt(i) <= '9')) {
                throw new IllegalArgumentException("Invalid Location.");
            }
        }

        System.out.println("Item Name      RFID      Original Location   Current Location    Price");
        System.out.println("---------      ----      -----------------   ----------------    -----");
        for (cursor = head; cursor != null; cursor = cursor.getNextNode()) {
            if(cursor.getItemInfo().getCurrentLocation().equals(location)) {
                System.out.println(String.format(
                        "%-15s%-10s%-20s%-20s%.2f",
                        cursor.getItemInfo().getProductName(),
                        cursor.getItemInfo().getRfidTagNum(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getProductPrice()));
            }
        }
    }

    /**
     * Method to relocate ItemInfoNode elements in ItemList object that are not in their current locations.
     * This method relocates only if item is in some other shelf that where it suppose to be. It does not
     * relocates items that are sold or currently in carts.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * Postconditions: ItemInfoNode elements in ItemList object are relocated.
     *
     * Big-O Notation:
     *  Best scenario: O(n)
     *  Worst scenario: O(n)
     *   Since we have to look at each item's current and original location and compare them, we look
     *   through every element in the ItemList doubly linked listed. Thus, both scenarios are O(n).
     *   'n' here represents amount of elements in our doubly liked list.
     */
    public void cleanStore() {
        System.out.println("The following item(s) have been moved back to their original locations:\n\n");
        System.out.println("Item Name      RFID      Original Location   Current Location    Price");
        System.out.println("---------      ----      -----------------   ----------------    -----");

        for(cursor = head; cursor!=null; cursor = cursor.getNextNode()) {
            // If item's current location moved only within shelves, print it then relocate it.
            if(!(cursor.getItemInfo().getCurrentLocation().equals(cursor.getItemInfo().getOriginalLocation())) &&
                (!cursor.getItemInfo().getCurrentLocation().equals("out")) &&
                (cursor.getItemInfo().getCurrentLocation().charAt(0) != 'c')) {

                System.out.println(String.format(
                        "%-15s%-10s%-20s%-20s%.2f",
                        cursor.getItemInfo().getProductName(),
                        cursor.getItemInfo().getRfidTagNum(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getProductPrice()));

                cursor.getItemInfo().setCurrentLocation(cursor.getItemInfo().getOriginalLocation());
            }
        }
    }

    /**
     * Method to check out items that are currently in specific cart.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * @param cartNum
     *  String variable to specify which cart is being checked out.
     *
     * @throws IllegalArgumentException
     *  If cartNum variable is not in correct cart number form, method throws Illegal
     *  Argument Exception.
     *
     *  Big-O Notation:
     *   Best Scenario: O(n)
     *   Worst Scenario: O(n)
     *    Unless method throws exception, then best scenario would be O(1), we need to look
     *    through every ItemInfoNode element in ItemList doubly linked list in order to
     *    find out which elements are in specific cart. Thus, both scenarios will be O(n).
     *    'n' here represents amount of elements in our doubly liked list.
     */
    public void checkOut(String cartNum) throws IllegalArgumentException {
        // Testing if the cart number's length greater than 4.
        if(cartNum.length()>4)
            throw new IllegalArgumentException("Invalid cart number.");
        // Testing whether shelf location starts with c.
        if (cartNum.charAt(0) != 'c')
            throw new IllegalArgumentException("Invalid cart number.");
        // Testing whether cart number in correct format or not.
        for (int i=1; i<cartNum.length(); i++) {
            if(!(cartNum.charAt(i) >= '0' && cartNum.charAt(i) <= '9')) {
                throw new IllegalArgumentException("Invalid cart number.");
            }
        }
        double total = 0;
        System.out.println("Item Name      RFID      Original Location   Current Location    Price");
        System.out.println("---------      ----      -----------------   ----------------    -----");
        for (cursor = head; cursor != null; cursor = cursor.getNextNode()) {
            if(cursor.getItemInfo().getCurrentLocation().equals(cartNum)) {
                System.out.println(String.format(
                        "%-15s%-10s%-20s%-20s%.2f",
                        cursor.getItemInfo().getProductName(),
                        cursor.getItemInfo().getRfidTagNum(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getProductPrice()));

                total += cursor.getItemInfo().getProductPrice();
                cursor.getItemInfo().setCurrentLocation("out");
            }
        }
        System.out.println("The total cost for all merchandise in cart " + cartNum + " was $" + total);
    }

    /**
     * Method to print ItemInfoNode elements' ItemInfo instances that has specific rfidTagNum.
     *
     * Preconditions: ItemList object must be initialized.
     *
     * @param rfidTagNum
     *  String variable to determine which elements are going to be printed.
     *
     *  @throws IllegalArgumentException
     *  rfidTagNum variable has to be in correct RFID Tag Number format. Otherwise method
     *  throws Illegal Argument Exception.
     *
     *  Big-O Notation:
     *   Best scenario: O(n).
     *   Worst scenario: O(n).
     *    Unless method throws Exception, thne best scenario would be O(1), both scenarios will be O(n).
     *    We need to look at every elements current location in order to decide whether print it or not.
     *    'n' here represents amount of elements in our doubly liked list.
     */
    public void printByRFID(String rfidTagNum) throws IllegalArgumentException {
        // Checking if RIFD number's length greater than 9.
        if(rfidTagNum.length()!=9)
            throw new IllegalArgumentException("RFID number's length too large.");
        // Testing whether number in hex format or not.
        for (int i=0; i<rfidTagNum.length(); i++) {
            if(!(rfidTagNum.charAt(i) >= '0' && rfidTagNum.charAt(i) <= '9') ||
                    (rfidTagNum.charAt(i) >= 'a' && rfidTagNum.charAt(i) <= 'f') ||
                    (rfidTagNum.charAt(i) >= 'A' && rfidTagNum.charAt(i) <= 'F')) {
                throw new IllegalArgumentException("RFID number is not in Hex format.");
            }
        }

        System.out.println("Item Name      RFID      Original Location   Current Location    Price");
        System.out.println("---------      ----      -----------------   ----------------    -----");
        for (cursor = head; cursor != null; cursor = cursor.getNextNode()) {
            if(cursor.getItemInfo().getRfidTagNum().equals(rfidTagNum)) {
                System.out.println(String.format(
                        "%-15s%-10s%-20s%-20s%.2f",
                        cursor.getItemInfo().getProductName(),
                        cursor.getItemInfo().getRfidTagNum(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getProductPrice()));
            }
        }
    }
}