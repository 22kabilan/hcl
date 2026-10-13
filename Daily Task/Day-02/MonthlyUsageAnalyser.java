public class MonthlyUsageAnalyser {

    static final long SLAB_LIMIT = 100;
    static final long SLAB_RATE = 5;

    public static void main(String[] args) {

        int[] monthlyUsage = {
            120, 150, 90, 200, 180, 160,
            140, 130, 170, 190, 210, 220
        };

        long total = 0;
        int max = monthlyUsage[0];
        int min = monthlyUsage[0];

        for (int usage : monthlyUsage) {
            total += usage;

            if (usage > max) {
                max = usage;
            }

            if (usage < min) {
                min = usage;
            }
        }

        double average = (double) total / monthlyUsage.length;

        char grade = average > 180 ? 'A' :
                     average > 140 ? 'B' :
                     average > 100 ? 'C' : 'D';

        System.out.println("Total Usage: " + total);
        System.out.println("Average Usage: " + average);
        System.out.println("Maximum Usage: " + max);
        System.out.println("Minimum Usage: " + min);
        System.out.println("Grade: " + grade);

        int[][] houses = {
            {120, 150, 90, 200},
            {100, 130, 110, 170},
            {200, 220, 190, 210}
        };

        System.out.println("\nHouse Usage:");

        for (int i = 0; i < houses.length; i++) {
            System.out.print("House " + (i + 1) + ": ");

            for (int j = 0; j < houses[i].length; j++) {
                System.out.print(houses[i][j] + " ");
            }

            System.out.println();
        }
    }
}