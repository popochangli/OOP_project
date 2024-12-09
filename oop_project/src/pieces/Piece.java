package pieces;

public abstract class Piece {
	 protected int[] location = new int[2];
	    private final boolean team; // true = white, false = black
	    private boolean inPlay = true;

	    public Piece(boolean team, int[] location) {
	        this.team = team;
	        this.location = location;
	    }

	    public boolean getTeam() {
	        return team;
	    }

	    public int[] getLocation() {
	        return location;
	    }

	    public void setLocation(int x, int y) {
	        location[0] = x;
	        location[1] = y;
	    }

	    public boolean isInPlay() {
	        return inPlay;
	    }

	    public void remove() {
	        inPlay = false;
	    }

	    // Abstract method to validate moves for specific piece types
	    public abstract boolean moveValid(int[] target, Piece[][] board);
}
