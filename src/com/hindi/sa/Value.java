package com.hindi.sa;

import java.io.Serializable;

public class Value implements Serializable
{
	float posScore, negScore;
	
	void set(float s1, float s2)
	{
		posScore = s1;
		negScore = s2;
	}
	
	@Override
	public String toString() {
		return "pos: "+posScore+",neg:"+negScore;
	}
}