package byow.lab12;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

/**
 * 1. Build a Hexagon
 * Recursion
 * 1. draw the first line
 * 2. draw the remaining line
 * 3. draw the last line (same as the first line)
 */

public class HexWorldDemo {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    // Private helper class to deal with the position
    private static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    /**
     * Draw a row of tiles to the board at a given position
     */
    public static void drawRow(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            tiles[p.x + dx][p.y] = tile;
        }
    }

    /**
     * Adds a hexagon to the world at position P of size SIZW
     */
    public static void addHexagon(TETile[][] tiles, Position p, TETile t, int size) {
        if (size < 2) {
            return;
        }
        addHexagonHelper(tiles, p, t, size - 1, size);
    }

    public static void addHexagonHelper(TETile[][] tiles, Position p, TETile tile, int b, int t) {
        Position startOfRow = p.shift(b, 0);
        drawRow(tiles, startOfRow, tile, t);
        if (b > 0) {
            Position nextLine = p.shift(0, - 1);
            addHexagonHelper(tiles, nextLine, tile, b - 1, t + 2);
        }
        Position newLine = startOfRow.shift(0, -(2 * b + 1));
        drawRow(tiles, newLine, tile, t);
    }

    /**
     * Add a column of hexagons, each of whose biomes are chosen randomly
     * to the world at position P. Each of the hexagons are of size `SIZE`.
     */
    public static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
        if (num < 1) {
            return;
        }
        // Draw this hexagon
        addHexagon(tiles, p, randomBiome(), size);

        if (num > 1) {
            Position bottomNeighbor = getBottomNeighbor(p, size);
            addHexColumn(tiles, bottomNeighbor, size, num - 1);
        }

    }

    public static Position getBottomNeighbor(Position p, int size) {
        return p.shift(0, - 2 * size);
    }
    /**
     * Fills the given 2D array of tiles with blank tiles
     */
    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static TETile randomBiome() {
        int tileNum = RANDOM.nextInt(9);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            case 5: return Tileset.GRASS;
            case 6: return Tileset.AVATAR;
            case 7: return Tileset.FLOOR;
            case 8: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }
    /**
     * Get the position of the top right neighbor of a hexagon
     * at Position P. N is the size of the hexagon we are tessellating.
     */
    public static Position getTopRightNeighbor(Position p , int n) {
        return p.shift(2 * n - 1, n);
    }

    public static Position getBottomRightNeighbor(Position p , int n) {
        return p.shift(2 * n - 1, - n);
    }

    /**
     * Draws the hexagonal world
     */
    public static void drawWord(TETile[][] tiles, Position p, int hexSize, int tessSize) {

        addHexColumn(tiles, p, hexSize, tessSize);

        for (int i = 1; i < tessSize; i++) {
            p = getTopRightNeighbor(p, hexSize);
            addHexColumn(tiles, p, hexSize, tessSize + i);
        }
        for (int i = 0; i < tessSize - 1; i++) {
            p = getBottomRightNeighbor(p, hexSize);
            addHexColumn(tiles, p, hexSize, 2 * tessSize - 2 - i);
            // tessSize + tessSize - 1 is the # of hexagons of the highest column
            // the first column is the right neighbour of the highest column
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(world);
        Position anchor = new Position(12 ,34);
        drawWord(world, anchor, 3, 4);
        ter.renderFrame(world);
    }
}
