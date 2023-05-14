/**
 * The main game class to run the 'Air Hockey' game.
 */
public class AirHockey {

private static int gameSpeed = 1; // game speed - higher number means slower game speed
private static int p1Score = 0; // player 1 score
private static int p2Score = 0; // player 2 score

/**
 * Calculates resultant trajectory/position of a Ball(or extended) object after 'collision'.
 * @param xPosition1
 * @param yPosition1
 * @param xSpeed1
 * @param ySpeed1
 * @param xPosition2
 * @param yPosition2
 * @param xSpeed2
 * @param ySpeed2
 */
public static void deflect(double xPosition1, double yPosition1, double xSpeed1, double ySpeed1, double xPosition2, double yPosition2, double xSpeed2, double ySpeed2) {// method added and modified, originally pre-written for me 
    // all necessary variables re passed in to this function.

    // Calculate initial momentum of the balls... We assume unit mass here.
    double p1InitialMomentum = Math.sqrt(xSpeed1 * xSpeed1 + ySpeed1 * ySpeed1);
    double p2InitialMomentum = Math.sqrt(xSpeed2 * xSpeed2 + ySpeed2 * ySpeed2);
    // calculate motion vectors
    double[] p1Trajectory = {xSpeed1, ySpeed1};
    double[] p2Trajectory = {xSpeed2, ySpeed2};
    // Calculate Impact Vector
    double[] impactVector = {xPosition2 - xPosition1, yPosition2 - yPosition1};
    double[] impactVectorNorm = normalizeVector(impactVector);
    // Calculate scalar product of each trajectory and impact vector
    double p1dotImpact = Math.abs(p1Trajectory[0] * impactVectorNorm[0] + p1Trajectory[1] * impactVectorNorm[1]);
    double p2dotImpact = Math.abs(p2Trajectory[0] * impactVectorNorm[0] + p2Trajectory[1] * impactVectorNorm[1]);
    // Calculate the deflection vectors - the amount of energy transferred from one ball to the other in each axis
    double[] p1Deflect = { -impactVectorNorm[0] * p2dotImpact, -impactVectorNorm[1] * p2dotImpact };
    double[] p2Deflect = { impactVectorNorm[0] * p1dotImpact, impactVectorNorm[1] * p1dotImpact };
    // Calculate the final trajectories
    double[] p1FinalTrajectory = {p1Trajectory[0] + p1Deflect[0] - p2Deflect[0], p1Trajectory[1] + p1Deflect[1] - p2Deflect[1]};
    double[] p2FinalTrajectory = {p2Trajectory[0] + p2Deflect[0] - p1Deflect[0], p2Trajectory[1] + p2Deflect[1] - p1Deflect[1]};
    // Calculate the final energy in the system.
    double p1FinalMomentum = Math.sqrt(p1FinalTrajectory[0] * p1FinalTrajectory[0] + p1FinalTrajectory[1] * p1FinalTrajectory[1]);
    double p2FinalMomentum = Math.sqrt(p2FinalTrajectory[0] * p2FinalTrajectory[0] + p2FinalTrajectory[1] * p2FinalTrajectory[1]);

    // Scale the resultant trajectories if we've accidentally broken the laws of physics.
    double mag = (p1InitialMomentum + p2InitialMomentum) / (p1FinalMomentum + p2FinalMomentum);
    // Calculate the final x and y speed settings for the two balls after collision.
    // *NOTE* we only need the x/y speed for the puck, not the mallet.
    xSpeed1 = p1FinalTrajectory[0] * mag;
    ySpeed1 = p1FinalTrajectory[1] * mag;
    xSpeed2 = p2FinalTrajectory[0] * mag;
    ySpeed2 = p2FinalTrajectory[1] * mag;
}

/**
 * Converts a vector into a unit vector.
 * Used by the deflect() method to calculate the resultant direction after a collision.
 * @param vec
 * @return
 */
private static double[] normalizeVector(double[] vec) {
    double mag = 0.0;
    int dimensions = vec.length;
    double[] result = new double[dimensions];
    for (int i=0; i < dimensions; i++) {
        mag += vec[i] * vec[i];
        mag = Math.sqrt(mag);
    }
    if (mag == 0.0) {
        result[0] = 1.0;
        for (int i=1; i < dimensions; i++) {
            result[i] = 0.0;
        }
    } else {
        for (int i=0; i < dimensions; i++) {
            result[i] = vec[i] / mag;
        }
    }
    return result;
}

public static void main(String[] args) {
        // Game window (Game Arena named 'table')
        GameArena table = new GameArena(1100, 600, true);

        // Air Hockey Table
        Rectangle background = new Rectangle(0, 0, 1100, 600, "grey", 0);
        Rectangle tableBorder = new Rectangle(100, 100, 900, 400, "black", 0);
        Rectangle tableSurface = new Rectangle(125, 125, 850, 350, "white", 0);
        Rectangle goal1 = new Rectangle(125, 212, 10, 175, "yellow", 0);
        Rectangle goal2 = new Rectangle(965, 212, 10, 175, "yellow", 0);
        Line centreLine = new Line(550, 125, 550, 475, 1, "black", 0);
        Ball centreRingOutline = new Ball(550, 300, 70, "black", 0);
        Ball centreRing = new Ball(550, 300, 68, "white", 0);

        // 2 Players and Hockey puck
        Puck puck = new Puck(550, 300, 20, "black", 1);
        Mallet player1 = new Mallet(250, 300, 50, "blue", 1);
        Mallet player2 = new Mallet(850, 300, 50, "blue", 1);
        
        // Text Elements within the game
        Text title = new Text("Air Hockey Game", 25, 25, 45, "black");
        Text player1Score = new Text("0", 40, 25, 300, "black");
        Text player2Score = new Text("0", 40, 1050, 300, "black");
 
        // Adding all above elements into the 'Game Arena' named 'table'
        table.addRectangle(background);
        table.addRectangle(tableBorder);
        table.addRectangle(tableSurface);
        table.addRectangle(goal1);
        table.addRectangle(goal2);
        table.addLine(centreLine);
        table.addBall(centreRingOutline);
        table.addBall(centreRing);
        table.addBall(player1);
        table.addBall(player2);
        table.addBall(puck);
        table.addText(title);
        table.addText(player1Score);
        table.addText(player2Score);
    

        // Movement for Player 1 & 2
        Runnable movementEngine = new Runnable() {
            public void run() {
                while(true) {
                    if (table.letterPressed('w')) {
                        player1.move(0,-1);
                    }
                    if (table.letterPressed('s')) {
                        player1.move(0,1);
                    }
                    if (table.letterPressed('a')) {
                        player1.move(-1,0);
                    }
                    if (table.letterPressed('d')) {
                        player1.move(1,0);
                    }
                    if (table.upPressed()) {
                        player2.move(0,-1);
                    }
                    if (table.downPressed()) {
                        player2.move(0,1);
                    }
                    if (table.leftPressed()) {
                        player2.move(-1,0);
                    }
                    if (table.rightPressed()) {
                        player2.move(1,0);
                    }
                    // System.out.println(player1.getXSpeed() + " xSpeed for Player1");
                    // System.out.println(player1.getYSpeed() + " ySpeed for Player1");
                    // System.out.println(player2.getXSpeed() + " xSpeed for Player2");
                    // System.out.println(player2.getYSpeed() + " ySpeed for Player2");
                    try {
                        Thread.sleep(gameSpeed);
                    } catch (Exception e) {}
                }
            }
        };
        Thread movementEngineThread = new Thread(movementEngine);
        movementEngineThread.start();

        // Speed engine for calculating multiplied speed over time with the Player mallets.
        Runnable speedEngine = new Runnable() {
            public void run() {
                while (true) {
                    while(table.letterPressed('a') || table.letterPressed('d')) {
                        player1.setXSpeed(player1.getXSpeed() * 1.00000000001);
                    }
                    while(table.letterPressed('w') || table.letterPressed('s')) {
                        player1.setYSpeed(player1.getYSpeed() * 1.00000000001);
                    }
                    while(table.leftPressed()|| table.rightPressed()) {
                        player2.setXSpeed(player2.getXSpeed() * 1.00000000001);
                    }
                    while(table.upPressed() || table.downPressed()) {
                        player2.setYSpeed(player2.getYSpeed() * 1.00000000001);
                    }
                    try {
                        Thread.sleep(gameSpeed);
                    } catch (Exception e) {}
                }
            }
        };
        Thread speedEngineThread = new Thread(speedEngine);
        speedEngineThread.start();

        // Boundary engine for preventing the players from leaving their halves of the table or the table itself.
        Runnable playerBoundaryEngine = new Runnable() {
            public void run() {
                while (true) {
                    // left side of boundary
                    if (player1.getXPosition() <= (tableSurface.getXPosition() + (player1.getSize() /2))) {
                        player1.setXPosition(player1.getXPosition() + 1);
                    }
                    // top side of boundary
                    if (player1.getYPosition() <= (tableSurface.getYPosition() + (player1.getSize() /2))) {
                        player1.setYPosition(player1.getYPosition() + 1);
                    }
                    // right side of boundary
                    if (player1.getXPosition() >= (tableSurface.getXPosition() + (tableSurface.getWidth() /2) - (player1.getSize()/2))) {
                        player1.setXPosition(player1.getXPosition() - 1);
                    }
                    // bottom side of boundary
                    if (player1.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight()) - (player1.getSize()/2)) {
                        player1.setYPosition(player1.getYPosition() - 1);
                    }

                    if (player2.getXPosition() <= (tableSurface.getXPosition() + tableSurface.getWidth() /2) + (player2.getSize()/2)) {
                        player2.setXPosition(player2.getXPosition() + 1);
                    }
                    if (player2.getYPosition() <= (tableSurface.getYPosition() + (player2.getSize()/2) -1) ) {
                        player2.setYPosition(player2.getYPosition() + 1);
                    }
                    if (player2.getXPosition() >= (tableSurface.getXPosition() + tableSurface.getWidth()) - (player2.getSize()/2)) {
                        player2.setXPosition(player2.getXPosition() - 1);
                    }
                    if (player2.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight()) - (player2.getSize()/2)) {
                        player2.setYPosition(player2.getYPosition() - 1);
                    }

                    try {
                        Thread.sleep(gameSpeed);
                    } catch (Exception e) {}
                }
            }
        };
        Thread playerBoundaryThread = new Thread(playerBoundaryEngine);
        playerBoundaryThread.start();

        // Score engine for incrementing the respective player's score when the puck lands in their opponent's goal. //// need to add command to change the text objects accordingly.
        Runnable puckScoreEngine = new Runnable() {
            public void run() {
                if (puck.getXPosition() >= goal1.getXPosition() && puck.getXPosition() <= (goal1.getXPosition() + goal1.getWidth())) {
                    if (puck.getYPosition() >= goal1.getYPosition() && puck.getYPosition() <= (goal1.getYPosition() + goal1.getHeight())) {
                        p2Score++;
                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();
                    }
                }

                if (puck.getXPosition() >= goal2.getXPosition() && puck.getXPosition() <= (goal2.getXPosition() + goal2.getWidth())) {
                    if (puck.getYPosition() >= goal2.getYPosition() && puck.getYPosition() <= (goal2.getYPosition() + goal2.getHeight())) {
                        p1Score++;
                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();
                    }
                }

                try {
                    Thread.sleep(gameSpeed);
                } catch (Exception e) {}
            }
        };
        Thread puckScoreThread = new Thread(puckScoreEngine);
        puckScoreThread.start();

        // while(true) {
        //     if (player1.collides(puck)) {
        //         puck.move(player1.getXSpeed(), player1.getYSpeed()); //maybe
        //     }
        //     if (player2.collides(puck)) {
        //         puck.move(-(player2.getYSpeed()), -(player2.getYSpeed()));
        //     }
        //     try {
        //         Thread.sleep(gameSpeed);
        //     } catch(Exception e) {
        //         System.out.println("Exception.");
        //     }
        // }
        
        puck.setXSpeed(1);
        puck.setYSpeed(1);

        while(true) {
            puck.move(puck.getXSpeed(), puck.getYSpeed());

            try {
                Thread.sleep(gameSpeed);
            } catch(Exception e) {
                System.out.println("Exception.");
            }
        }




    }
}