package com.hindi.ssfreader;

import java.util.ArrayList;

public class Sentence {
	public int id;
	public String sentence;
	public ArrayList<Phrase> phrases;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public ArrayList<Phrase> getPhrases() {
		return phrases;
	}

	public void setPhrases(ArrayList<Phrase> phrases) {
		this.phrases = phrases;
	}

	@Override
	public String toString() {

		return "Sentence id: " + id + ",sentence= " + sentence + ", phrases= ["
				+ phrases + "]";
	}
}
