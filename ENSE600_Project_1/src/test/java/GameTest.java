
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
class GameTest {

    Player player;
    Enemy enemy;
    DBInitializer dbInitializer;
    Battle battle;
    ShopGUI shop;

    @BeforeEach
    void setup() {
        dbInitializer = new DBInitializer(true);  // Assuming true for fresh setup
        dbInitializer.setupDatabase();
        player = new Player("bob", 1, 100, 10, null, 100, 0);
        enemy = new Enemy("bad guy", 1, 50, 5);
        battle = new Battle(player, enemy);
    }

    @Test
    void testBattleInitialization() {
        assertNotNull(battle, "Battle should initialize correctly");
        assertEquals("bob", player.getName());
        assertEquals("bad guy", enemy.getName());
    }

    @Test
    void testDatabaseInitialization() {
        assertDoesNotThrow(() -> dbInitializer.setupDatabase(), "Database should initialize without throwing exceptions");
    }

    @Test
    void testPlayerLevelUp() {
        player.addXP(200);  // Assuming level 1, this should trigger level-up
        assertTrue(player.getLevel() > 1, "Player should level up when enough XP is gained");
    }

    @Test
    void testInventoryAddItem() {
    }

    @Test
    void testInventoryRemoveItem() {
    }

}
