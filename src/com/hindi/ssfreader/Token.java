package com.hindi.ssfreader;

class Token {
	public String word;
	public String tag;
	public String rootWord;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRootWord() {
		return rootWord;
	}

	public void setRootWord(String root) {
		this.rootWord = root;
	}

	@Override
	public String toString() {

		return "word= " + word + ",tag= " + tag + ",rootword=" + rootWord;
	}
}
