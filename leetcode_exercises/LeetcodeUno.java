public class LeetcodeUno {
//Leetcode 1800
    public static int maxAscendingSum(int[] nums) {    

        int sumaMaxima = 0;
        int sumaActual = nums[0];

        // recorremos el array por todo su tamaño
        for (int i = 1; i < nums.length; i++) {
            // si el array en su indice actual es mayor que el indice anterior
            if (nums[i] > nums[i-1]){
                // sumamos a la suma el indice actual
                sumaActual += nums[i];
                // si no
            }else{ 
                // colocamos en suma maxima el numero mas grande y en la suma actual
                //  el indice actual
            sumaMaxima = Math.max(sumaActual, sumaActual);
            sumaActual = nums[i];
        }
        }
        // devolvemos el mayor entre la suma maxima y la suma actual
        return  Math.max(sumaMaxima, sumaActual);
    }


    public static void main(String[] args) {
        // int[] nums = {10, 20 ,30 ,5 ,500};
        int[] nums = {1, 2 ,3 ,1 ,4, 3};
        System.out.println(maxAscendingSum(nums));
    }
}

// Input: nums = [12,17,15,13,10,11,12]
// Output: 33
// Explanation: [10,11,12] is the ascending subarray with the maximum sum of 33.