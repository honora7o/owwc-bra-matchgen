package MatchGenerator.domain;

/**
 *
 * @author rato
 */
public class Player {

    private final String name;
    private final String role;
    private int matchesPlayed;
    private final int elo;

    public Player(String name, String role, int elo, int matchesPlayed) {
        this.name = name;
        this.role = role;
        this.matchesPlayed = matchesPlayed;
        this.elo = elo;
    }

    public String toString() {
        return this.name + "," + this.role + "," + this.elo + "," + this.matchesPlayed;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void incrementMatchesPlayed() {
        this.matchesPlayed++;
    }
    
    public int getElo() {
        return this.elo;
    }
}
