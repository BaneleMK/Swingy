package com.swingy.model;

import java.util.Random;

public class LootTable{
    static String weaponnames[] = {"Stark Blade", "Stick of Truth", "Daedric blade"};
    static int weapondamages[] = { 10, 4, 15};

    static String armornames[] = {"Stark armor", "armor of Truth", "Daedric armor"};
    static int armordefense[] = { 7, 4, 9};

    static String helmnames[] = {"Stark helm", "helm of Truth", "Daedric helm"};
    static int helmhitpoints[] = { 10, 5, 15};

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