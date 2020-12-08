package Mesermeunts;

import Tree.AVLTree;

import java.util.Arrays;
import java.util.Random;

public class splitMes {
    public static void main(String[] args){
        String SPACE ="        ";
        String SPACE2 ="                     ";
        int n=10000; //TODO n = 10000;



        System.out.println("i"+SPACE+"insert_rand"+SPACE+"insert_desc"+SPACE+"avl_rand"+SPACE+"avl_desc");
        System.out.println("i"+SPACE+"insert_rand"+SPACE+"insert_desc"+SPACE+"avl_rand"+SPACE+"avl_desc");
        for (int i=1;i<=10;i++){
            int[][] newArrays = CreateRandomAndDescArrays(n*i);

        }
    }


    public static int[][] CreateRandomAndDescArrays(int n) {
        Random rd = new Random(); // creating Random object
        int [][] results = new int[2][n];
        int[] arrRand = new int[n];
        int[] arrDesc = new int[n];
        for (int i = 0; i < n; i++) {
            arrRand[i] = rd.nextInt(); // storing random integers in an array
            arrDesc[i] = -1 * arrRand[i];
        }
        Arrays.sort(arrDesc);
        for (int i = 0; i < n; i++) {
            arrDesc[i] = -1 * arrDesc[i];


        }
        results[0]= arrDesc;
        results[1] = arrRand;
        return results;
    }

    public  static void loadTree(int[] values, AVLTree tree) {
        for (int val : values) {
            String info = Integer.toString(val);
            tree.insert(val, info);

        }
    }




}
