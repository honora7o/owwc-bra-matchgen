package MatchGenerator;

import MatchGenerator.logic.MatchGenerator;
import MatchGenerator.ui.UserInterface;
import java.util.Scanner;

/**
 *
 * @author rato
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MatchGenerator mg = new MatchGenerator();
        UserInterface ui = new UserInterface(scanner, mg);
        ui.start();

    }
}
