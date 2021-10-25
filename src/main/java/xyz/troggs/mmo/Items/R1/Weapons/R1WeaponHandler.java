package xyz.troggs.mmo.Items.R1.Weapons;

import xyz.troggs.mmo.Items.Item;
import xyz.troggs.mmo.Items.R1.Weapons.Cleric.ElderBerries;
import xyz.troggs.mmo.Items.R1.Weapons.Mage.WandOfSparking;
import xyz.troggs.mmo.Items.R1.Weapons.Ranger.LongBow;
import xyz.troggs.mmo.Items.R1.Weapons.Warrior.Broadsword;
import xyz.troggs.mmo.Main;

import java.util.HashMap;
import java.util.Map;

public class R1WeaponHandler {

    private Main main;

    public R1WeaponHandler(Main main){
        this.main = main;
    }

    public Map<String, Item> getItems(){
        Map<String, Item> map = new HashMap<String, Item>();
        map.putAll(getCleric());
        map.putAll(getMage());
        map.putAll(getRanger());
        map.putAll(getWarrior());
        return map;
    }

    public Map<String, Item> getCleric(){
        Map<String, Item> map = new HashMap<String, Item>();
        map.putAll(new ElderBerries().item(main));

        return map;
    }

    public Map<String, Item> getMage(){
        Map<String, Item> map = new HashMap<String, Item>();
        map.putAll(new WandOfSparking().item(main));

        return map;
    }

    public Map<String, Item> getRanger(){
        Map<String, Item> map = new HashMap<String, Item>();
        map.putAll(new LongBow().item(main));

        return map;
    }

    public Map<String, Item> getWarrior(){
        Map<String, Item> map = new HashMap<String, Item>();
        map.putAll(new Broadsword().item(main));

        return map;
    }
}
