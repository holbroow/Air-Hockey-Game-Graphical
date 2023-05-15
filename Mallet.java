/**
 * This class extends the 'Ball' class to create a specialised class for a Hockey 'Mallet'.
 * @author Will Holbrook
 */
public class Mallet extends Ball {
    
    private double speedMultiplier;
    private double xSpeed;
    private double ySpeed;
    private double originalXPosition;
    private double originalYPosition;
    private int score;

    public Mallet(double x, double y, double diameter, String col) {
        super(x, y, diameter, col);

		this.xSpeed = 0;
		this.ySpeed = 0;
        this.speedMultiplier = 0.01;
		this.originalXPosition = x;
		this.originalYPosition = y;
        this.score = 0;
	}	
    
    public Mallet(double x, double y, double diameter, String col, int layer) {
		super(x, y, diameter, col, layer);
		
		this.xSpeed = 0;
		this.ySpeed = 0;
        this.speedMultiplier = 0.01;
        this.originalXPosition = x;
		this.originalYPosition = y;
        this.score = 0;
	}

    public void resetPosition() {
        this.setXPosition(originalXPosition);
        this.setYPosition(originalYPosition);
        this.setSpeedMultiplier(0.01);
        this.setXSpeed(0);
        this.setYSpeed(0);
    }

    // calculateSpeed() {
        
    // }





    // accessors
    public double getXSpeed() {
        return this.xSpeed;
    }

    public double getYSpeed() {
        return this.ySpeed;
    }

    public double getOriginalXPosition() {
        return this.originalXPosition;
    }

    public double getOriginalYPosition() {
        return this.originalYPosition;
    }

    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    public int getScore() {
        return this.score;
    }

    // mutators
    public void setXSpeed(double value) {
        this.xSpeed = value;
    }

    public void setYSpeed(double value) {
        this.ySpeed = value;
    }

    public void setSpeedMultiplier(double value) {
        this.speedMultiplier = value;
    }

    public void setScore(int value) {
        this.score = value;
    }
}
