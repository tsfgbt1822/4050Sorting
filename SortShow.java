/**
 *
 * @author Ouda
 */

//importing the libraries that will be needed in this program

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Random;

//The class that has all the sorts in it
public class SortShow extends JPanel {


	// An array to hold the lines_lengths to be sorted
	public int[] lines_lengths;
	//amount of lines needed
	public final int total_number_of_lines = 256;
	// An array to holds the scrambled lines_lengths
	public int[] scramble_lines;
	//temp Array that is used later for sorts
	public int[] tempArray;

	//the default constructor for the SortShow class
	public SortShow(){
		//assigning the size for the lines_lengths below
		lines_lengths = new int[total_number_of_lines];
		for(int i = 0; i < total_number_of_lines; i++)
			lines_lengths[i] =  i+5;

	}


	//A method that scrambles the lines
	public void scramble_the_lines(){
		//A random generator
		Random num = new Random();
		//Randomly switching the lines
		for(int i = 0; i < total_number_of_lines; i++){
			//getting a random number using the nextInt method (a number between 0 to i + 1)
			int j = num.nextInt(i + 1);
			//swapping The element at i and j
			swap(i, j);
		}
		//assigning the size for the scramble_lines below
		scramble_lines = new int[total_number_of_lines];
		//copying the now scrambled lines_lengths array into the scramble_lines array
		//to store for reuse for other sort methods
		//so that all sort methods will use the same scrambled lines for fair comparison
		for (int i = 0; i < total_number_of_lines; i++)
		{
			scramble_lines[i] = lines_lengths[i];
		}
		//Drawing the now scrambled lines_lengths
		paintComponent(this.getGraphics());
		delay(10);
	}

	//Swapping method that swaps two elements in the lines_lengths array
	public void swap(int i, int j){
		//storing the i element in lines_lengths in temp
		int temp = lines_lengths[i];
		//giving i element in lines_lengths the value of j element in lines_lengths
		lines_lengths[i] = lines_lengths[j];
		//giving j element in lines_lengths the value of temp
		lines_lengths[j] = temp;
	}

