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
   
   // Pre: Takes in a Scanner, "input" that reads from a text files.
   //
   // Post: Organizes the file in a preorder pattern and further organizes the
   //       text depending on whether it text is marked as a question,
   //       (marked as "Q:"), or an answer, (marked as "A:"). All of the
   //       answers are organized to stem from questions.
   public void read(Scanner input) {
      superRoot = readHelper(input, superRoot);
   }
   
   // Pre: Takes in a Scanner, "input", that reads from a text file. Takes
   //      in a QuestionNode, "root", that is the starting point for all of
   //      the new  questions and answers.
   //
   // Post: Determines whether a line in a text file is a question or an
   //       answer by whether it corresponds to an indicator written in the
   //       text file (either a "Q:" or "A:" is written). Organizes the text
   //       in a preorder manner. Returns a QuestionNode, which is the newly
   //       added question/answer read in from the text file.
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
   
   // Pre: Takes in a PrinStream, "output", that is connected to write to a
   //       text file.
   //
   // Post: Writes all of the questions and answers, including the newly added
   //       question and answer given by the user, in a preorder pattern to the
   //       text file. Tags each question/answer with an indicator, either an
   //       "A:" or a "Q:".
   public void write(PrintStream output) {
      writeHelper(output, superRoot);
   }
   
   // Pre: Takes in a PrintStream, "output", that is connected to write to a
   //      text file. Takes in a QuestionNode, "currentRoot" that indicates
   //      the current question/answer being written into the text file.
   //
   // Post: Writes all of the questions and answers, including the newly added
   //       question and answer given by the user, in a preorder pattern, to
   //       the text file. Tags each question/answer with an indicator,
   //       either an "A:" or a "Q:".
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
   
   // Post: Prints out a question to the console which can be either answered
   //       by the user with a yes, by entering "y", or a no, by entering "n".
   //       Depending on the user's answer, it will ask another question, or
   //       if the series of questions has been exhausted, then it will print
   //       out an answer to the console. If the user's object is guessed
   //       correctly then game ends, otherwise, the user is asked to add
   //       a question and answer to the game.
   public void askQuestions() {
      superRoot = askQuestionsHelper(superRoot);
   }
   
   // Pre: Takes in a QuestionNode, "root", which represents the current
   //      question/answer that the user is currently being asked.
   //
   // Post: Prints out a question to the console which can be either answered
   //       by the user with a yes, by entering "y", or a no, by entering "n".
   //       Depending on the user's answer, it will ask another question, or
   //       if the series of questions has been exhausted, then it will print
   //       out an answer to the console. If the user's object is guessed
   //       correctly then game ends, and returns a QuestionNode which is the
   //       correctly guessed answer. Otherwise the user is asked to add
   //       a question and answer to the game. Returns a QuestionNode, which
   //       is the user's newly added question, the object that is the answer,
   //       and whether the object can be answered with a "yes" or "no" in
   //       relation to the question.
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
   
   // Pre: Takes in a QuestionNode, "root", which is the current answer that
   //      was an incorrect guess to the user's object. Takes in a String,
   //      "userAnswer" which is the correct object that the user was thinking
   //      about. Takes in a String, "userQuestion", which is a question that
   //      differentiates the incorrect object answer from the object the user
   //      was thinking about. Finally, takes in a boolean, "yesOrNoAnswer",
   //      which signifies whether the new object, "userAnswer" is a yes
   //      or no answer to the newly added question, "userQuestion".
   //
   // Post: Adds the new question, "userQuestion" and answer, "userAnswer"
   //       to all of the current questions and answers, while keeping the
   //       incorrect answer as the alternative answer to the newly added
   //       question. If "yesOrNoAnswer" is true, then the newly added object,
   //       "userAnswer" is the "yes" answer for the newly added question
   //        while the former answer is considered the "no" answer. If
   //       "yesOrNoAnswer" is false, then the added object, "userAnswer" is
   //       the "no" answer for the newly added question, "userQuestion" while
   //       the former answer is considered the "yes" answer. Returns the newly
   //       added question, along with the new  answer and old answer.
   private QuestionNode addUserObject(QuestionNode root, String userAnswer, String userQuestion,
         boolean yesOrNoAnswer) {
      if (yesOrNoAnswer) {
         return new QuestionNode(userQuestion, new QuestionNode(userAnswer), root);
      }
      return new QuestionNode(userQuestion, root, new QuestionNode(userAnswer));
   }
   
   // Pre: Takes in a String, "prompt" which is a question or answer
   //      that can be answered with a yes or a no.
   //
   // Post: Asks the user a question, forcing an answer of "y" or "n".
   //       Returns true if the answer was yes, returns false otherwise.
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
