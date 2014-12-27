package org.mo.pmas.util;

import java.util.Comparator;

import org.mo.pmas.entity.Contact;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<Contact> {

	public int compare(Contact o1, Contact o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
