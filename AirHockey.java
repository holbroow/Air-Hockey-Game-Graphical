public class AirHockey
{


public static void main(String[] args)
    {
        // Game window (Game Arena named 'table')
        GameArena table = new GameArena(1100, 600);

        // Air Hockey Table
        Rectangle background = new Rectangle(0, 0, 1100, 600, "grey");
        Rectangle tableBorder = new Rectangle(100, 100, 900, 400, "black");
        Rectangle tableSurface = new Rectangle(125, 125, 850, 350, "white");
        Rectangle goal1 = new Rectangle(125, 212, 10, 175, "yellow");
        Rectangle goal2 = new Rectangle(965, 212, 10, 175, "yellow");
        Line centreLine = new Line(550, 125, 550, 475, 1, "black");
        Ball centreRingOutline = new Ball(550, 300, 70, "black");
        Ball centreRing = new Ball(550, 300, 68, "white");

        // 2 Players and Hockey puck
        Ball puck = new Ball(550, 300, 20, "black");
        Ball player1 = new Ball(250, 300, 50, "black");
        Ball player2 = new Ball(850, 300, 50, "black");
        
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
        table.addBall(puck);
        table.addBall(player1);
        table.addBall(player2);
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
                    try
                    {
                        Thread.sleep(2);
                    }
                    catch(Exception e) {}
                }
            }
        };
        Thread movementEngineThread = new Thread(movementEngine);
        movementEngineThread.start();

        Runnable speedEnginePlayer1 = new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    while(table.letterPressed('a') || table.letterPressed('d'))
                    {
                        player1.changeXSpeed(player1.xSpeed() * 1.1);
                    }
                    while(table.letterPressed('w') || table.letterPressed('s'))
                    {
                        player1.changeYSpeed(player1.ySpeed() * 1.1);
                    }
                    System.out.printf("xSpeed for Player1 is %d.", player1.xSpeed());
                    System.out.printf("ySpeed for Player1 is %d.", player1.ySpeed());
                }
            }
        };
        Thread speedEnginePlayer1Thread = new Thread(speedEnginePlayer1);
        speedEnginePlayer1Thread.start();

        Runnable speedEnginePlayer2 = new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    while(table.leftPressed() || table.rightPressed())
                    {
                        player1.changeXSpeed(player1.xSpeed() * 1.1);
                    }
                    while(table.upPressed() || table.downPressed())
                    {
                        player1.changeYSpeed(player1.ySpeed() * 1.1);
                    }
                }
            }
        };
        Thread speedEnginePlayer2Thread = new Thread(speedEnginePlayer2);
        speedEnginePlayer2Thread.start();


        // // Collision for Player 1, 2, and the Puck
        // Runnable collisionEngine = new Runnable()
        // {
        //     public void run()
        //     {
        //         if (player1.collides(puck))
        //         {
        //             if (player1.getXPosition() < puck.getXPosition())
        //             {
        //                 if (player1.getYPosition() < puck.getYPosition())
        //                 {
        //                     puck.move(1, 1);
        //                 }
        //                 else
        //                 {
        //                     puck.move(1, 0);
        //                 }
        //             }
        //             else
        //             {
        //                 if (player1.getYPosition() > puck.getYPosition())
        //                 {
        //                     puck.move(-1, -1);
        //                 }
        //                 else
        //                 {
        //                     puck.move(-1, 0);
        //                 }
        //             }
        //         }

        //     }
        // };
        // Thread collisionEngineThread = new Thread(collisionEngine);
        // collisionEngineThread.start();



    }
}