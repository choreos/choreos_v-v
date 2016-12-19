package eu.choreos.vv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {
	
	public static List<Number> listFor(Number... values) {
		List<Number> list = new ArrayList<Number>();
		Collections.addAll(list, values);
		return list;
	}

}
