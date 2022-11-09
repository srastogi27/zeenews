package com.zee.base;

import org.testng.asserts.SoftAssert;

public class Assertions extends SoftAssert{

	public boolean assertContains(String actual, String excepted) {
		return excepted.contains(actual);
	}
}
