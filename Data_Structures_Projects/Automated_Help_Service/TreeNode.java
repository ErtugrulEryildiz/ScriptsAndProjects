/**
 * @author Ertugrul Eryildiz
 * SBU ID: 1122495660
 */

package Automated_Help_Service;
public class TreeNode {

    private TreeNode leftChild;
    private TreeNode middleChild;
    private TreeNode rightChild;
    private String label;
    private String prompt;
    private String message;

    /**
     * Constructor that creates TreeNode objects with passed parameters. TreeNode object
     * is fully equipped tree data structure node. Each node can have up to 3 children.
     *
     * @param label
     *  Sets label of node.
     * @param prompt
     * Sets prompt of node.
     * @param message
     * Sets message of node.
     *
     * Postcondition:
     *  TreeNode object constructed with provided parameters.
     */
    public TreeNode(String label, String prompt, String message) {
        this.label = label;
        this.prompt = prompt;
        this.message = message;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  leftChild of this node.
     */
    public TreeNode getLeftChild() {
        return leftChild;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  middleChild of this node.
     */
    public TreeNode getMiddleChild() {
        return middleChild;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  rightChild of this node.
     */
    public TreeNode getRightChild() {
        return rightChild;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  label instance of this node.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  message instance of this node.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  prompt of this node.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  Total number of children.
     */
    public int getTotalNumOfChildren() {
        int count = 0;
        if (this.leftChild != null) count++;
        if (this.middleChild != null) count++;
        if (this.rightChild != null) count++;

        return count;
    }

    /**
     * Getter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  Every children of this node.
     */
    public TreeNode[] getChildren() {
        int count = 0;
        TreeNode[] children = new TreeNode[getTotalNumOfChildren()];
        if (this.leftChild != null) children[count++] = this.leftChild;
        if (this.middleChild != null) children[count++] = this.middleChild;
        if (this.rightChild != null) children[count] = this.rightChild;

        return children;
    }


    /**
     * Setter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @param leftChild
     *  TreeNode object that will become left child.
     *
     * Postcondition:
     *  leftChild instance of this TreeNode object set to leftChild parameter.
     */
    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Setter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @param middleChild
     *  TreeNode object that will become middle child.
     *
     * Postcondition:
     *  middleChild instance of this TreeNode object set to middleChild parameter.
     */
    public void setMiddleChild(TreeNode middleChild) {
        this.middleChild = middleChild;
    }

    /**
     * Setter method.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @param rightChild
     *  TreeNode object that will become right child.
     *
     * Postcondition:
     *  rightChild instance of this TreeNode object set to rightChild parameter.
     */
    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * This method prints whole tree data structure in pre ordered form. It prints
     * each node's label, prompt and, message.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     */
    public void preOrder() {
        System.out.println(toString());
        if (leftChild != null)
            leftChild.preOrder();
        if(middleChild != null)
            middleChild.preOrder();
        if(rightChild != null)
            rightChild.preOrder();
    }

    /**
     * This method checks whether this TreeNode object has child(s) or not.
     *
     * Precondition:
     *  TreeNode object must be initialized.
     *
     * @return
     *  boolean value.
     */
    public boolean isLeaf() {
        return (this.leftChild == null);
    }

    /**
     * This method prints this node's label, prompt and, message.
     *
     * @return
     *  label, prompt and, message instance values of this object.
     */
    public String toString() {
        return "\nLabel: " + this.label +
                "\nPrompt: " + this.prompt +
                "\nMessage: " + this.message;
    }
}
