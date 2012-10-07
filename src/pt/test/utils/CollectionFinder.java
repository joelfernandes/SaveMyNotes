package pt.test.utils;

import java.util.Collection;

public class CollectionFinder {
	public static final int INVALID_INDEX = -1;
	
	public static int findObjectPosition(Object obj, Collection<?> collection) {
		if(collection == null || obj == null) {
			throw new NullPointerException("Neither object to find nor collection may be null");
		}
		
		int i = 0;
		for (Object object : collection) {
			if(object.equals(obj)) {
				return i;
			}
			i++;
		}
		
		return INVALID_INDEX;
	}
	
}
