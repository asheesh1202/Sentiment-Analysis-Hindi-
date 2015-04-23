import java.util.ArrayList;

class Sentence {
	int id;
	String sentence;
	ArrayList<Phrase> phrases;

	int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	String getSentence() {
		return sentence;
	}

	void setSentence(String sentence) {
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
