import static org.junit.Assert.*;
import org.junit.Test;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Bug;
import info.gridworld.actor.ActorWorld;
import java.awt.Color;

public class JumperTest {

       private Jumper jumper = new Jumper();

       @Test
       public void testRockandActor() {                    // jumper could jump over the rock and actor
        ActorWorld world = new ActorWorld();
        world.add(new Location(7, 8), new Rock());
        world.add(new Location(5, 8), new Actor());
        world.add(new Location(8, 8), jumper);
        jumper.act();
        Location ans = new Location(6, 8);
        assertEquals(ans.getRow(), jumper.getLocation().getRow());
        assertEquals(ans.getCol(), jumper.getLocation().getCol());
        jumper.act();
        ans = new Location(4, 8);
        assertEquals(ans.getRow(), jumper.getLocation().getRow());
        assertEquals(ans.getCol(), jumper.getLocation().getCol());
       }
       @Test
       public void testTwoJumper() {             //if a jumper on the location, another jumper can't jump on the location
         ActorWorld world = new ActorWorld();
         Jumper jumper2 = new Jumper();
         jumper2.setDirection(Location.EAST);
         world.add(new Location(8, 8), jumper);
         world.add(new Location(6, 6), jumper2);
         jumper.act();
         jumper2.act();
         Location loc = new Location(6, 6);
         assertEquals(loc.getRow(), jumper2.getLocation().getRow());
         assertEquals(loc.getCol(), jumper2.getLocation().getCol());
       }
       @Test
       public void testCritter() {             //if a critter on the location, jumper can't jump on the location
         ActorWorld world = new ActorWorld();
         world.add(new Location(5, 5), new Critter());
         world.add(new Location(7, 5), jumper);
         jumper.act();
         Location ans = new Location(7, 5);
         assertEquals(ans.getRow(), jumper.getLocation().getRow());
         assertEquals(ans.getCol(), jumper.getLocation().getCol());
       }
       @Test
       public void testBugAndFlower() {       //Jumper could eat bug and flower
         ActorWorld world = new ActorWorld();
         world.add(new Location(6, 5), new Bug());
         world.add(new Location(4, 5), new Flower());
         world.add(new Location(8, 5), jumper);
         jumper.act();
         Location ans1 = new Location(6, 5);
         assertEquals(ans1.getRow(), jumper.getLocation().getRow());
         assertEquals(ans1.getCol(), jumper.getLocation().getCol());
         jumper.act();
         Location ans2 = new Location(4, 5);
         assertEquals(ans2.getRow(), jumper.getLocation().getRow());
         assertEquals(ans2.getCol(), jumper.getLocation().getCol());         
       }
       @Test
       public void testOutRange() {        //Grid is 10*10
         ActorWorld world = new ActorWorld();
         world.add(new Location(1, 5), jumper);
         jumper.act();
         Location ans = new Location(1, 5);
         assertEquals(ans.getRow(), jumper.getLocation().getRow());
         assertEquals(ans.getCol(), jumper.getLocation().getCol());
       }
} 
