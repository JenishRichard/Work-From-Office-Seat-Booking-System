package model;
public class Seat {
	private int seatId;
    private String seatType;
    private boolean available;

public Seat(int seatId, String seatType, boolean available) {
	this.seatId = seatId;
	this.seatType = seatType;
	this.available = available;
}

public int getSeatId(){ return seatId; }
public String getSeatType(){ return seatType; }
public boolean isAvailable(){ return available; }

public void setAvailable(boolean available) {
    this.available = available;
}

@Override
public String toString() {
	 
	return "Seat[ID=" + seatId + ", Type=" + seatType + ", Available=" + available + "]";
	}

}

