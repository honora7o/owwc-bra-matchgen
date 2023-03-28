package MatchGenerator.ui;

import MatchGenerator.domain.Match;
import MatchGenerator.domain.Player;
import MatchGenerator.logic.MatchGenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rato
 */
public class UserInterface {

    private final MatchGenerator matchGenerator;
    private final Scanner scanner;

    public UserInterface(Scanner scanner, MatchGenerator matchGenerator) {
        this.scanner = scanner;
        this.matchGenerator = matchGenerator;
    }

    public void start() {
        while (true) {
            System.out.println("Commands: \n"
                    + "1 - Generate match\n"
                    + "2 - List players\n"
                    + "3 - Add player to pool\n"
                    + "4 - Remove player from pool\n"
                    + "X - Stop");

            String command = scanner.nextLine().toLowerCase();
            if (command.equals("x")) {
                break;
            }

            if (command.equals("1")) {
                while (true) {
                    Match match = this.matchGenerator.generateMatch();
                    match.printMatch();
                    System.out.println("Reroll teams? Y/N");
                    String confirmMatch = scanner.nextLine().toLowerCase();
                    if (confirmMatch.equals("n")) {
                        //save match to file and end program when user types n to confirm match
                        System.out.println("Match confirmed. Updating count for players in the match.");
                        match.incrementMatchesPlayed();
                        this.updateMatchCountFile(match);
                        break;
                    } else if (confirmMatch.equals("y")) {
                        //rerolls match until user types n to confirm match
                        this.matchGenerator.resetPool();
                        System.out.println("Rerolling match.");
                    }
                }
            }

            if (command.equals("2")) {
                System.out.println("player name, role, elo, matches played");
                this.matchGenerator.printPool();
            }

            if (command.equals("3")) {
                this.addPlayerToPool();
            }

            if (command.equals("4")) {
                this.removePlayerByName();
            }
        }
    }

    //add player to pool and save it to "pool.txt" csv file
    private boolean addPlayerToPool() {
        System.out.println("Player name: ");
        String playerName = scanner.nextLine();

        System.out.println("Player role: ");
        String playerRole = scanner.nextLine();
        
        System.out.println("Player elo: ");
        String playerElo = scanner.nextLine();

        //check if no duplicate then add to file in csv format
        if (this.matchGenerator.isInPoolByName(playerName)) {
            System.out.println(playerName + " is already in the pool.");
            return false;
        }

        try {
            FileWriter writer = new FileWriter("pool.txt", true);

            writer.append("\n" + playerName + "," + playerRole + "," + playerElo + ",0");
            writer.close();

            System.out.println(playerName + " added to pool.");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add " + playerName + " to pool." + e);
            return false;
        }
    }

    //removes player from pool and "pool.txt" csv file
    private boolean removePlayerByName() {
        System.out.println("Player to be removed(name): ");
        String playerName = scanner.nextLine();

        //check if exists on file then remove
        if (!this.matchGenerator.isInPoolByName(playerName)) {
            System.out.println(playerName + " is not in the pool.");
            return false;
        }

        try {
            //copies old file to new file line by line except for line which has player to be removed
            File inputFile = new File("pool.txt");
            File tempFile = new File("poolTemp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String nameSplitted = currLine.substring(0, currLine.indexOf(","));
                if (nameSplitted.equals(playerName)) {
                    continue;
                }
                writer.write(currLine + "\n");
            }

            reader.close();
            writer.close();
            //deletes old file and renames "temp" file to "pool.txt"
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println(playerName + " removed from pool.");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add" + playerName + "to pool." + e);
            return false;
        }
    }

    //merges players that were not picked and left in pool to all players in match and updates matches played in csv file
    private boolean updateMatchCountFile(Match match) {
        ArrayList<Player> pool = matchGenerator.getPool();
        pool.addAll(match.playersInMatch());

        try {
            FileWriter writer = new FileWriter("pool.txt", false);

            for (int i = 0; i < pool.size(); i++) {
                writer.write(pool.get(i).toString() + "\n");
            }
            writer.close();

            return true;
        } catch (Exception e) {
            System.out.println("Failed to add update match count." + e);
            return false;
        }
    }
}
