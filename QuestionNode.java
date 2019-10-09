// February 28, 2017


public class QuestionNode {
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   

   public QuestionNode(String data) {
      this.data = data;
   }
   

   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}
