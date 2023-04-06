package main;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameWindow game = GameWindow.get();
        game.setup();
        game.run();
    }
}