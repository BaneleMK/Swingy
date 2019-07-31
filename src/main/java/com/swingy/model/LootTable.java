package com.swingy.model;

import java.util.Random;

public class LootTable{
    static String weaponnames[] = {"Stark_Blade", "Stick_of_Truth", "Daedric_blade"};
    static int weapondamages[] = { 10, 4, 15};

    static String armornames[] = {"Stark_armor", "Armor_of_Truth", "Daedric_armor"};
    static int armordefense[] = { 7, 4, 9};

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
}