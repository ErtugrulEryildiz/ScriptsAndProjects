/**
 * @author Ertugrul Eryildiz
 * SBU ID: 112495660
 */

package Automated_Help_Service;
import java.io.*;
public class Tree {

    private TreeNode root;
    private TreeNode cursor;

    /**
     * Constructor that creates Tree object by using TreeNode objects. TreeNode object
     * is fully equipped tree data structure node. TreeNode type Root and cursor objects
     * set to null.
     *
     * Postcondition:
     *  Tree object has been constructed.
     */
    public Tree() {
        this.root = null;
        this.cursor = null;
    }

    /**
     * Getter method.
     *
     * Precondition;
     *  Tree object must be initialized.
     *
     * @return
     * root instance of this object.
     */
    public TreeNode getRoot() {
        return this.root;
    }

    /**
     * Method where program builds tree structure by reading each node's
     * instances from file.
     *
     * Preconditions:
     *  Tree object must be initialized.
     *
     * @param fileName
     *  File object that program uses to build tree structure.
     *
     * @throws IOException
     *  If method fails to open fileName parameter, or fails to read from
     *  it, throws IOException.
     */
    public void beginSession(File fileName) throws IOException{

        String label,
                prompt,
                message,
                readLine;

        Reader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Loops iterates until there is no data to read.
        while (true) {
            readLine = bufferedReader.readLine();
            // If there is data to read, proceed.
            if(readLine != null) {
                // If read readLine is not blank, proceed.
                if (readLine.length() > 0) {
                    // If first node in the tree, it will be the root of the tree.
                    if (this.root == null) {
                        label = readLine;
                        prompt = bufferedReader.readLine();
                        message = bufferedReader.readLine();
                        addNode(null, label, prompt, message);
                    }
                    // We have root in tree. New node will be child of a node that is labelled as
                    // readLine[0], and readLine[1] indicates how many child(s) it has.
                    else {
                        String[] labelProperties = readLine.split(" ");
                        // Construct all of the children of this labelled node.
                        for (int i = 0; i < Integer.parseInt(labelProperties[1]); i++) {
                            label = bufferedReader.readLine();
                            prompt = bufferedReader.readLine();
                            message = bufferedReader.readLine();
                            addNode(labelProperties[0], label, prompt, message);
                        }
                    }
                }
            }
            // There is no data to read, break.
            else {
                break;
            }
        }
        bufferedReader.close();
        reader.close();

        System.out.println("Tree created successfully.");
    }

    /**
     * Method that creates a child node and connects it with specific parent node.
     * Child node set to be most left possible.
     *
     * Preconditions:
     *  Tree object must be initialized.
     *
     * @param parentRootLabel
     *  Parent node of constructed child node.
     * @param label
     *  label of constructed Tree Node object.
     * @param prompt
     *  prompt of constructed Tree Node object.
     * @param message
     *  message of constructed Tree Node object.
     *
     * @return
     *  Boolean value, indicating success of method.
     *
     * @throws IllegalArgumentException
     *  If parent root is full, method throws Illegal argument exception.
     *  If parent root cannot be found in the tree, method throws Illegal
     *   argument exception.
     */
    public boolean addNode(String parentRootLabel, String label, String prompt, String message)
    throws IllegalArgumentException {
        // If root does not exist, make new node to be root.
        if( this.root == null) {
            this.root = new TreeNode(label, prompt, message);
            return true;
        }
        // Else, set cursor to where this parent root located at.
        else {
            cursor = getReferenceNode(parentRootLabel, root);
            // If we successfully find where this parent root located at, proceed.
            if(cursor != null) {
                // Find available position for new node.
                if (cursor.getLeftChild() == null) {
                    cursor.setLeftChild(new TreeNode(label, prompt, message));
                    return true;
                } else if (cursor.getMiddleChild() == null) {
                    cursor.setMiddleChild(new TreeNode(label, prompt, message));
                    return true;
                } else if (cursor.getRightChild() == null) {
                    cursor.setRightChild(new TreeNode(label, prompt, message));
                    return true;
                } else
                    throw new IllegalArgumentException("Root is full. Cannot add.");
            }
            else {
                throw new IllegalArgumentException("Labelled Root is not in tree. Node cannot be created.");
            }
        }
    }

    /**
     * Method that finds specific node in tree structure.
     *
     * Preconditions:
     *  Tree object must be initialized.
     *
     * @param targetLabel
     *  Label instance of TreeNode to be found.
     *
     * @return
     *  Boolean value, indicating whether labelled node is found or not.
     */
    public TreeNode getReferenceNode(String targetLabel, TreeNode cursor) {
        TreeNode nodeToReturn = null;

        // Case 1: Check if targetLabel belongs to root of the tree.
        if(cursor.getLabel().equals(targetLabel))
            return cursor;

        // Case 2: If root has child(s), proceed and find which family has this node with specified targetLabel.
        if(!cursor.isLeaf()) {
            for (TreeNode child: cursor.getChildren()) {
                // If this child of parent root contains the cursor node's targetLabel, then we set cursor to this child node.
                if (targetLabel.contains(child.getLabel())) {
                   cursor = child;
                   nodeToReturn = getReferenceNode(targetLabel, cursor);
               }
            }
            return nodeToReturn;
        }

        // Root with this targetLabel does not exist.
        else { return null; }
    }
}
