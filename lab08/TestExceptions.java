public class TestExceptions {

	public static void main (String [ ] args) {
		//________________ ;

		try {
			//got null pointer ;

		} catch (NullPointerException e) {
			System.out.println ("got null pointer");
		}
		try {
			//got illegal array store ;

		} catch (ArrayStoreException e) {
			System.out.println ("got illegal array store");
		}
		try {
			//got illegal class cast ;

		} catch (ClassCastException e) {
			System.out.println ("got illegal class cast");
		}
	}

}
