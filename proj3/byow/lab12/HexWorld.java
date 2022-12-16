package byow.lab12;
import edu.princeton.cs.algs4.TST;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import ucb.gui.Widget;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 60;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void drawFull(TETile[][] tiles, int n, int tessSize) {
        // total layer = tessSize * 2 - 1 + tessSize
        int startX = (WIDTH - n) / 2 - n + 1;
        int layer = 0;
        int Y = 0;
        for (int startY = 0; layer < tessSize; startY += n) {
            drawFull(tiles, n, startX, layer, startY);
            if (layer == tessSize - 2) {
                for (int i = 0; i < tessSize - 1; i++) {
                    drawFull(tiles, n, startX, layer, startY + 2 * (i + 1) * n);
                }
            }
            if (layer == tessSize - 1) {
                for (int i = 0; i < tessSize - 2; i++) {
                    drawFull(tiles, n, startX, layer, startY + 2 * (i + 1) * n);
                }
            }
            layer += 1;
        }
        int totalHeight = (tessSize * 2 - 1) * 2 * n;
        layer = 0;
        for (int startY = totalHeight - 2 * n; layer < tessSize; startY -= n) {
            drawFull(tiles, n, startX, layer, startY);
            layer += 1;
        }
    }

    private static void drawFull(TETile[][] tiles, int n, int startX, int layer, int startY) {
        int startXforloop = startX - (2 * n - 1) * layer;
        int i = 0;
        while (i < layer + 1) {
            if (startXforloop >= 0 && startXforloop + n + 2 * (n-1) < WIDTH) {
                addHexagon(tiles, n, startXforloop, startY);
                startXforloop += n + 2 * (n - 1) + n;
            } else if (startXforloop + n + 2 * (n - 1) < WIDTH) {
                startXforloop += 3 * n - 2 + n;
                while (startXforloop < 0) {
                    startXforloop += 3 * n - 2 + n;
                }
                addHexagon(tiles, n, startXforloop , startY);
                startXforloop += n + 2 * (n - 1) + n;
            } else {
                break;
            }
            i += 1;
        }
    }

    public static void addHexagon(TETile[][] tiles, int n, int startX, int startY) {
        TETile style = randomTile();
        int width = 2 * (n - 1) + n;
        for (int i = 0; i < n; i++) { // the height
            int thisLine = n + i; // i = 0, this line = 2, start from 0, to width - 0
            drawRow(tiles, thisLine + startY, i + startX, width - i + startX, style); // the # of real thing
        }
        // the half bottom part
        for (int line = 0; line < n; line++) { // i = 0, this line = 0, start from 1, to width - 2
            drawRow(tiles, line + startY, n - line - 1 + startX, width - (n - line - 1) + startX, style); // the # of real thing
        }
    }

    private static void drawRow(TETile[][] tiles, int line, int start, int end, TETile style) {
        for (int real = start; real < end; real++) {
            tiles[real][line] = style;
        }
    }

    // TETile.colorVariant
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] blankTiles = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(blankTiles);

        int hexSize = 3;
        int tessSize = 5;
        drawFull(blankTiles, hexSize, tessSize);
        ter.renderFrame(blankTiles);
    }

    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
    private static TETile randomTile() {
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

}
