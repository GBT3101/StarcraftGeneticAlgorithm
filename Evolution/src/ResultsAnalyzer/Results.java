package ResultsAnalyzer;

public class Results {
	private int P1wins;
	private int P2wins;
	private int P3wins;
	private int P1score;
	private int P2score;
	private int P3score;
	private int P1time;
	private int P2time;
	private int P3time;
	
	public Results(){
		
	}

	public int getP1wins() {
		return P1wins;
	}

	public void setP1wins(int p1wins) {
		P1wins = p1wins;
	}

	public int getP2wins() {
		return P2wins;
	}

	public void setP2wins(int p2wins) {
		P2wins = p2wins;
	}

	public int getP3wins() {
		return P3wins;
	}

	public void setP3wins(int p3wins) {
		P3wins = p3wins;
	}

	public int getP1score() {
		return P1score;
	}

	public void setP1score(int p1score) {
		P1score = p1score;
	}

	public int getP2score() {
		return P2score;
	}

	public void setP2score(int p2score) {
		P2score = p2score;
	}

	public int getP3score() {
		return P3score;
	}

	public void setP3score(int p3score) {
		P3score = p3score;
	}

	public int getP1time() {
		return P1time;
	}

	public void setP1time(int p1time) {
		P1time = p1time;
	}

	public int getP2time() {
		return P2time;
	}

	public void setP2time(int p2time) {
		P2time = p2time;
	}

	public int getP3time() {
		return P3time;
	}

	public void setP3time(int p3time) {
		P3time = p3time;
	}
	
	public void addWinTo(String player){
		switch(player){
		case "P1":
			this.P1wins++;
			break;
		case "P2":
			this.P2wins++;
			break;
		case "P3":
			this.P3wins++;
			break;
		}
	}
	
	public void addScoreTo(int score, String player){
		switch(player){
		case "P1":
			this.P1score+=score;
			break;
		case "P2":
			this.P2score+=score;
			break;
		case "P3":
			this.P3score+=score;
			break;
		}
	}
	
	public void addTimeTo(int time, String player){
		switch(player){
		case "P1":
			this.P1time+=time;
			break;
		case "P2":
			this.P2time+=time;
			break;
		case "P3":
			this.P3time+=time;
			break;
		}
	}
	
	/*public String toString(){
		return "P1 wins: "+this.P1wins+" P2 wins: "+this.P2wins+ "\nP1 total Score: "+this.P1score
					+" P2 total Score: "+this.P2score;
	}*/
}
