import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import dev.robocode.tankroyale.botapi.graphics.Color;

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

        //goToTopRightCorner();

        while (isRunning()) {
            setBodyColor(Color.BROWN);
            setGunColor(Color.RED);
            setBulletColor(Color.RED);


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

    // Helper method to move to top-right corner
    private void goToTopRightCorner() {
        int battlefieldWidth = getArenaWidth();
        int battlefieldHeight = getArenaHeight();

        double x = getX();
        double y = getY();

        // Move horizontally to right edge
        if (x < battlefieldWidth) {
            double angle = 0 - getDirection(); // face east
            turnLeft(normalizeAngle(angle));
            forward(battlefieldWidth - x);
        }

        // Move vertically to top edge
        if (y < battlefieldHeight) {
            double angle = 90 - getDirection(); // face north
            turnLeft(normalizeAngle(angle));
            forward(battlefieldHeight - y);
        }

        // Face south after reaching corner
        turnLeft(normalizeAngle(180 - getDirection()));
    }

    // Normalize angle to range [-180, 180]
    private double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
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