package com.swingy.model;

import java.util.Random;

public class LootTable{
    static String weaponnames[] = {"Stark_Blade", "Stick_of_Truth", "Daedric_blade"};
    static int weapondamages[] = { 10, 4, 15};

    static String armornames[] = {"Stark_armor", "Armor_of_Truth", "Daedric_armor"};
    static int armordefense[] = { 3, 2, 5};

    static String helmnames[] = {"Stark_helm", "Helm_of_Truth", "Daedric_helm"};
    static int helmhitpoints[] = { 10, 5, 15};
    
    /**
     * @return the weaponnames
     */
    public static String[] getWeaponnames() {
        return weaponnames;
    }

    /**
     * @return the weapondamages
     */
    public static int[] getWeapondamages() {
        return weapondamages;
    }

    /**
     * @return the armornames
     */
    public static String[] getArmornames() {
        return armornames;
    }

    /**
     * @return the armordefense
     */
    public static int[] getArmordefense() {
        return armordefense;
    }

    /**
     * @return the helmnames
     */
    public static String[] getHelmnames() {
        return helmnames;
    }

    /**
     * @return the helmhitpoints
     */
    public static int[] getHelmhitpoints() {
        return helmhitpoints;
    }

    private LootTable(){
    
    }

    static Artifact genArtifact(int level){
        Random rand = new Random();
        int artifact_number = rand.nextInt(3);

        switch (rand.nextInt(3)){
            case 0:
                return new Weapon(LootTable.weaponnames[artifact_number], LootTable.weapondamages[artifact_number], level);
            case 1:
                return new Armor(LootTable.armornames[artifact_number], LootTable.armordefense[artifact_number], level);
            case 2:
                return new Helm(LootTable.helmnames[artifact_number], LootTable.helmhitpoints[artifact_number], level);
            default:
                return null;
        }
    }

    static String villainnames[] = {"Skumwam", "Skhumba", "Smoother", "Cooler", "Charmer", "Bowser", "Pain", "TK", "Norm", "Caustic", "Jiren"};
    static String villainclass[] = {"Slime", "Zombie", "Boss_monster"};
    static int villaindamages[] = { 2, 4, 15};
    static int villainhitpoints[] = { 15, 20, 55};
    static int villaindefense[] = { 0, 1, 15};

    static Villain genVillain(int level){
        Random rand = new Random();
        int class_number = rand.nextInt(3);
        int chosen_level = level;

        switch (rand.nextInt(2)) {
            case 0:
                chosen_level += rand.nextInt(2);               
                break;
            case 1:
                chosen_level -= rand.nextInt(2);
                if (chosen_level <= 0)
                    chosen_level = 1;
                break;
        }


        return new Villain(LootTable.villainnames[rand.nextInt(LootTable.villainnames.length)], LootTable.villainclass[class_number], chosen_level,
        LootTable.villaindamages[class_number], LootTable.villainhitpoints[class_number], LootTable.villaindefense[class_number]);
    }
}