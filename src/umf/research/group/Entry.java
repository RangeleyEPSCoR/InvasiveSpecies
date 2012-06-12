package umf.research.group;

public class Entry {
	private String questionDescription;
	private Integer [] resID; 
	private String yesNext, noNext;
	private int gameState;
	
	public Entry (String s, Integer [] i, String yn, String nn, int stg) {
		resID = i;
		questionDescription = s;
		yesNext = yn;
		noNext = nn;
		gameState = stg;
	}
	
	public Integer [] getResIds() {
		return resID;
	}
	
	public String getQuestionDescription(){
		return questionDescription;
	}
	
	public String getYesNext(){
		return yesNext;
	}
	
	public String getNoNext(){
		return noNext;
	}
	
	public int getGameState() {
		return gameState;
	}
	
	public Entry getMe() {
		return this;
	}
	
}
