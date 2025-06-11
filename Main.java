import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final int TEAM_SIZE = 10;

    public static void main(String[] args) throws IOException {
        int[][] teams = {
                { 45, 31, 24, 22, 20, 17, 14, 13, 12, 10 },
                { 31, 18, 15, 12, 10, 8, 6, 4, 2, 1 },
                { 51, 30, 10, 9, 8, 7, 6, 5, 2, 1 }
        };

        int[] nationalTeam = mergeAll(teams);
        System.out.println(Arrays.toString(nationalTeam));
    }

    public static int[] mergeAll(int[][] teams) {
        if (teams.length == 0) {
            return new int[0];
        }

        int[] nationalTeam = teams[0];

        for (int i = 1; i < teams.length; i++) {
            nationalTeam = merge(nationalTeam, teams[i]);
        }

        return nationalTeam;
    }

    public static int[] merge(int[] teamA, int[] teamB) {
        int[] mergedTeam = new int[TEAM_SIZE];
        int i = 0, j = 0, k = 0;

        while (k < TEAM_SIZE) {
            if (teamA[i] >= teamB[j]) {
                mergedTeam[k++] = teamA[i++];
            } else {
                mergedTeam[k++] = teamB[j++];
            }
        }

        return mergedTeam;
    }
}