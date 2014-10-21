import java.io.*;

public class rand {

	private static void swap(int[] array, int size) {
		int i, j;
		i = (int)(Math.random() * (size + 1)) % size;
		j = (int)(Math.random() * (size + 1)) % size;

		while(i == j) {
			j = (int)(Math.random() * (size + 1)) % size;
		}

		int temp;
		temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		i = 0;
		j = 0;
	}

	private static int[] genInts(int count, int start, int step, int swaps) {
		// Create an array of the requested size
		int[] array = new int[count];
		
		// Populate the array
		int i, j = start;
		for(i = 0; i < count; i++) {
			array[i] = j;
			j += step;
		}

		// Perform swaps
		int k = 0;
		while(k < swaps) {
			swap(array, count);
			k++;
		}
		return array;
	}

	public static void main (int count, int start, int step, int swaps) throws IOException {

		// Generate array
		int[] intArray = genInts(count, start, step, swaps);
		
		// Write the array to file
		BufferedWriter br = new BufferedWriter(new FileWriter("randInts.txt"));
		for(int j = 0; j < count; j++) {
			br.write(String.valueOf(intArray[j]) + '\n');
		}
		br.close();
		System.out.println("\n[Generated " + count + " items]");
	}
}
