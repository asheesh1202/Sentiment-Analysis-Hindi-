import java.util.ArrayList;

class Phrase {

	String phraseTag;
	String phraseHead;
	String headRoot;
	ArrayList<Token> tokens;
	public String getHeadRoot() {
		return headRoot;
	}

	public void setHeadRoot(String headRoot) {
		this.headRoot = headRoot;
	}

	

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	String getPhraseTag() {
		return phraseTag;
	}

	void setPhraseTag(String tag) {
		this.phraseTag = tag;
	}

	String getPhraseHead() {
		return phraseHead;
	}

	void setPhraseHead(String head) {
		this.phraseHead = head;
	}

	@Override
	public String toString() {

		return "phrasetag= " + phraseTag +"headRoot= "+headRoot+ ",phrasehead= " + phraseHead
				+ ",tokens=[" + tokens + "]";
	}
}
