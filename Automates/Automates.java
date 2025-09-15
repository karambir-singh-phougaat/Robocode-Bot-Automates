import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class Automates extends Bot {

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

            /*turnGunRight(360);
            turnGunLeft(360);*/

            forward(100);
            turnGunRight(360);
            back(150);
            turnGunLeft(360);

            forward(50);
            turnGunRight(360);
            back(70);
            turnGunLeft(360);
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(5);
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
        forward(120);
    }
}