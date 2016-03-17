package treechopper;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;
import java.util.Random;

@ScriptManifest(author = "ishyfishy", category = Category.WOODCUTTING, name = "wood chopper", version = 1.0)
public class firstScript extends AbstractScript{

    private final Area BANKAREA = new Area(3207,3220,3210,3216,2);
    private final Area TREEAREA = new Area(3191,3250,3202,3236);
    private final Tile LUMBRIDGESTAIR = new Tile(3206,3209,0);
    private final Tile TOPSTAIR = new Tile(3206,3209,2);
    private final Area SAFETILE = new Area(3202,3224,3204,3219,0);

    public int loopKnt = 0;

    public void loopCountPP(){
        this.loopKnt++;
    }

    public void onStart(){
        log("BEGIN");
    }

    private enum State{
        CHOP,
        BANK,
        WALK_TO_BANK,
        WALK_TO_TREE,
        WAIT
    }

    private State getState(){

        if(TREEAREA.contains(getLocalPlayer()) && !(getInventory().isFull())){
            return State.CHOP;
        }
        if(BANKAREA.contains(getLocalPlayer()) && getInventory().isFull()){
            return State.BANK;
        }
        if(getInventory().isFull()){
            return State.WALK_TO_BANK;
        }
        if(!(getInventory().isFull()) && !(TREEAREA.contains(getLocalPlayer()))){
            return State.WALK_TO_TREE;
        }
        if(TREEAREA.contains(getLocalPlayer()) && getGameObjects().closest("Tree")==null){
            return State.WAIT;
        }
        log("WARNING: RETURNING NULL");
        return null;
    }

    @Override
    public int onLoop(){

        loopCountPP();

        if(!getLocalPlayer().isOnScreen()){
            getCamera().rotateToEntity(getLocalPlayer());
        }

        if(getLocalPlayer().isMoving()){
            Tile dest = getClient().getDestination();
            if(getLocalPlayer().getTile().distance(dest) > 5){
                return Calculations.random(400,600);
            }
        }

        Random srand = new Random();
        if(!getWalking().isRunEnabled() && getWalking().getRunEnergy() > srand.nextInt(20)+40){
            getWalking().toggleRun();
        }

        if(getLocalPlayer().isInCombat()){
            getWalking().walk(SAFETILE.getRandomTile());
            log("We're in combat, going to safety tile.");
            sleepUntil(() -> SAFETILE.contains(getLocalPlayer()), Calculations.random(200, 700));
            sleepUntil(() -> !getLocalPlayer().isInCombat(), Calculations.random(600,1500));
        }

        switch(getState()){

            case CHOP:
                GameObject tree = getGameObjects().closest("Tree");
                log("Chopping new tree.");
                tree.interact("Chop down");

                sleepWhile(tree::exists, Calculations.random(5000, 13769));
                log("Tree chopped.");
                break;

            case BANK:
                log("Banking.");
                if(getBank().isOpen()){
                    getBank().depositAllItems();
                    sleepUntil(() -> getInventory().isEmpty(), Calculations.random(800,1250));
                } else {
                    getBank().open();
                    sleepUntil(() -> getBank().isOpen(), Calculations.random(500, 1250));
                }
                log("Banked.");
                break;

            case WALK_TO_TREE:
                log("Walking to tree.");
                if(getLocalPlayer().getTile().getZ() == 2){
                    if(getLocalPlayer().getTile().distance(TOPSTAIR) > 5){
                        getWalking().walk(TOPSTAIR);
                    } else {
                        GameObject stairs = getGameObjects().closest("Staircase");
                        if(stairs != null){
                            stairs.interact("Climb-down");

                            sleepUntil(() -> getLocalPlayer().getTile().getZ() == 1, Calculations.random(1000,2000));
                        }
                    }
                } else if (getLocalPlayer().getTile().getZ() == 1){
                    GameObject stairs = getGameObjects().closest("Staircase");
                    if(stairs != null){
                        stairs.interact("Climb-down");

                        sleepUntil(() -> getLocalPlayer().getTile().getZ() == 0, Calculations.random(1000,2000));
                    }
                } else {
                    if(getLocalPlayer().getTile().distance(TREEAREA.getRandomTile()) > 6){
                        getWalking().walk(TREEAREA.getRandomTile());
                    }
                }
                break;

            case WALK_TO_BANK:
                log("Walking to bank.");
                if(getLocalPlayer().getTile().getZ() == 2){
                    getWalking().walk(BankLocation.LUMBRIDGE.getCenter());
                } else if(getLocalPlayer().getTile().getZ() == 1){
                    GameObject stairs = getGameObjects().closest("Staircase");
                    if(stairs != null){
                        stairs.interact("Climb-up");
                        sleepUntil(() -> getLocalPlayer().getTile().getZ() == 2, Calculations.random(1000,1983));
                    }
                } else {
                    getWalking().walk(LUMBRIDGESTAIR);
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getLocalPlayer().getTile().distance(LUMBRIDGESTAIR) < 5;
                        }
                    }, Calculations.random(10000,10701));
                    GameObject stairs = getGameObjects().closest("Staircase");
                    if(stairs!=null){
                        stairs.interact("Climb-up");
                        sleepUntil(() -> getLocalPlayer().getTile().getZ()==1, Calculations.random(1002, 1540));
                        }
                    }
                break;

            case WAIT:
                log("No trees in TREEAREA, waiting for new tree to spawn.");
                sleepUntil(() -> {
                    GameObject tree1 = getGameObjects().closest("Tree");
                    return tree1 != null;
                }, Calculations.random(8000,14882));
                break;
        }

        return Calculations.random(200,300);
    }

    public void onPaint(Graphics g){

    }

    public void onExit(){
        log("Program looped " + this.loopKnt + " times");
    }

}