	//The selectionSort method
	//Iterative
	public void SelectionSort(){
		System.out.println("Reached selection sort");
		//getting the date and time when the selection sort starts
		Calendar start = Calendar.getInstance();
		//Using the selection sort to lines_lengths sort the array
		int size = total_number_of_lines;
		int imin; //to hold index of minimum value in array

		//sorts array
		//moves boundary of unsorted array after each selection/swap
		for(int i = 0; i < size-1; i++)
		{
			imin = getIndexOfSmallest(i, size - 1);
			swap(imin, i);
			paintComponent(this.getGraphics());
			delay(10);
			//System.out.println(lines_lengths[i]);
		}

		//getting the date and time when the selection sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the selection sort to execute
		//subtracting the end time with the start time
		SortGUI.selectionTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//this method gets the smallest element in the array of lines_lengths
	public int getIndexOfSmallest(int first, int last){

		//System.out.println("Get Index of smallest:" + first + "/" + last);
		//variable to hold index of minimum value in array
		int imin = first;

		//loop through all elements of array from start to last
		for(int i = first + 1; i <= last; i++)
		{
			//if value at index i is less than value currently held in position of imin
			// assign index to imin, is now holding the smallest value found so far
			if(lines_lengths[i] < lines_lengths[imin])
				imin=i;
		}

		//return index of smallest value
		return imin;
	}
	///////////////////////////////////////////////////////////////////////////////////

	//recursive merge sort method
	public void R_MergeSort() {
		// Getting the date and time when the recursive merge sort starts
		Calendar start = Calendar.getInstance();

		// Start the sorting
		R_MergeSort(0, total_number_of_lines - 1);

		Calendar end = Calendar.getInstance();
		// Calculating the time it took for the recursive merge sort to execute
		SortGUI.rmergeTime = end.getTime().getTime() - start.getTime().getTime();
	}

	// Recursive merge sort method with array bounds
	public void R_MergeSort(int first, int last) {
		if (first < last) {
			int mid = (first + last) / 2;
			R_MergeSort(first, mid); // Sort the first half
			R_MergeSort(mid + 1, last); // Sort the second half
			R_Merge(first, mid, last); // Merge the sorted halves

			delay(10);
		}
		paintComponent(this.getGraphics());
	}

	// merge the two sorted halves
	public void R_Merge(int first, int mid, int last) {
		// Temporary array to hold merged result
		int[] temp = new int[last - first + 1];

		int i = first, j = mid + 1, k = 0;

		// Merge the two halves into temp array
		while (i <= mid && j <= last) {
			if (lines_lengths[i] <= lines_lengths[j]) {
				temp[k++] = lines_lengths[i++];
			} else {
				temp[k++] = lines_lengths[j++];
			}
		}

		// Copy the remaining elements from the first half, if any
		while (i <= mid) {
			temp[k++] = lines_lengths[i++];
		}

		// Copy the remaining elements from the second half, if any
		while (j <= last) {
			temp[k++] = lines_lengths[j++];
		}

		// Copy the merged elements back into the original array
		for (i = first, k = 0; i <= last; i++, k++) {
			lines_lengths[i] = temp[k];
		}

	}

	//

	//////////////////////////////////////////////////////////////////////////////////////////

	//iterative merge sort method
	public void I_MergeSort()
	{
		//getting the date and time when the iterative merge sort starts
		Calendar start = Calendar.getInstance();
		//assigning the size for the tempArray below
		tempArray = new int[total_number_of_lines];
		//saving the value of total_number_of_lines
		int beginLeftovers = total_number_of_lines;


		for (int segmentLength = 1; segmentLength <= total_number_of_lines/2; segmentLength = 2*segmentLength)
		{
			beginLeftovers = I_MergeSegmentPairs(total_number_of_lines, segmentLength);
			int endSegment = beginLeftovers + segmentLength - 1;
			if (endSegment < total_number_of_lines - 1)
			{
				I_Merge(beginLeftovers, endSegment, total_number_of_lines - 1);
			}
		}

		// merge the sorted leftovers with the rest of the sorted array
		if (beginLeftovers < total_number_of_lines) {
			I_Merge(0, beginLeftovers-1, total_number_of_lines - 1);
		}
		//getting the date and time when the iterative merge sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the iterative merge sort to execute
		//subtracting the end time with the start time
		SortGUI.imergeTime = end.getTime().getTime() - start.getTime().getTime();
	}

	// Merges segments pairs (certain length) within an array
	public int I_MergeSegmentPairs(int l, int segmentLength)
	{
		//The length of the two merged segments

		//You suppose  to complete this part (Given).
		int mergedPairLength = 2 * segmentLength;
		int numberOfPairs = l / mergedPairLength;

		int beginSegment1 = 0;
		for (int count = 1; count <= numberOfPairs; count++)
		{
			int endSegment1 = beginSegment1 + segmentLength - 1;

			int beginSegment2 = endSegment1 + 1;
			int endSegment2 = beginSegment2 + segmentLength - 1;
			I_Merge(beginSegment1, endSegment1, endSegment2);

			beginSegment1 = endSegment2 + 1;
			//redrawing the lines_lengths
			paintComponent(this.getGraphics());
			delay(10);
			//Causing a delay for 10ms
		}
		// Returns index of last merged pair
		return beginSegment1;
		//return 1;//modify this line
	}

	public void I_Merge(int first, int mid, int last)
	{
		//You suppose  to complete this part (Given).
		// Two adjacent sub-arrays
		int beginHalf1 = first;
		int endHalf1 = mid;
		int beginHalf2 = mid + 1;
		int endHalf2 = last;

		// While both sub-arrays are not empty, copy the
		// smaller item into the temporary array
		int index = beginHalf1; // Next available location in tempArray
		for (; (beginHalf1 <= endHalf1) && (beginHalf2 <= endHalf2); index++)
		{
			// Invariant: tempArray[beginHalf1..index-1] is in order
			if (lines_lengths[beginHalf1] < lines_lengths[beginHalf2])
			{
				tempArray[index] = lines_lengths[beginHalf1];
				beginHalf1++;
			}
			else
			{
				tempArray[index] = lines_lengths[beginHalf2];
				beginHalf2++;
			}
		}
		//redrawing the lines_lengths
		paintComponent(this.getGraphics());

		// Finish off the nonempty sub-array

		// Finish off the first sub-array, if necessary
		for (; beginHalf1 <= endHalf1; beginHalf1++, index++)
			// Invariant: tempArray[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf1];

		// Finish off the second sub-array, if necessary
		for (; beginHalf2 <= endHalf2; beginHalf2++, index++)
			// Invariant: tempa[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf2];

		// Copy the result back into the original array
		for (index = first; index <= last; index++)
			lines_lengths[index] = tempArray[index];
	}

	//////////////////////////////////////////////////////////////////////

	//calls actual quick sort function (seperate for recursion/ time tracking)
	public void quickSort()
	{
		//getting date and timme of start of quick sort
		Calendar start = Calendar.getInstance();
		//calls quick function to quicksort
		quick(lines_lengths, 0, total_number_of_lines - 1);
		//gets date and time of end of quick sort
		Calendar end = Calendar.getInstance();
		//getting time it took to execute quick sort
		//subtracting end time with start time
		SortGUI.qsortTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//
	public void quick(int [] arr, int low, int high)
	{
		//updates lines
		paintComponent(this.getGraphics());
		delay(10);
		//if low is less than high, execute
		//else, they are equal and we have finished sorting
		if(low < high)
		{
			//call partition function to get pivot
			int pivot = partition(arr, low, high);

			//Recursive call to quick sort, one for left of pivot, one for right of pivot
			quick(arr, low, pivot-1);
			quick(arr, pivot + 1, high);
		}
	}

	//partitions
	public int partition(int [] arr, int low, int high)
	{
		//selects pivot element as last element
		int pivot = arr[high];
		//index of smallest element
		int l = low-1;

		//iterates through array
		for(int i = low; i < high; i++)
		{
			//if current element is smaller or equal to pivot,
			//increment smallest element indexx
			//swap smallest and i
			if(arr[i] <= pivot)
			{
				l++;
				swap(i,l);
			}
		}

		//swap pivot and l + 1
		swap(l+1, high);

		//return new pivot
		return l + 1;
	}


	//////////////////////////////////////////////////////////////////////

	public void bubbleSort() {
		//Get start time
		Calendar start = Calendar.getInstance();
		// track if a swap has occurred during a pass
		boolean swapped;

		// loop for each pass through the array
		for (int i = 0; i < total_number_of_lines - 1; i++) {
			// set swapped to false on each new pass
			swapped = false;

			// loop for comparing adjacent elements
			for (int j = 0; j < total_number_of_lines - 1 - i; j++) {
				// Compare adjacent elements and swap if out of order
				if (lines_lengths[j] > lines_lengths[j + 1]) {
					// Swap elements
					swap(j, j + 1);

					// Set swapped to true since a swap occurred
					swapped = true;
				}
			}

			// If no swaps occurred, array is sorted
			if (!swapped) {
				break;
			}

			paintComponent(this.getGraphics());

			delay(10);
		}
		//getting time it took to execute quick sort
		Calendar end = Calendar.getInstance();
		//subtracting end time with start time
		SortGUI.bubbleSortTime = end.getTime().getTime() - start.getTime().getTime();
	}


	//////////////////////////////////////////////////////////////////////

	public void insertionSort(){
		//Get start time
		Calendar start = Calendar.getInstance();
		//loop through array starting at second element
		for(int i = 1; i < total_number_of_lines; i++){
			//variables for
			int key = lines_lengths[i];
			int j = i - 1;

			while(j>=0 && lines_lengths[j] > key){
				lines_lengths[j+1] = lines_lengths[j];
				j -= 1;
			}
			lines_lengths[j+1] = key;

			// Optionally, update the UI to show the current state
			paintComponent(this.getGraphics());

			// Introduce a delay for visualization purposes
			delay(10);
		}
		//getting time it took to execute quick sort
		Calendar end = Calendar.getInstance();
		//subtracting end time with start time
		SortGUI.insertionSortTime = end.getTime().getTime() - start.getTime().getTime();
	}

	public void shellSort(){
		//Get start time
		Calendar start = Calendar.getInstance();
		int gap = total_number_of_lines/2;
		for(; gap > 0; gap /= 2){
			for(int i = gap; i < total_number_of_lines; i+=1){
				int hold = lines_lengths[i];
				int j;

				for(j = i; j>= gap && lines_lengths[j-gap] > hold; j -= gap) {
					lines_lengths[j] = lines_lengths[j-gap];
				}

				lines_lengths[j] = hold;

				// Optionally, update the UI to show the current state
				paintComponent(this.getGraphics());
			}
			// Introduce a delay for visualization purposes
			delay(10);
		}
		//getting time it took to execute quick sort
		Calendar end = Calendar.getInstance();
		//subtracting end time with start time
		SortGUI.shellSortTime = end.getTime().getTime() - start.getTime().getTime();
	}

	/////////////////////////////////////////////////////////////////////

	//This method resets the window to the scrambled lines display
	public void reset(){
		if(scramble_lines != null)
		{
			//copying the old scrambled lines into lines_lengths
			for (int i = 0; i < total_number_of_lines; i++)
			{
				lines_lengths[i] = scramble_lines[i] ;
			}
			//Drawing the now scrambled lines_lengths
			paintComponent(this.getGraphics());
		}
	}


	//This method colours the lines and prints the lines
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//A loop to assign a colour to each line
		for(int i = 0; i < total_number_of_lines; i++){
			//using eight colours for the lines
			if(i % 8 == 0){
				g.setColor(Color.green);
			} else if(i % 8 == 1){
				g.setColor(Color.blue);
			} else if(i % 8 == 2){
				g.setColor(Color.yellow);
			} else if(i%8 == 3){
				g.setColor(Color.red);
			} else if(i%8 == 4){
				g.setColor(Color.black);
			} else if(i%8 == 5){
				g.setColor(Color.orange);
			} else if(i%8 == 6){
				g.setColor(Color.magenta);
			} else
				g.setColor(Color.gray);

			//Drawing the lines using the x and y-components
			g.drawLine(4*i + 25, 300, 4*i + 25, 300 - lines_lengths[i]);
		}

	}

	//A delay method that pauses the execution for the milliseconds time given as a parameter
	public void delay(int time){
		try{
			Thread.sleep(time);
		}catch(InterruptedException ie){
			Thread.currentThread().interrupt();
		}
	}

}

