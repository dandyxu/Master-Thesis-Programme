package com.March5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVRecognize {

	public static void main(String[] args) throws FileNotFoundException {
		// Get scanner instance
		Scanner scanner = new Scanner(new File("Zuzel.csv"));

		// Set Delimiter used in file
		scanner.useDelimiter(",");

		// print all the data of file
		while (scanner.hasNext()) {
			System.out.println(scanner.next());
		}

		// close the scanner
		scanner.close();

		// Get another scanner instance to input
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the metric name to start: ");
		int i = sc.nextInt();

		selectMetric(i);
	}

	// popup a GUI table to show metrics thresholds
	public static void popup() {
		System.out.println("Hello");

	}
	
	//select metric
	public static void selectMetric(int i) {
		//print all cbo values
		if (i == 1) {
			int[] a = new int[30];
			for (int j = 0; j < 30; j++) {
				int cbo = 0;
				//popup();
			}
		}else if (i == 2){
			System.out.println("...");
		}

	}

}
