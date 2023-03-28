package MatchGenerator.domain;

import java.util.ArrayList;

/**
 *
 * @author rato
 */
public class Match {

    private ArrayList<Team> teams;

    public Match(Team team1, Team team2) {
        this.teams = new ArrayList<>();
        this.teams.add(team1);
        this.teams.add(team2);
    }

    //prints match in formatted manner
    public void printMatch() {
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                System.out.println("Team 1 - avg: " + this.teams.get(0).getAvgElo() + " | " + "Team 2 - avg: " + this.teams.get(1).getAvgElo());
            }
            System.out.println(this.teams.get(0).getPlayerByIndex(i) + "   |   " + this.teams.get(1).getPlayerByIndex(i));
        }
    }

    //increments matches played for all players in match
    public void incrementMatchesPlayed() {
        for (Team team : teams) {
            team.incrementMatchesPlayed();
        }
    }

    //returns arraylist from all players in match
    public ArrayList<Player> playersInMatch() {
        ArrayList<Player> team1 = teams.get(0).getTeam();
        ArrayList<Player> team2 = teams.get(1).getTeam();
        team1.addAll(team2);

        return team1;
    }

    public int getMatchDiff() {
        return Math.abs(this.teams.get(0).getAvgElo() - this.teams.get(1).getAvgElo());
    }
}
