// February 28, 2017

// The QuestionTree class creates a game similar to twenty questions.
// If the user chooses to read in the questions/answers from a file,
// then the questions and answers will be added to the game. If not, then
// the user is asked if an object is his/her guess. If the object is not
// the what the user guessed, then the user is asked to add a new question
// and their object to the game. Once the user is done playing, all of the
// newly added questions and answers provided by the user will be read back
// into the text file to expand the number of questions and answers accessible
// to the game.

import java.util.*;
import java.io.*;

public class QuestionTree {
   private Scanner console;
   private QuestionNode superRoot;
   
   // Constructs a new game with one initial answer.
   public QuestionTree() {
      console = new Scanner(System.in);
      superRoot = new QuestionNode("computer");
   }
   
   public void read(Scanner input) {
      superRoot = readHelper(input, superRoot);
   }
   
   
   private QuestionNode readHelper(Scanner input, QuestionNode root) {
      String nodeType = input.nextLine();
      String text = input.nextLine();
      if (nodeType.equals("A:")) {
         return new QuestionNode(text);
      }
      root = new QuestionNode(text);
      root.left = readHelper(input, root.left);
      root.right = readHelper(input, root.right);
      return root;
   }
   

   public void write(PrintStream output) {
      writeHelper(output, superRoot);
   }
   

   private void writeHelper(PrintStream output, QuestionNode currentRoot) {
      if (currentRoot.left == null && currentRoot.right == null) {
         output.println("A:");
         output.println(currentRoot.data);
      } else {
         output.println("Q:");
         output.println(currentRoot.data);
         writeHelper(output, currentRoot.left);
         writeHelper(output, currentRoot.right);
      }
   }
   

   public void askQuestions() {
      superRoot = askQuestionsHelper(superRoot);
   }
   

   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.left == null && root.right == null) {
         System.out.print("Would your object happen to be ");
         boolean userObject = yesTo(root.data + "?");
         if (userObject) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String userAnswer = console.nextLine().toLowerCase();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String userQuestion = console.nextLine();
            boolean yesOrNoAnswer = yesTo("And what is the answer for your object?");
            root = addUserObject(root, userAnswer, userQuestion, yesOrNoAnswer);
         }
      } else {
         boolean userResponse = yesTo(root.data);
         if (userResponse) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      }
      return root;
   }
   

   private QuestionNode addUserObject(QuestionNode root, String userAnswer, String userQuestion,
         boolean yesOrNoAnswer) {
      if (yesOrNoAnswer) {
         return new QuestionNode(userQuestion, new QuestionNode(userAnswer), root);
      }
      return new QuestionNode(userQuestion, root, new QuestionNode(userAnswer));
   }
   

   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)?");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}
