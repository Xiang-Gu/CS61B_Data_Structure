public class BubbleGrid {
    private int[][] grid;

    public BubbleGrid(int[][] g) {
        int numRows = g.length;
        int numCols = g[0].length;
        grid = new int[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = g[row][col];
            }
        }
    }

    public int[] popBubbles(int[][] darts) {
        

    }
}
