// February 28, 2017

// The QuestionNode class stores a phrase (a question or answer) that will be
// used in the QuestionTree class.

public class QuestionNode {
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   
   // Pre: Takes in a String, "data", that will be either a phrase that is
   //      a question or an answer.
   //
   // Post: Creates a new QuestioNode that contains "data".
   public QuestionNode(String data) {
      this.data = data;
   }
   
   // Pre: Takes in a String, "data", a QuestionNode, "left", and a
   //      QuestionNode, "right".
   //
   // Post: Creates a new QuestioNode with the phrase "data", and connects to
   //       both the other QuestionNodes, "left" and "right".
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}
