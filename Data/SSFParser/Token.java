class Token {
	String word;
	String tag;
	String rootWord;

	String getWord() {
		return word;
	}

	void setWord(String word) {
		this.word = word;
	}

	String getTag() {
		return tag;
	}

	void setTag(String tag) {
		this.tag = tag;
	}

	String getRootWord() {
		return rootWord;
	}

	void setRootWord(String root) {
		this.rootWord = root;
	}

	@Override
	public String toString() {

		return "word= " + word + ",tag= " + tag + ",rootword=" + rootWord;
	}
}
