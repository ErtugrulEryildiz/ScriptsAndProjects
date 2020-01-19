/**
 * @author ErtugrulEryildiz
 * SBU ID: 112495660
 */
package RFID_Store;

public class ItemInfo {
    private String productName;
    private String rfidTagNum;
    private String originalLocation;
    private String currentLocation;
    private double productPrice;

    /**
     * Constructs ItemInfo object, initializes private instances to null.
     *
     * Postconditions:
     *  This ItemInfo object has been constructed with null instances.
     */
    public ItemInfo () {}

    /**
     * Setter method. Instantiates productName instances to productName variable.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @param productName
     * String variable to set product name instance.
     *
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Setter method. Instantiates rfidTagNum instances to rfidTagNum variable.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @param rfidTagNum
     *  String variable to set rfidTagNum instance.
     *
     * @throws IllegalArgumentException
     *  If rfidTagNum variable is not in a hexadecimal format, throws Illegal Argument
     *  Exception.
     */
    public void setRfidTagNum(String rfidTagNum) throws IllegalArgumentException {
        // Checking if RIFD number's length equals to 9.
        if(rfidTagNum.length()!=9)
            throw new IllegalArgumentException("RFID number's length has to be 9.");
        // Testing whether number in hex format or not.
        for (int i=0; i<rfidTagNum.length(); i++) {
            if(!((rfidTagNum.charAt(i) >= '0' && rfidTagNum.charAt(i) <= '9') ||
                 (rfidTagNum.charAt(i) >= 'a' && rfidTagNum.charAt(i) <= 'f') ||
                 (rfidTagNum.charAt(i) >= 'A' && rfidTagNum.charAt(i) <= 'F'))) {
                throw new IllegalArgumentException("RFID number is not in Hex format.");
            }
        }
        // All conditions are met to set rfid number.
        this.rfidTagNum = rfidTagNum;
    }

    /**
     * Setter method. Instantiates originalLocation instance to originalLocation variable.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @param originalLocation
     *  String variable to set originalLocation instance.
     *
     * @throws IllegalArgumentException
     *  If originalLocation variable is not in correct shelf location format, it throws
     *  Illegal Argument Exception.
     */
    public void setOriginalLocation(String originalLocation) throws IllegalArgumentException {
        // Testing if shelf location's length equals to 6.
        if(originalLocation.length()!=6)
            throw new IllegalArgumentException("Shelf location's length too large.");
        // Testing whether shelf location starts with s or not.
        if (originalLocation.charAt(0) != 's')
            throw new IllegalArgumentException("Shelf location is not in correct format(s_ _ _ _ _).");
        // Testing whether shelf location in correct format or not.
        for (int i=1; i<originalLocation.length(); i++) {
            if(!(originalLocation.charAt(i) >= '0' && originalLocation.charAt(i) <= '9')) {
                throw new IllegalArgumentException("Shelf location is not in correct format(s_ _ _ _ _).");
            }
        }
        // All conditions are met to set shelf location.
        this.originalLocation = originalLocation;
    }

    /**
     * Setter method. Instantiates currentLocation instance to currentLocation variable.
     * currentLocation instance can only be a shelf location, cart location or out, to
     * indicate item was sold.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @param currentLocation
     *  String variable to set currentLocation instance.
     *
     * @throws IllegalArgumentException
     *  currentLocation format only accepts shelf location, cart location or "out". Otherwise
     *  throws Illegal Argument Exception.
     */
    public void setCurrentLocation(String currentLocation) throws IllegalArgumentException {
        // Testing if shelf location or cart number's length greater than 6.
        if(currentLocation.length()>6)
            throw new IllegalArgumentException("Invalid location to move, length too large.");
        // Testing whether new location shelf, cart or out.
        if (! ((currentLocation.charAt(0) == 's') ||
               (currentLocation.charAt(0) == 'c') ||
               (currentLocation.equals("out"))))
            throw new IllegalArgumentException("Location is not in correct format to set.(s_ _ _ _ _ or c_ _ _)");
        // All conditions are met to set new location.
        this.currentLocation = currentLocation;
    }

    /**
     * Setter method. Instantiates productPrice instance to productPrice variable.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @param productPrice
     *  Double variable to set productPrice instance.
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Getter method for productName instance.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @return
     *  Returns productName instance.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Getter method for rfidTagNum instance.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @return
     *  Returns rfidTagNum instance.
     */
    public String getRfidTagNum() {
        return rfidTagNum;
    }

    /**
     * Getter method for originalLocation instance.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @return
     *  Returns originalLocation instance.
     */
    public String getOriginalLocation() {
        return originalLocation;
    }

    /**
     * Getter method for currentLocation instance.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @return
     *  Returns currentLocation instance.
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Getter method for productPrice instance.
     *
     * Preconditions: ItemInfo object must be instantiated.
     *
     * @return
     *  Returns productPrice instance.
     */
    public double getProductPrice() {
        return productPrice;
    }
}
