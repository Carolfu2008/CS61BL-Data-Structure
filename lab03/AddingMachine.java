import java.util.*;

public class AddingMachine {

	public static void main (String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean isPreviousZero = false;
		int total = 0;
		int subtotal = 0;
		int input;
		int last=1;
		int MAXIMUM_NUMBER_OF_INPUTS = 100;
		int rank=0;
		int tmp=0;
    // TODO Add code here and elsewhere to complete AddingMachine
		int list[]=new int[MAXIMUM_NUMBER_OF_INPUTS];
		while (true) {
			input = scanner.nextInt();
			list[rank]=input;
			if (input == 0) {
				if (isPreviousZero) {
					System.out.println("total " + total);
					return;
				} else {
					System.out.println("subtotal " + subtotal);
					total += subtotal;
					subtotal = 0;
					isPreviousZero = true;
					last++;
				}
			}
			subtotal += input;
			if (input != 0) {
				isPreviousZero = false;
			}
		    rank++;
		}

	}


}
