
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;
/**
* This class runs a world with additional grid choices.
*/
public final class SparseBoundedGridRunner
{
  public static void main(String[] args)
  {
    ActorWorld world = new ActorWorld();
    world.addGridClass("SparseBoundedGrid");
    world.addGridClass("SparseBoundedGrid1");
    world.addGridClass("SparseUnboundedGrid");
    world.add(new Location(2, 2), new Critter());
    world.show();
  }

  private SparseBoundedGridRunner() {
    // default constructor.
  }
}