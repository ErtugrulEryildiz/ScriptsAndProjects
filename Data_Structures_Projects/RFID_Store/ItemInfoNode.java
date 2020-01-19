/**
 * @author ErtugrulEryildiz
 * SBU ID: 112495660
 */
package RFID_Store;

public class ItemInfoNode {
    private ItemInfoNode prevNode;
    private ItemInfoNode nextNode;
    private ItemInfo itemInfo;

    /**
     * Constructs ItemInfoNode object, initializes private instances to null. This objects
     * can be used as doubly linked list, because it has ItemInfoNode instances.
     *
     * Postconditions; ItemInfoNode object has been constructed with null instances.
     */
    public ItemInfoNode () {}

    /**
     * Constructs ItemInfoNode object, initializes itemInfo instances to itemInfo
     * variable. Calls super constructor inside.
     *
     * Postconditions; ItemInfoNode object has been constructed with initialized itemInfo
     * instance and null for other instances.
     */
    public ItemInfoNode (ItemInfo itemInfo) {
        this();
        this.itemInfo = itemInfo;
    }

    /**
     * Constructs ItemInfoNode object. Initializes itemInfo instances to itemInfo
     * variable and nextNode instances to nextNode variable. Used to construct new
     * ItemInfoNode before nextNode variable. Calls super constructor with itemInfo
     * parameter passed to it.
     *
     * Postconditions; ItemInfoNode object has been constructed after nextNode variable
     * with itemInfo instance set to itemInfo variable.
     */
    public ItemInfoNode (ItemInfo itemInfo, ItemInfoNode nextNode) {
        this(itemInfo);
        this.nextNode = nextNode;
    }

    /**
     * Constructs ItemInfoNode object. Initializes itemInfo instances to itemInfo
     * variable and prevNode instances to prevNode variable. Used to construct new
     * ItemInfoNode after prevNode variable. Calls super constructor with itemInfo
     * parameter passed to it.
     *
     * Postconditions; ItemInfoNode object has been constructed before prevNode variable
     * with itemInfo instance set to itemInfo variable.
     */
    public ItemInfoNode(ItemInfoNode prevNode, ItemInfo itemInfo) {
        this(itemInfo);
        this.prevNode = prevNode;
    }

    /**
     * Setter method. Instantiates nextNode instance to nextNode variable.
     *
     * Preconditions: ItemInfoNode must be instantiated.
     *
     * @param nextNode
     *  ItemInfoNode type variable to initialize nextNode instance.
     */
    public void setNextNode(ItemInfoNode nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * Setter method. Instantiates prevNode instance to prevNode variable.
     *
     * Preconditions: ItemInfoNode must be instantiated.
     *
     * @param prevNode
     *  ItemInfNode type variable to initialize prevNoce instance.
     */
    public void setPrevNode(ItemInfoNode prevNode) {
        this.prevNode = prevNode;
    }

    /**
     * Getter method.
     *
     * Preconditions: ItemInfoNode must be instantiated.
     *
     * @return
     *  Returns itemInfo instance.
     */
    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    /**
     * Getter method.
     *
     * Preconditions: ItemInfoNode must be instantiated.
     *
     * @return
     *  Returns nextNode instance.
     */
    public ItemInfoNode getNextNode() {
        return nextNode;
    }

    /**
     * Getter method.
     *
     * Preconditions: ItemInfoNode must be instantiated.
     *
     * @return
     *  Returns prevNode instance.
     */
    public ItemInfoNode getPrevNode() {
        return prevNode;
    }
}
