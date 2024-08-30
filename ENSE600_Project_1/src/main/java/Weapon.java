
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Weapon implements Serializable{
    private static final long serialVersionUID = 1L;
    private String weaponName;
    private int weaponATK;

    public Weapon(String weaponName, int weaponATK) {
        this.weaponName = weaponName;
        this.weaponATK = weaponATK;
    }

    public String getWeaponName() { return weaponName; }
    public int getWeaponATK() { return weaponATK; }

    public void setWeaponName(String weaponName) { this.weaponName = weaponName; }
    public void setWeaponATK(int weaponATK) { this.weaponATK = weaponATK; }

}


