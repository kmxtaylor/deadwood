import java.util.*;

public class Player{

    private String playerName;
    private int rank = 1;
    private int dollars = 0;
    private int credits = 0;
    private boolean employed = false; 
    private int rehearseTokens = 0;
    private Set location;
    private String roleName;

    public Player(Set s, String p){
        playerName = p;
        location = s;
    }

    public Player(Set s, String p, int sl, int sc){
        playerName = p;
        rank = sl;
        location = s;
    }

    public String getName(){
        return playerName;
    }
    public void setName(String s){
        this.playerName = s;
    }

    public int getRank(){
        return rank;
    }
    public void setLevel(int level){
        this.rank = level;
    }

    public int getDollars(){
        return dollars;
    }
    public void incDollar(int dollar){
        this.dollars += dollar;
    }

    public int getCredits(){
        return credits;
    }
    public void incCred(int credit){
        this.credits += credit;
    }

    public boolean isEmployed() {
        return employed;
    }
    public void giveRole(String rn){
        this.roleName = rn;
        this.employed = true;
    }

    public int getRehearseTokens() {
        return rehearseTokens;
    }
    public void incToken(){
        this.rehearseTokens++;
    }

    public Set getLocation() {
        return location;
    }
    public void setLocation(Set newPos){ // I don't think this is used anywhere
        this.location = newPos;
    }
    //to move a player to a neighbor
    public static boolean canMoveTo(String dest, List<String> neighbors){
        for(int i = 0; i < neighbors.size(); i++){
            //if the designated neighbor exists return true
            if(dest.equals(neighbors.get(i)))
            {
                return true;
            }
        }
        return false;
    }
    public boolean moveTo(Set dest) {
        boolean successfulMove = false;
        String destName;

        //if player is not employed
        if(!employed){
            try { // this might not be the best way to handle this error...
                destName = dest.getName(); // null if not a valid Set name
            } catch (NullPointerException e){
                System.out.println("The place you tried to move to isn't a valid set name. Try again..or don't.");
                return successfulMove;
            }

            if (location.checkNeighbor(destName) ) {
                location = dest;
                System.out.println("You're now located in " + destName);
                successfulMove = true;
            }
            else
            {
                System.out.println("You can't move to that location; it's not a neighbor of the current location. (View neighbors with the command `neighbors`.)");
            }
        }
        else{
            System.out.println("Since you are employed in a role, you cannot move but you can act or rehearse if you have not already");
        }

        return successfulMove;
    }
    
    public String getRoleName() {
        return roleName;
    }
    public void resetRole(){
        roleName = "";
        this.rehearseTokens = 0;
        this.employed = false;
    }

    public boolean act(List<Player> onCardPlayers, List<Player> offCardPlayers) {
        boolean successfulActing = false;
        if(employed){
            //make and roll a die
            int budget = Integer.valueOf((location.getCard()).getBudget());
            int die = 1 + (int)(Math.random() * 6);

            System.out.println("The budget of your current job is: " + budget + ", you rolled a: " + die + ", and you have " + rehearseTokens + " rehearsal tokens");
            //if the die is higher than the budget
            if(budget > (die + rehearseTokens)){
                //for acting failures on card and off card
                System.out.println("Acting failure!");
                if(location.getCard().hasRole(roleName)){
                    System.out.println("Since you had an important role you get nothing!");
                }
                else{
                    System.out.println("Since you weren't that important you will get 1 dollar, out of pity");
                    this.incDollar(1);
                }
            }
            else{
                //for acting successes on card and off card
                System.out.println("Acting success!");
                if(location.getCard().hasRole(roleName)){
                    System.out.println("Since you were important, you get 2 credits");
                    this.incCred(2);
                }
                else{
                    System.out.println("Since you weren't that important you get 1 credit and 1 dollar");
                    this.incCred(1);
                    this.incDollar(1);
                }
                //increment takes
                location.decTakesLeft();
            }

            //if it was the last scene and someone was on card hand out bonuses
            if(location.getTakesLeft() == 0){
                
                if(location.canBonus()){
                    System.out.println("Oh no! That was the last scene! Everyone gets money!");
                    location.bonuses(onCardPlayers, offCardPlayers);
                }
                else{
                    System.out.println("That was the last scene, but no ones on card so no one gets money! Aha!");
                }
                location.wrapUp(onCardPlayers, offCardPlayers);
            }
            else{
                System.out.println("There are only " + location.getTakesLeft() + " takes left in this scene!");
            }

            successfulActing = true;
        }
        else{
            System.out.println("You are currently not employed");
        }

        return successfulActing;
    }

    public boolean rehearse() {
        boolean successfulRehearsal = false;

        // if player is employed
        if (employed) {
            //and if the player does not have the max number of tokens already
            if(rehearseTokens < 5) {
                this.incToken();
                System.out.println("You've rehearsed! You gain a rehearsal token (adds +1 to your subsequent dice rolls while acting on role only)");
                successfulRehearsal = true;
            } else {
                System.out.println("You have reached the max for rehearsal and only have the option to act. Its a guaranteed success though!");
            }
        } else {
            System.out.println("You're not employed? What are you going to rehearse for??");
        }

        return successfulRehearsal;
    }

    public int calcFinalScore(){
        return (dollars + credits + (5 * rank));
    }
}