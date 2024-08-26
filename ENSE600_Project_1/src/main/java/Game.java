
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Game {
    private Player player;
    private Scanner scanner;

    public Game() {
        // weapon creation
        Weapon sword = new Weapon("Sword", 10, 0.2); //name, attack power, attackBonus.
        Weapon axe = new Weapon("Axe", 15, 0.1);
        Weapon bow = new Weapon("Bow", 8, 0.3);

        // player creation
        player = new Player("Hero", 10, 100, 20, sword, 0.2); //name, level, health, attackCharacter, Weapon, defenseIgnore
        scanner = new Scanner(System.in);
        
        start();
    }

    private void start() {

    }

}

