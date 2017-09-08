package comparetrees;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Performs insertions and searches, using the same data set,on a binary search
 * tree and an AVL tree to compare the empirically compare the performance of
 * these operations on the trees.
 * @author Samuel Maloney
 * @SEE AVLTree, AVLTreeException, BSTree, BSTreeException, WordStat
 * @since 3/14/17
 */
public class CompareTrees 
{
    public enum TraversalTypes{INORDER, LEVELORDER}
    public enum TreeTypes{BST, AVL}
    /**
     * @param args the command line arguments
     * @throws AVLTreeException
     * @throws BSTreeException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws AVLTreeException, BSTreeException, IOException
    {   
        BSTree<WordStat> tree = new BSTree<>();
        AVLTree<WordStat> avl = new AVLTree<>();
        Function<WordStat, PrintStream> printLine = x -> System.out.printf("%-30s %-10d%n", x.getWord(), x.getCount());
        try (Scanner inFile = new Scanner(new FileReader(args[0]))) {
            while(inFile.hasNext()){
                WordStat w = new WordStat(inFile.next().toUpperCase(), 1);
                if(avl.inTree(w)){
                    w = avl.retrieve(w);
                    w.updateCount(1);  
                }
                tree.insert(w);
                avl.insert(w);
        }
        
        int tableNum = 1;
        for(TraversalTypes trav: TraversalTypes.values()){
            switch(trav){
                case INORDER:
                    for(TreeTypes trees : TreeTypes.values()){
                        System.out.print("Table "+tableNum);
                        switch(trees){
                            case BST:
                                System.out.println(": Binary Search Tree [" + args[0] + "]");
                                System.out.println("In-order Traversal");
                                System.out.println("===================================");
                                System.out.printf("%s %18s%n", "Word", "Frequency");
                                System.out.println("-----------------------------------");
                                tree.traverse(printLine);
                                System.out.println("-----------------------------------");
                                System.out.println();
                                break;
                            case AVL:
                                System.out.println(": AVL Tree [" + args[0] + "]");
                                System.out.println("In-order Traversal");
                                System.out.println("===================================");
                                System.out.printf("%s %18s%n", "Word", "Frequency");
                                System.out.println("-----------------------------------");
                                avl.traverse(printLine);
                                System.out.println("-----------------------------------");
                                System.out.println();                                
                                break;
                        }
                        tableNum++;
                    }
                break;    
                case LEVELORDER:
                    for(TreeTypes trees : TreeTypes.values()){
                        System.out.print("Table "+tableNum);
                        switch(trees){
                            case BST:
                                System.out.println(": Binary Search Tree [" + args[0] + "]");
                                System.out.println("Level-order Traversal");
                                System.out.println("===================================");
                                System.out.printf("%s %18s%n", "Word", "Frequency");
                                System.out.println("-----------------------------------");
                                tree.levelTraverse(printLine);
                                System.out.println("-----------------------------------");  
                                System.out.println();
                                break;
                                
                            case AVL:
                                System.out.println(": AVL Tree [" + args[0] + "]");
                                System.out.println("Level-order Traversal");
                                System.out.println("===================================");
                                System.out.printf("%s %18s%n", "Word", "Frequency");
                                System.out.println("-----------------------------------");
                                avl.levelTraverse(printLine);
                                System.out.println("-----------------------------------");
                                System.out.println();                                
                                break;
                        }
                        tableNum++;
                    }
                }
        }    
        
        System.out.println("Table 5 : Number of Nodes vs Height");
        System.out.println("Using Data in [" + args[0] + "]");
        System.out.println("===================================");
        System.out.printf("Tree %14s %10s%n", "# Nodes", "Height");
        System.out.println("-----------------------------------");
        System.out.println("BST            "+tree.size()+"         "+tree.height());
        System.out.println("AVL            "+avl.size()+"         "+avl.height());
        System.out.println("-----------------------------------");
        System.out.println();
        
        System.out.println("Table 6 : Total Number of Nodes Accessed");
        System.out.println("Searching for all the Words in [" + args[0] + "]");
        System.out.println("==========================================");
        System.out.printf("Tree %21s%n", "# Nodes");
        System.out.println("------------------------------------------");
        int bstCnt=0;
        int avlCnt=0;
        try (Scanner sr = new Scanner(new FileReader(args[0]))) {
            while(sr.hasNext()){
                WordStat w = new WordStat(sr.next().toUpperCase(), 1);
                bstCnt=bstCnt+tree.depth(w)+1;
                avlCnt=avlCnt+avl.depth(w)+1;
            }
        }
        System.out.println("BST                 "+bstCnt);
        System.out.println("AVL                 "+avlCnt);
        System.out.println("------------------------------------------");
        System.out.println();
        
        }    
    }
}