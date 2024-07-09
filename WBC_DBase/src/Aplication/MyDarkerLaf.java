package Aplication;

import com.formdev.flatlaf.FlatDarkLaf;

public class MyDarkerLaf extends FlatDarkLaf {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static boolean setup() {
        return setup( new MyDarkerLaf() );
    }

    @Override
    public String getName() {
        return "MyDarkerLaf";
    }
}
