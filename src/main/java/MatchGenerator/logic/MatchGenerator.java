package MatchGenerator.logic;

import MatchGenerator.domain.Player;
import MatchGenerator.domain.Match;
import MatchGenerator.domain.Team;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author rato
 */
public class MatchGenerator {

    private ArrayList<Player> pool; //pool of available players

    public MatchGenerator() {
        this.pool = new ArrayList<>();
        this.readPool();
    }

    //reads csv file "pool.txt" line by line and adds player (one by line) to pool
    private void readPool() {
        try (Scanner fileScanner = new Scanner(new File("pool.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] split = fileScanner.nextLine().split(",");
                this.pool.add(new Player(split[0], split[1], Integer.valueOf(split[2]), Integer.valueOf(split[3])));
            }

        } catch (Exception e) {
            System.out.println("Could not read file: " + e);
        }
    }

    //updates and prints all available players in pool
    public void printPool() {
        this.resetPool();
        this.pool.stream().forEach(System.out::println);
    }

    //sorts and returns new team
    public Team generateTeam() {
        Team team = new Team();

        team.addPlayer(getRandomElementByRole(this.pool, "tank"));
        team.addPlayer(getRandomElementByRole(this.pool, "dps"));
        team.addPlayer(getRandomElementByRole(this.pool, "dps"));
        team.addPlayer(getRandomElementByRole(this.pool, "supp"));
        team.addPlayer(getRandomElementByRole(this.pool, "supp"));

        return team;
    }

    //get random element(player) by role and removes it from pool of available players to be picked
    public Player getRandomElementByRole(ArrayList<Player> pool, String role) {
        while (true) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(pool.size());
            Player randomPlayer = pool.get(randomIndex);
            if (randomPlayer.getRole().equals(role)) {
                pool.remove(randomIndex);
                return randomPlayer;
            }
        }
    }

    //generates matches until match with set matchDiff is randomized
    public Match generateMatch() {
        while (true) {
            Team team1 = generateTeam();
            Team team2 = generateTeam();
            Match match = new Match(team1, team2);
            
            if (match.getMatchDiff() < 2) {
                return match;
            } else {
                System.out.println("finding match...");
                this.resetPool();
            }
        }
    }

    //checks if player is available in pool by name
    public boolean isInPoolByName(String name) {
        for (Player player : this.pool) {
            if (player.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private void clearPool() {
        this.pool.clear();
    }

    public ArrayList<Player> getPool() {
        return pool;
    }

    public void resetPool() {
        this.clearPool();
        this.readPool();
    }

}
