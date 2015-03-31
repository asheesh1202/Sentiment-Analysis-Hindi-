package com.hindi.ssfreader;

import java.util.ArrayList;

class Phrase {

	public String phraseTag;
	public String phraseHead;
	public String headRoot;
	public ArrayList<Token> tokens;
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

	public String getPhraseTag() {
		return phraseTag;
	}

	public void setPhraseTag(String tag) {
		this.phraseTag = tag;
	}

	public String getPhraseHead() {
		return phraseHead;
	}

	public void setPhraseHead(String head) {
		this.phraseHead = head;
	}

	@Override
	public String toString() {

		return "phrasetag= " + phraseTag +"headRoot= "+headRoot+ ",phrasehead= " + phraseHead
				+ ",tokens=[" + tokens + "]";
	}
}
