/**
 * This class extends the 'Ball' class to create a specialised class for a Hockey 'Puck'.
 * @author Will Holbrook
 */
public class Puck extends Ball{

    private double friction;
    private double speedMultiplier;
    private double xSpeed;
    private double ySpeed;
    private double originalXPosition;
    private double originalYPosition;
    private double originalFriction;
    private double originalSpeedMultiplier;
    private double originalXSpeed;
    private double originalYSpeed;

    public Puck(double x, double y, double diameter, String col) {
        super(x, y, diameter, col);

		this.xSpeed = 0;
		this.ySpeed = 0;
        this.friction = 0.99;
        this.speedMultiplier = 1;
		this.originalXPosition = x;
		this.originalYPosition = y;
        this.originalFriction = friction;
        this.originalSpeedMultiplier = speedMultiplier;
        this.originalXSpeed = xSpeed;
        this.originalYSpeed = ySpeed;
	}	
    
    public Puck(double x, double y, double diameter, String col, int layer) {
		super(x, y, diameter, col, layer);
		
		this.xSpeed = 0;
		this.ySpeed = 0;
        this.friction = 0.99;
        this.speedMultiplier = 1;
        this.originalXPosition = x;
		this.originalYPosition = y;
        this.originalFriction = friction;
        this.originalSpeedMultiplier = speedMultiplier;
        this.originalXSpeed = xSpeed;
        this.originalYSpeed = ySpeed;
	}

    public void resetPosition() {
        this.setXPosition(originalXPosition);
        this.setYPosition(originalYPosition);
        this.setFriction(originalFriction);
        this.setSpeedMultiplier(originalSpeedMultiplier);
        this.setXSpeed(originalXSpeed);
        this.setYSpeed(originalYSpeed);
    }


    // accessors
    public double getFriction() {
        return friction;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public double getOriginalXPosition() {
        return originalXPosition;
    }

    public double getOriginalYPosition() {
        return originalYPosition;
    }

    public double getOriginalFriction() {
        return originalFriction;
    }

    public double getOriginalSpeedMultiplier() {
        return originalSpeedMultiplier;
    }

    public double getOriginalXSpeed() {
        return originalXSpeed;
    }

    public double getOriginalYSpeed() {
        return originalYSpeed;
    }

    // mutators
    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setOriginalXPosition(double originalXPosition) {
        this.originalXPosition = originalXPosition;
    }

    public void setOriginalYPosition(double originalYPosition) {
        this.originalYPosition = originalYPosition;
    }

    public void setOriginalFriction(double originalFriction) {
        this.originalFriction = originalFriction;
    }

    public void setOriginalSpeedMultiplier(double originalSpeedMultiplier) {
        this.originalSpeedMultiplier = originalSpeedMultiplier;
    }

    public void setOriginalXSpeed(double originalXSpeed) {
        this.originalXSpeed = originalXSpeed;
    }

    public void setOriginalYSpeed(double originalYSpeed) {
        this.originalYSpeed = originalYSpeed;
    }

}
