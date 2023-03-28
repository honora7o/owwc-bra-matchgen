package MatchGenerator.domain;

import java.util.ArrayList;

/**
 *
 * @author rato
 */
public class Team {

    private ArrayList<Player> team;

    public Team() {
        this.team = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (!this.team.contains(player)) {
            this.team.add(player);
        }
    }
    
    //prints each player in team
    public void printTeam() {
        this.team.stream().forEach(System.out::println);
    }

    public Player getPlayerByIndex(int index) {
        return this.team.get(index);
    }

    public String toString() {
        String formattedString = "";
        for (Player player : team) {
            formattedString += player.toString() + "\n";
        }

        return formattedString;
    }

    //increments matches played for each player in team
    public void incrementMatchesPlayed() {
        for (Player player : team) {
            player.incrementMatchesPlayed();
        }
    }

    public ArrayList<Player> getTeam() {
        return team;
    }
    
    public int getAvgElo() {
        int sum = 0;
        for (Player player : team) {
            sum += player.getElo();
        }
        
        return sum/5;
    }

}
