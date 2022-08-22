package shop.makaroni.bunjang.utils;

import java.text.DecimalFormat;

public class Formatter {
	public static String changeWon(Long price) {
		DecimalFormat formatter = new DecimalFormat("###,###원");
		return formatter.format(price);
	}
}
