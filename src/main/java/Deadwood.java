public class Deadwood{
    public static void main(String[] args){

        Controller controller = new Controller(args);

        Game.getInstance().run();

    }

}
