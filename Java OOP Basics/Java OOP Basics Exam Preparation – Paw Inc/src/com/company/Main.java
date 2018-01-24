package com.company;

import com.company.commandEngine.AnimalCenterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        AnimalCenterManager centerManager = new AnimalCenterManager();

        String stopCommand = "Paw Paw Pawah";
        String[] commandLine = reader.readLine().split(" \\| ");
        while (!commandLine[0].equals(stopCommand)) {
            processingCommands(commandLine, centerManager);
            commandLine = reader.readLine().split(" \\| ");
        }
        centerManager.printStatistics();
    }

    private static void processingCommands(String[] commandLine, AnimalCenterManager centerManager) {
        String command = commandLine[0].trim();

        switch (command) {
            case "RegisterCleansingCenter":
                registerCleansingCenter(commandLine, centerManager);
                break;
            case "RegisterAdoptionCenter":
                registerAdoptionCenter(commandLine, centerManager);
                break;
            case "RegisterDog":
                registerDog(commandLine, centerManager);
                break;
            case "RegisterCat":
                registerCat(commandLine, centerManager);
                break;
            case "SendForCleansing":
                sendForCleansing(commandLine, centerManager);
                break;
            case "Cleanse":
                cleanse(commandLine, centerManager);
                break;
            case "Adopt":
                adopt(commandLine, centerManager);
                break;
        }
    }

    private static void adopt(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> Adopt | {adoptionCenterName}
        String adoptionCenterName = commandLine[1].trim();
        centerManager.adopt(adoptionCenterName);
    }

    private static void cleanse(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> Cleanse | {cleansingCenterName}
        String cleansingCenterName = commandLine[1].trim();
        centerManager.cleanse(cleansingCenterName);
    }

    private static void sendForCleansing(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> SendForCleansing | {adoptionCenterName} | {cleansingCenterName}
        String adoptionCenterName = commandLine[1].trim();
        String cleansingCenterName = commandLine[2].trim();
        centerManager.sendForCleansing(adoptionCenterName, cleansingCenterName);
    }

    private static void registerCat(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> RegisterCat | {name} | {age| | {intelligenceCoefficient} | {adoptionCenterName}
        String name = commandLine[1].trim();
        int age = Integer.parseInt(commandLine[2].trim());
        int intelligenceCoefficient = Integer.parseInt(commandLine[3].trim());
        String adoptionCenterName = commandLine[4].trim();

        centerManager.registerCat(name, age, intelligenceCoefficient, adoptionCenterName);
    }

    private static void registerDog(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> RegisterDog | {name} | {age} | {learnedCommands} | {adoptionCenterName}
        String name = commandLine[1].trim();
        int age = Integer.parseInt(commandLine[2].trim());
        int learnedCommands = Integer.parseInt(commandLine[3].trim());
        String adoptionCenterName = commandLine[4].trim();

        centerManager.registerDog(name, age, learnedCommands, adoptionCenterName);
    }

    private static void registerAdoptionCenter(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> RegisterAdoptionCenter | {name}
        String name = commandLine[1].trim();
        centerManager.registerAdoptionCenter(name);
    }

    private static void registerCleansingCenter(String[] commandLine, AnimalCenterManager centerManager) {
        //input -> RegisterCleansingCenter | {name}
        String name = commandLine[1].trim();
        centerManager.registerCleansingCenter(name);
    }
}
