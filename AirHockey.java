import java.io.File;

/**
 * This class provides an environment to set-up and run the 'Air Hockey' game.
 * @author Will Holbrook
 */
public class AirHockey {

private static boolean soundOn = false;
private static boolean doublePointsActive = false;

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
 * @return
 */
public static double[] deflect(double xPosition1, double yPosition1, double xSpeed1, double ySpeed1, double xPosition2, double yPosition2, double xSpeed2, double ySpeed2) {// method added and modified, originally pre-written for me 
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

    double[] deflectValues = new double[4];
    deflectValues[0] = xSpeed1;
    deflectValues[1] = ySpeed1;
    deflectValues[2] = xSpeed2;
    deflectValues[3] = ySpeed2;

    return deflectValues;
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

/**
 * Main function for declaring objects and running various functions/engines. The main application for the Air Hockey game.
 * @param args
 */
public static void main(String[] args) {
        // Game window (Game Arena named 'table')
    GameArena table = new GameArena(1100, 600, true);

    // Air Hockey Table
    Rectangle background = new Rectangle(0, 0, 1100, 600, "#000041" /* navy blue */, 0);
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
    Text title = new Text("Air Hockey Game", 40, 25, 45, "white", 0);
    Text player1ScoreText = new Text("0", 40, 25, 300, "white", 0);
    Text player2ScoreText = new Text("0", 40, 1050, 300, "white");
    Text player1Wins = new Text("Player 1 wins the round!", 25, 25, 45, "green", 0);
    Text player2Wins = new Text("Player 2 wins the round!", 25, 25, 45, "yellow", 0);
    Text player1WinsGame = new Text("Player 1 wins with 6 points! Press space to start a new game.", 20, 25, 45, "white", 0);
    Text player2WinsGame = new Text("Player 2 wins with 6 points! Press space to start a new game.", 20, 25, 45, "white", 0);
    

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
    table.addText(player1ScoreText);
    table.addText(player2ScoreText);

    File applause = new File("applause.wav");
    File bounce = new File("bounce.wav");
    File drumroll = new File("drumroll.wav");
    File fanfare = new File("fanfare.wav");
    File hit = new File("hit.wav");
    SoundPlayer soundPlayer = new SoundPlayer();


    if (soundOn == true) {
        soundPlayer.playAudio(fanfare);
    }

    /**
     * Movement for Player 1 & 2.
     */
    Runnable movementEngine = new Runnable() {
        public void run() {
            while(true) {
                // up
                if (table.letterPressed('w')) {
                    player1.move(0,-1);
                    // player1.setYSpeed(player1.getYSpeed() - player1.getSpeedMultiplier());
                }
                // down
                if (table.letterPressed('s')) {
                    player1.move(0,1);
                    // player1.setYSpeed(player1.getYSpeed() + player1.getSpeedMultiplier());
                }
                // left
                if (table.letterPressed('a')) {
                    player1.move(-1,0);
                    // player1.setXSpeed(player1.getXSpeed() - player1.getSpeedMultiplier());
                }
                // right
                if (table.letterPressed('d')) {
                    player1.move(1,0);
                    // player1.setXSpeed(player1.getXSpeed() + player1.getSpeedMultiplier());
                }
                // up
                if (table.upPressed()) {
                    player2.move(0,-1);
                    // player2.setYSpeed(player2.getYSpeed() - player2.getSpeedMultiplier());
                }
                // down
                if (table.downPressed()) {
                    player2.move(0,1);
                    // player2.setYSpeed(player2.getYSpeed() + player2.getSpeedMultiplier());
                }
                // left
                if (table.leftPressed()) {
                    player2.move(-1,0);
                    // player2.setXSpeed(player2.getXSpeed() - player2.getSpeedMultiplier());
                }
                // right
                if (table.rightPressed()) {
                    player2.move(1,0);
                    // player2.setXSpeed(player2.getXSpeed() + player2.getSpeedMultiplier());
                }

                // if (!table.letterPressed('w') && !table.letterPressed('s')) {
                //     player1.setYSpeed(0);
                // }
                // if (!table.letterPressed('a') && !table.letterPressed('d')) {
                //     player1.setYSpeed(0);
                // }
                // if (!table.upPressed() && !table.downPressed()) {
                //     player2.setYSpeed(0);
                // }
                // if (!table.leftPressed() && !table.rightPressed()) {
                //     player2.setXSpeed(0);
                // }
                // player1.move(player1.getXSpeed(), player1.getYSpeed());
                // player2.move(player2.getXSpeed(), player2.getYSpeed());

                // System.out.println(player1.getXSpeed() + " xSpeed for Player1"); //debug
                // System.out.println(player1.getYSpeed() + " ySpeed for Player1"); //debug
                // System.out.println(player2.getXSpeed() + " xSpeed for Player2"); //debug
                // System.out.println(player2.getYSpeed() + " ySpeed for Player2"); //debug

                try {
                    Thread.sleep(1);
                } catch (Exception e) {}
            }
        }
    };
    Thread movementEngineThread = new Thread(movementEngine);

    /**
     * Calculate and determine speed for PLayer Mallets for use with deflect().
     */
    Runnable speedEngine = new Runnable() {
        public void run() {
            while (true) {
                if (table.letterPressed('a') || table.letterPressed('d')) {
                    player1.setXSpeed(player1.getXSpeed() + player1.getSpeedMultiplier());
                } else {
                    player1.setXSpeed(0);
                }
            
                if (table.letterPressed('w') || table.letterPressed('s')) {
                    player1.setYSpeed(player1.getYSpeed() + player1.getSpeedMultiplier());
                } else {
                    player1.setYSpeed(0);
                }
            
                if (table.leftPressed() || table.rightPressed()) {
                    player2.setXSpeed(player2.getXSpeed() + player2.getSpeedMultiplier());
                } else {
                    player2.setXSpeed(0);
                }
            
                if (table.upPressed() || table.downPressed()) {
                    player2.setYSpeed(player2.getYSpeed() + player2.getSpeedMultiplier());
                } else {
                    player2.setYSpeed(0);
                }
            
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
        }
    };
    Thread speedEngineThread = new Thread(speedEngine);

    /**
     * Preventing the players from leaving their halves of the table or the table itself.
     */
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
                    Thread.sleep(1);
                } catch (Exception e) {}
            }
        }
    };
    Thread playerBoundaryThread = new Thread(playerBoundaryEngine);

    /**
     * Score engine for incrementing the respective player's score when the puck lands in their opponent's goal, and determining eventual winner.           //// issues ////
     */
    Runnable scoreEngine = new Runnable() {
        public void run() {
            while (true) {
                if (puck.getXPosition() >= goal1.getXPosition() && puck.getXPosition() <= (goal1.getXPosition() + goal1.getWidth())) {
                    if (puck.getYPosition() >= goal1.getYPosition() && puck.getYPosition() <= (goal1.getYPosition() + goal1.getHeight())) {
                        player2.setScore(player2.getScore() + (1 * player2.getScoreMultiplier()));
                        
                        player2ScoreText.setText(Integer.toString(player2.getScore()));
                        table.removeText(title);
                        table.addText(player2Wins);

                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();

                        table.removeBall(player1);
                        table.removeBall(player2);
                        table.removeBall(puck);

                        try {
                            Thread.sleep(2200);
                        } catch (Exception e) {}
                        table.removeText(player2Wins);
                        table.addText(title);

                        table.addBall(player1);
                        table.addBall(player2);
                        table.addBall(puck);
                    }
                }

                if (puck.getXPosition() >= goal2.getXPosition() && puck.getXPosition() <= (goal2.getXPosition() + goal2.getWidth())) {
                    if (puck.getYPosition() >= goal2.getYPosition() && puck.getYPosition() <= (goal2.getYPosition() + goal2.getHeight())) {
                        player1.setScore(player1.getScore() + (1 * player2.getScoreMultiplier()));

                        player1ScoreText.setText(Integer.toString(player1.getScore()));
                        table.removeText(title);
                        table.addText(player1Wins);
                        
                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();

                        table.removeBall(player1);
                        table.removeBall(player2);
                        table.removeBall(puck);

                        try {
                            Thread.sleep(2200);
                        } catch (Exception e) {}
                        table.removeText(player1Wins);
                        table.addText(title);

                        table.addBall(player1);
                        table.addBall(player2);
                        table.addBall(puck);
                    }
                }

                if (player1.getScore() == 6) {
                    table.removeText(title);
                    table.addText(player1WinsGame);

                    table.removeBall(player1);
                    table.removeBall(player2);
                    table.removeBall(puck);

                    if (table.spacePressed()) {
                        if (soundOn == true) {
                            soundPlayer.playAudio(fanfare);
                        }
                        table.removeText(player1WinsGame);       // not removing when called.
                        table.addText(title);

                        table.addBall(player1);
                        table.addBall(player2);
                        table.addBall(puck);
                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();

                        player1.setScore(0);
                        player1ScoreText.setText(Integer.toString(player1.getScore()));
                        player2.setScore(0);
                        player2ScoreText.setText(Integer.toString(player2.getScore()));
                    }
                } 
                else if (player2.getScore() == 6) {
                    table.removeText(title);
                    table.addText(player2WinsGame);

                    table.removeBall(player1);
                    table.removeBall(player2);
                    table.removeBall(puck);

                    if (table.spacePressed()) {
                        if (soundOn == true) {
                            soundPlayer.playAudio(fanfare);
                        }
                        table.removeText(player2WinsGame);      // not removing when called.
                        table.addText(title);

                        table.addBall(player1);
                        table.addBall(player2);
                        table.addBall(puck);
                        player1.resetPosition();
                        player2.resetPosition();
                        puck.resetPosition();
                        
                        player1.setScore(0);
                        player1ScoreText.setText(Integer.toString(player1.getScore()));
                        player2.setScore(0);
                        player2ScoreText.setText(Integer.toString(player2.getScore()));
                    }
                }

                try {
                    Thread.sleep(1);
                } catch (Exception e) {}
            }
        }
    };
    Thread scoreThread = new Thread(scoreEngine);

    /**
     * Boundary engine for bouncing puck off of table boundaries and 'Mallet' objects.
     */
    Runnable puckCollisionEngine = new Runnable() {
        public void run() {
            while(true) {
                if(player1.collides(puck)) {
                    double[] deflectValues = deflect(player1.getXPosition(), player1.getYPosition(), player1.getXSpeed(), player1.getYSpeed(), puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed());
                    // puck.setXSpeed(0);
                    // puck.setYSpeed(0);
                    if (player1.getXPosition() < puck.getXPosition()) { // these move the player a pixel away from the puck once it's hit
                        player1.move(-1, 0);                         // this is because in some cases the player can remain close to the puck if the puck takes too long to move.
                    }                                                   // meaning that the puck can exhibit odd behaviour.
                    if (player1.getXPosition() > puck.getXPosition()) {
                        player1.move(1, 0);
                    }
                    if (player1.getYPosition() < puck.getYPosition()) {
                        player1.move(0, -1);
                    }
                    if (player1.getYPosition() > puck.getYPosition()) {
                        player1.move(0, 1);
                    }
                    puck.setXSpeed(deflectValues[2]);
                    puck.setYSpeed(deflectValues[3]);
                }
                if(player2.collides(puck)) {
                    double[] deflectValues = deflect(player2.getXPosition(), player2.getYPosition(), player2.getXSpeed(), player2.getYSpeed(), puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed());
                    // puck.setXSpeed(0);
                    // puck.setYSpeed(0);
                    if (player2.getXPosition() < puck.getXPosition()) {
                        player2.move(-1, 0);
                    }
                    if (player2.getXPosition() > puck.getXPosition()) {
                        player2.move(1, 0);
                    }
                    if (player2.getYPosition() < puck.getYPosition()) {
                        player2.move(0, -1);
                    }
                    if (player2.getYPosition() > puck.getYPosition()) {
                        player2.move(0, 1);
                    }
                    puck.setXSpeed(deflectValues[2]);
                    puck.setYSpeed(deflectValues[3]);
                }
        
        
                // left side of boundary
                if (puck.getXPosition() <= (tableSurface.getXPosition() + (puck.getSize() /2))) {
                    // double[] deflectValues = deflect(puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed(), 1, puck.getXPosition(), puck.getYPosition(), 0, 0, 1000000);
                    puck.setXSpeed(puck.getXSpeed() * -1);
                    // puck.setXSpeed(deflectValues[0]);
                    // puck.setYSpeed(deflectValues[1]);
                }
                // top side of boundary
                if (puck.getYPosition() <= (tableSurface.getYPosition() + (puck.getSize() /2))) {
                    // double[] deflectValues = deflect(puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed(), 1, puck.getXPosition(), puck.getYPosition(), 0, 0, 100);
                    puck.setYSpeed(puck.getYSpeed() * -1);
                    // puck.setXSpeed(deflectValues[0]);
                    // puck.setYSpeed(deflectValues[1]);
                }
                // right side of boundary
                if (puck.getXPosition() >= (tableSurface.getXPosition() + tableSurface.getWidth() - (puck.getSize()/2))) {
                    // double[] deflectValues = deflect(puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed(), 1, puck.getXPosition(), puck.getYPosition(), 0, 0, 1000000);
                    puck.setXSpeed(puck.getXSpeed() * -1);
                    // puck.setXSpeed(deflectValues[0]);
                    // puck.setYSpeed(deflectValues[1]);
                }
                // bottom side of boundary
                if (puck.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight() - (puck.getSize()/2))) {
                    // double[] deflectValues = deflect(puck.getXPosition(), puck.getYPosition(), puck.getXSpeed(), puck.getYSpeed(), 1, puck.getXPosition(), puck.getYPosition(), 0, 0, 100);
                    puck.setYSpeed(puck.getYSpeed() * -1);
                    // puck.setXSpeed(deflectValues[0]);
                    // puck.setYSpeed(deflectValues[1]);
                }

                puck.move(puck.getXSpeed(), puck.getYSpeed());

                try {
                    Thread.sleep(1);
                } catch(Exception e) {}
            }
        }
    };
    Thread puckCollisionThread = new Thread(puckCollisionEngine);

    /**
     * Friction engine for allowing the puck to slow down due to slight friction.
     */
    double frictionThreshold = 0.01; // this is used to set the pucks speed to 0 
    Runnable puckFrictionEngine = new Runnable() {
        public void run() {
            while(true) {
                System.out.println("Puck speed is " + puck.getXSpeed() + ", " + puck.getYSpeed());
                // if (puck.getXSpeed() > 0) { // friction statements to allow the puck to slow down over time
                //     puck.setXSpeed(puck.getXSpeed() * puck.getFriction());
                // }
                // if (puck.getYSpeed() > 0) {
                //     puck.setYSpeed(puck.getYSpeed() * puck.getFriction());
                // }

                // speed > 0     // we need to determine whether the puck is moving left or right across the air hockey table to then be able to calculate and set speed based on friction.
                if (puck.getXSpeed() > 0) {
                    puck.setXSpeed(puck.getXSpeed() * puck.getFriction());
                    if (puck.getXSpeed() < frictionThreshold) {
                        puck.setXSpeed(0);
                    }
                }
                if (puck.getYSpeed() > 0) {
                    puck.setYSpeed(puck.getYSpeed() * puck.getFriction());
                    if (puck.getYSpeed() < frictionThreshold) {
                        puck.setYSpeed(0);
                    }
                }
                // speed < 0
                if (puck.getXSpeed() < 0) {
                    puck.setXSpeed(puck.getXSpeed() * puck.getFriction());
                    if (puck.getXSpeed() > -(frictionThreshold)) {
                        puck.setXSpeed(0);
                    }
                }
                if (puck.getYSpeed() < 0) {
                    puck.setYSpeed(puck.getYSpeed() * puck.getFriction());
                    if (puck.getYSpeed() > -(frictionThreshold)) {
                        puck.setYSpeed(0);
                    }
                }
                try {
                    Thread.sleep(5);
                } catch (Exception e) {}
            }
        }
    };
    Thread puckFrictionThread = new Thread(puckFrictionEngine);

    /**
     * Sound engine for playing sounds such as deflection sounds, hit sounds, and celebratory sounds.
     */
    Runnable soundEngine = new Runnable() {
        public void run() {
            while(true) {
                // Goals Scored (applause.wav)
                if (puck.getXPosition() >= goal1.getXPosition() && puck.getXPosition() <= (goal1.getXPosition() + goal1.getWidth())) {
                    if (puck.getYPosition() >= goal1.getYPosition() && puck.getYPosition() <= (goal1.getYPosition() + goal1.getHeight())) {
                        if (soundOn == true) {
                            soundPlayer.playAudio(applause);
                        }
                    }
                }                                                   // not working
                if (puck.getXPosition() >= goal2.getXPosition() && puck.getXPosition() <= (goal2.getXPosition() + goal2.getWidth())) {
                    if (puck.getYPosition() >= goal2.getYPosition() && puck.getYPosition() <= (goal2.getYPosition() + goal2.getHeight())) {
                        if (soundOn == true) {
                            soundPlayer.playAudio(applause);
                        }
                    }
                }                                                   // not working

                // Player wins (applause.wav)
                if (player1.getScore() == 6 || player2.getScore() == 6) {
                    if (soundOn == true) {
                        soundPlayer.playAudio(drumroll);
                    }
                }

                //Player hitting puck (hit.wav)
                if(player1.collides(puck) || player2.collides(puck)) {
                    if (soundOn == true) {
                        soundPlayer.playAudio(hit);
                    }
                }

                if ((puck.getXPosition() <= (tableSurface.getXPosition() + (puck.getSize() /2))) ||
                    (puck.getYPosition() <= (tableSurface.getYPosition() + (puck.getSize() /2))) ||
                    (puck.getXPosition() >= (tableSurface.getXPosition() + tableSurface.getWidth() - (puck.getSize()/2))) ||
                    (puck.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight() - (puck.getSize()/2)))) {
                        if (soundOn == true) {
                            soundPlayer.playAudio(bounce);
                        }
                }

                try {
                    Thread.sleep(1);
                } catch (Exception e) {}
            }
        }
    };
    Thread soundThread = new Thread(soundEngine);

    /**
     * Key press engine to detect key presses for commands such as muting and cheat codes.
     */
    Runnable hotkeyEngine = new Runnable() {
        public void run() {
            while(true) {
                // mute and unmute sounds
                if (table.letterPressed('m') == true) {
                    if (soundOn == true) {
                        soundOn = false;
                    } else {
                        soundOn = true;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {}
                }
                // player 1 speed CHEAT CODE
                if (table.letterPressed('z') ==  true) {
                    if (player1.getSpeedMultiplier() <= 0.01) {
                        player1.setSpeedMultiplier(player1.getSpeedMultiplier() * 2);
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {}
                    }
                }
                // player 2 speed CHEAT CODE
                if (table.letterPressed('x') ==  true) {
                    if (player2.getSpeedMultiplier() <= 0.01) {
                        player2.setSpeedMultiplier(player2.getSpeedMultiplier() * 2);
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {}
                    }
                }
                // first player to press repective letter gets double points CHEAT CODE
                if (table.letterPressed('v') == true) {
                    if (doublePointsActive == false) {
                        player1.setScoreMultiplier(2);
                        doublePointsActive = true;
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {}
                    }
                }
                if (table.letterPressed('b') == true) {
                    if (doublePointsActive == false) {
                        player2.setScoreMultiplier(2);
                        doublePointsActive = true;
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {}
                    }
                }
        
                try {
                    Thread.sleep(1);
                } catch (Exception e) {}
            }
        }
    };
    Thread hotkeyThread = new Thread(hotkeyEngine);

    //// players (mallets)
    movementEngineThread.start();
    speedEngineThread.start();
    playerBoundaryThread.start();

    //// keeping score
    scoreThread.start();

    //// the puck (puck)
    puckCollisionThread.start();
    puckFrictionThread.start();

    //// sound effects
    soundThread.start();

    //// key commands (hotkeys)
    hotkeyThread.start();
    

}
}
