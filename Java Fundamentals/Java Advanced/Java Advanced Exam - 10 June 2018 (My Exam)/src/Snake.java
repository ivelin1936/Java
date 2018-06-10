import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Snake {

    private static final String EMPTY_CELL = "*";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int sizeOfTheScreen = Integer.parseInt(reader.readLine());
        String[][] screenMatrix = new String[sizeOfTheScreen][sizeOfTheScreen];

        ArrayDeque<String> commands =
                Arrays.stream(reader.readLine()
                        .split(","))
                        .collect(Collectors.toCollection(ArrayDeque::new));

        int[] startPosition = fillScreenMatrix(reader, sizeOfTheScreen, screenMatrix);
        int currentRown = startPosition[0];
        int currentCol = startPosition[1];

        int totalFoodAmount = startPosition[2];
        int snakeLength = 1;

        while (commands.size() != 0) {
            String command = commands.pop();

            switch (command.trim()) {
                case "left":
                    if ((currentCol - 1) >= 0) {
                        currentCol--;
                    } else {
                        currentCol = screenMatrix[0].length - 1;
                    }
                    break;
                case "right":
                    if ((currentCol + 1) <= (screenMatrix[0].length - 1)) {
                        currentCol++;
                    } else {
                        currentCol = 0;
                    }
                    break;
                case "up":
                    if ((currentRown - 1) >= 0) {
                        currentRown--;
                    } else {
                        currentRown = screenMatrix.length - 1;
                    }
                    break;
                case "down":
                    if ((currentRown + 1) <= (screenMatrix.length - 1)) {
                        currentRown++;
                    } else {
                        currentRown = 0;
                    }
                    break;
            }

            String cellType = screenMatrix[currentRown][currentCol];
            if (cellType.equals("f")) {
                screenMatrix[currentRown][currentCol] = EMPTY_CELL;
                snakeLength += 1;
                totalFoodAmount--;

                if (totalFoodAmount == 0) {
                    System.out.println("You win! Final snake length is " + snakeLength);
                    return;
                }
            }

            if (cellType.equals("e")) {
                System.out.println("You lose! Killed by an enemy!");
                return;
            }
        }

        if (totalFoodAmount > 0) {
            System.out.println(String.format("You lose! There is still %d food to be eaten.", totalFoodAmount));
        }

        String debug = "";
    }

    private static int[] fillScreenMatrix(BufferedReader reader, int sizeOfTheScreen, String[][] screenMatrix) throws IOException {
        int[] startPosition = new int[3];
        int foodCounter = 0;
        for (int row = 0; row < sizeOfTheScreen; row++) {
            String[] tokens = reader.readLine().split("\\s+");
            for (int col = 0; col < sizeOfTheScreen; col++) {
                screenMatrix[row][col] = tokens[col];

                if (tokens[col].equals("f")) {
                    foodCounter++;
                    startPosition[2] = foodCounter;
                }

                if (tokens[col].equals("s")) {
                    startPosition[0] = row;
                    startPosition[1] = col;
                }
            }
        }
        return startPosition;
    }
}