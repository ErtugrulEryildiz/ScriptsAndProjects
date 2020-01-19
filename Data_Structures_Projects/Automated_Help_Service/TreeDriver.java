/**
 * @author Ertugrul Eryildiz
 * SBU ID: 112495660
 */

package Automated_Help_Service;
import java.io.*;
import java.util.Scanner;
public class TreeDriver {
    /**
     * Main method where automated help service program interacts with user and based on provided
     * information, helps user to solve their problem.
     *
     */
    public static void main(String[] args) {
        String choice;
        Scanner keyboard = new Scanner(System.in);
        Tree decisionMakingTree = new Tree();
        do {
            System.out.print("L - Load a Tree\n" +
                             "H - Begin a Help Session.\n" +
                             "T - Traverse the Tree in preorder.\n" +
                             "Q - Quit.\n" +
                             "Choice: ");
            choice = keyboard.nextLine().toLowerCase();
            switch (choice) {
                case "l":
                    System.out.println("Enter the name of the file");
                    try {
                        File newFile = new File(keyboard.nextLine());
                        if(newFile.exists())
                            decisionMakingTree.beginSession(newFile);
                        else
                            System.out.println("File does not exist.");
                    } catch(IOException ioe) {
                        System.out.println("File cannot be created.");
                    }
                    break;
                case "h":
                    // If Tree constructed with proper structure, proceed.
                    if(decisionMakingTree.getRoot() != null) {
                        TreeNode cursor = decisionMakingTree.getRoot();
                        // In this loop, program communicates with user and finds possible solutions
                        // to their problems by traversing various paths. After loop executes, prints
                        // what solution is to user's problem.
                        do {
                            System.out.println(cursor.getMessage());
                            if(cursor.getLeftChild() != null) {
                                System.out.println("1) " + cursor.getLeftChild().getPrompt());
                            }
                            if(cursor.getMiddleChild() != null) {
                                System.out.println("2) " + cursor.getMiddleChild().getPrompt());
                            }
                            if(cursor.getRightChild() != null) {
                                System.out.println("3) " + cursor.getRightChild().getPrompt());
                            }
                            System.out.println("0) Exit Session.\n Choice: ");
                            choice = keyboard.nextLine();
                            // If valid entry, move cursor to proper position.
                            if(choice.equals("1") && cursor.getLeftChild() != null) {
                                cursor = cursor.getLeftChild();
                            }
                            else if (choice.equals("2") && cursor.getMiddleChild() != null) {
                                cursor = cursor.getMiddleChild();
                            }
                            else if (choice.equals("3") && cursor.getRightChild() != null) {
                                cursor = cursor.getRightChild();
                            }
                            else if(!choice.equals("0"))
                                System.out.println("Invalid entry.");

                        } while(!cursor.isLeaf() && !choice.equals("0"));

                        if(cursor.isLeaf()) {
                            System.out.println(cursor.getMessage() +
                                    "\nThank you for using this automated help service.");
                        }

                    }
                    // Tree hasn't been constructed yet.
                    else {
                        System.out.println("Error! Tree hasn't constructed yet.");
                    }
                    break;
                case "t":
                    if(decisionMakingTree.getRoot() != null) {
                        System.out.println("Traversing the tree in pre order.");
                        decisionMakingTree.getRoot().preOrder();
                    }
                    else {
                        System.out.println("Error! Tree hasn't constructed yet.");
                    }
                    break;
                case "q":
                    System.out.println("See you later.");
                    break;
                default:
                    System.out.println("Illegal input. Try again.");
            }

        } while (!choice.equals("q"));

    }
}
