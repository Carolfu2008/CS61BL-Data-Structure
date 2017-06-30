public class ModNCounter {

	private int myCount;
	private int mod;

	public ModNCounter() {
		myCount = 0;
	}

	public ModNCounter(int n){
		myCount = 0;
		mod =n;
	}

	public void increment() {
		myCount++;
	}

	public void reset() {
		myCount = 0;
	}

	public int value() {
		return myCount;
	}

	public int mod(){
		return mod;
	}
}
