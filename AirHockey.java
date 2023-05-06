import java.util.function.LongConsumer;

public class AirHockey
{

public static int gameSpeed = 1; // higher number means slower game speed
// try
// {
//     Thread.sleep(gameSpeed);
// }
// catch (Exception e) {}


public static void main(String[] args)
    {
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
        Ball puck = new Ball(550, 300, 20, "black", 1);
        Ball player1 = new Ball(250, 300, 50, "black", 1);
        Ball player2 = new Ball(850, 300, 50, "black", 1);
        
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
        Runnable movementEngine = new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    if (table.letterPressed('w'))
                    {
                        player1.move(0,-1);
                    }
                    if (table.letterPressed('s'))
                    {
                        player1.move(0,1);
                    }
                    if (table.letterPressed('a'))
                    {
                        player1.move(-1,0);
                    }
                    if (table.letterPressed('d'))
                    {
                        player1.move(1,0);
                    }
                    if (table.upPressed())
                    {
                        player2.move(0,-1);
                    }
                    if (table.downPressed())
                    {
                        player2.move(0,1);
                    }
                    if (table.leftPressed())
                    {
                        player2.move(-1,0);
                    }
                    if (table.rightPressed())
                    {
                        player2.move(1,0);
                    }
                    System.out.println(player1.getXSpeed() + " xSpeed for Player1");
                    System.out.println(player1.getYSpeed() + " ySpeed for Player1");
                    System.out.println(player2.getXSpeed() + " xSpeed for Player2");
                    System.out.println(player2.getYSpeed() + " ySpeed for Player2");
                    try
                    {
                        Thread.sleep(gameSpeed);
                    }
                    catch (Exception e) {}
                }
            }
        };
        Thread movementEngineThread = new Thread(movementEngine);
        movementEngineThread.start();

        // Speed engine for calculating multiplied speed over time with the Player mallets.
        Runnable speedEngine = new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    while(table.letterPressed('a') || table.letterPressed('d'))
                    {
                        player1.changeXSpeed(player1.getXSpeed() * 1.00000000001);
                    }
                    while(table.letterPressed('w') || table.letterPressed('s'))
                    {
                        player1.changeYSpeed(player1.getYSpeed() * 1.00000000001);
                    }
                    while(table.leftPressed()|| table.rightPressed())
                    {
                        player2.changeXSpeed(player2.getXSpeed() * 1.00000000001);
                    }
                    while(table.upPressed() || table.downPressed())
                    {
                        player2.changeYSpeed(player2.getYSpeed() * 1.00000000001);
                    }
                    try
                    {
                        Thread.sleep(gameSpeed);
                    }
                    catch (Exception e) {}
                }
            }
        };
        Thread speedEngineThread = new Thread(speedEngine);
        speedEngineThread.start();

        // Collision engine for preventing the players from leaving their halves of the table or the table itself.
        Runnable playerCollisionEngine = new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    if (player1.getXPosition() <= (tableSurface.getXPosition() + (player1.getSize()/2)))
                    {
                        player1.setXPosition(player1.getXPosition() + 1);
                    }
                    if (player1.getYPosition() <= (tableSurface.getYPosition() + (player1.getSize()/2)))
                    {
                        player1.setYPosition(player1.getYPosition() + 1);
                    }
                    if (player1.getXPosition() >= (tableSurface.getXPosition() + tableSurface.getWidth()/2) )//- (player1.getSize()/2))
                    {
                        player1.setXPosition(player1.getXPosition() - 1);
                    }
                    if (player1.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight()) )//- (player1.getSize()/2))
                    {
                        player1.setYPosition(player1.getYPosition() - 1);
                    }

                    if (player2.getXPosition() <= (tableSurface.getXPosition() + tableSurface.getWidth()/2) )//+ (player2.getSize()/2))
                    {
                        player2.setXPosition(player2.getXPosition() + 1);
                    }
                    if (player2.getYPosition() <= (tableSurface.getYPosition() + (player2.getSize()/2)))
                    {
                        player2.setYPosition(player2.getYPosition() + 1);
                    }
                    if (player2.getXPosition() >= (tableSurface.getXPosition() + tableSurface.getWidth()) )//- (player2.getSize()/2))
                    {
                        player2.setXPosition(player2.getXPosition() - 1);
                    }
                    if (player2.getYPosition() >= (tableSurface.getYPosition() + tableSurface.getHeight()) )//- (player2.getSize()/2))
                    {
                        player2.setYPosition(player2.getYPosition() - 1);
                    }

                    try
                    {
                        Thread.sleep(gameSpeed);
                    }
                    catch (Exception e) {}
                }
            }
        };
        Thread playerCollisionThread = new Thread(playerCollisionEngine);
        playerCollisionThread.start();

    


        
    }
}