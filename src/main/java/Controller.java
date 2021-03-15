public class Controller{
    View view;
    Game game;

    public Controller(String[] args) {
        view = View.getInstance(this);
        game = Game.getInstance(args);
    }

    public String chooseRole() {
        String roleChosen = "";
        // lets user choose role from interface
        return roleChosen;
    }

    public String[] chooseUpgrade() {
        String[] upgradeChosen = {"dollars", "2"};
        // lets user choose upgrade from interface
        return upgradeChosen;
    }

    public void process(String res) {
        if (res.equals("move")) {
            game.tryMove();
        } else if (res.equals("take role")) {
            game.tryTakeRole();
        } else if (res.equals("upgrade")) {
            game.tryUpgrade();

        } else if (res.equals("rehearse")) {
            game.tryRehearse();
        } else if (res.equals("act")) {
            game.tryAct();
        } else if (res.equals("end")) {
            game.endTurn();
        }
    }
}