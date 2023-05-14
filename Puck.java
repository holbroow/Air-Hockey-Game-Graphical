public class Puck extends Ball{

    private double friction;
    private double speedMultiplier;
    private double xSpeed;
    private double ySpeed;
    private double originalXPosition;
    private double originalYPosition;
    private double originalFriction;

    public Puck(double x, double y, double diameter, String col) {
        super(x, y, diameter, col);

		this.xSpeed = 0;
		this.ySpeed = 0;
        this.friction = 0.8;
        this.speedMultiplier = 1;
		this.originalXPosition = x;
		this.originalYPosition = y;
        this.originalFriction = friction;
	}	
    
    public Puck(double x, double y, double diameter, String col, int layer) {
		super(x, y, diameter, col, layer);
		
		this.xSpeed = 0;
		this.ySpeed = 0;
        this.friction = 0.8;
        this.speedMultiplier = 1;
        this.originalXPosition = x;
		this.originalYPosition = y;
        this.originalFriction = friction;
	}

    public void resetPosition() {
        this.setXPosition(originalXPosition);
        this.setYPosition(originalYPosition);
        this.setFriction(originalFriction);
        this.setSpeedMultiplier(1);
        this.setXSpeed(0);
        this.setYSpeed(0);
    }


    // accessors
    public double getFriction() {
        return this.friction;
    }

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

    // mutators
    public void setFriction(double value) {
        this.friction = value;
    }

    public void setXSpeed(double value) {
        this.xSpeed = value;
    }

    public void setYSpeed(double value) {
        this.ySpeed = value;
    }

    public void setSpeedMultiplier(double value) {
        this.speedMultiplier = value;
    }

}
