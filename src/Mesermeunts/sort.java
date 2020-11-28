package Mesermeunts;

import Tree.FingerTree;

import java.util.Random;

public class sort {

    public int swap(int a, int b) {
        return 0;
    }


    public static long insertion_Sort(int[] arr) {

        int n = arr.length;
        long count = 0;
        int i, key, j;
        for (i = 1; i < n; i++) {
            key = arr[i];
            j = i - 1;

        /* Move elements of arr[0..i-1], that are
        greater than key, to one position ahead
        of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                count++;
            }
            arr[j + 1] = key;


        }
        return count;
    }
    public static int AVLSort(int[] values) {
//        System.out.println(Arrays.toString(values));
//         values =  createshuffedArrSize(200);
        int x=0;
        FingerTree tree = new FingerTree();
        for (int val : values) {
            String info = Integer.toString(val);
            x = tree.insert_FINGER(val, info);
//            System.out.println(x);

        }
//        tree.printTree("kh");
        return x;
    }




    public static void main(String[] args){
        String SPACE ="        ";
        String SPACE2 ="                     ";
        int n=10000; //TODO n = 10000;

        long[] results = new long[4];


        System.out.println("i"+SPACE+"insert_rand"+SPACE+"insert_desc"+SPACE+"avl_rand"+SPACE+"avl_desc");
        System.out.println("i"+SPACE+"insert_rand"+SPACE+"insert_desc"+SPACE+"avl_rand"+SPACE+"avl_desc");
        for (int i=1;i<=10;i++){
            int[] randArray =createRandArray(n*i);
            int[] descArray = createDescArray(n*i);
            results[2] = AVLSort(randArray);
            results[3] = AVLSort(descArray);

            results[0] = insertion_Sort(randArray);
            results[1] = insertion_Sort(descArray);
//


            System.out.println(i+SPACE+results[0]+SPACE2+results[1]+SPACE2+results[2]+SPACE2+results[3]);

            }
        }

    public static int[] createDescArray ( int n){
        int[] arr = new int[n];
        for (int i = 0; i<n; i++) {
            arr[i] = n-i;
        }
        return arr;
    }

    public static int[] createRandArray ( int n){
        int[] arr = createDescArray(n);
        shuffleArray(arr);
        return arr;

    }
    public static void shuffleArray ( int[] array){
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;


        }
    }
    public static int[] createshuffedArrSize(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        shuffleArray(arr);
        return arr;

    }
    }


