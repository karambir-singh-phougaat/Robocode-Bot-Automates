import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import java.util.Random;

public class Automates extends Bot {

    private final Random random = new Random();

    // The main method starts our bot
    public static void main(String[] args) {
        new Automates().start();
    }

    // Constructor, which loads the bot config file
    Automates() {
        super(BotInfo.fromFile("Automates.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        turnRadarRight(360);
        // Repeat while the bot is running
        while (isRunning()) {
            moveRandomly();
            turnRadarRight(30);

            moveRandomly();
            turnRadarLeft(50);
        }
    }

    /** Random zig-zag movement to be less predictable */

    private void moveRandomly() {
        if (random.nextBoolean()) {
            forward(80 + random.nextInt(60));
        } else {
            back(80 + random.nextInt(60));
        }

        if (random.nextBoolean()) {
            turnLeft(30 + random.nextInt(40));
        } else {
            turnRight(30 + random.nextInt(40));
        }
    }


    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(25);
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
    }
}
