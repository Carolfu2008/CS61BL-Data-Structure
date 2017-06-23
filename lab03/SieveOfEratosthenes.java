public class SieveOfEratosthenes {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You need to enter an argument!");
        }

        int upperBound = Integer.parseInt(args[0]);

        boolean[] isNotPrime = new boolean[upperBound];

        for (int i = 0; i <= upperBound; i++) {
            if (isNotPrime[i] == true) {
                continue;
            } else {
                for(int j=i;j<=upperBound;j=j+i)
                    isNotPrime[j]=true;
            }
        }

        for (int i = 2; i <= upperBound; i++) {
            if (!isNotPrime[i]) {
                System.out.println(i + " is a prime number.");
            }
        }
    }
}