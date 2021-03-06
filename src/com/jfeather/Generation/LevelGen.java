package com.jfeather.Generation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;

import com.jfeather.Game.GameInstance;
import com.jfeather.Level.LevelInstance;
import com.jfeather.Level.Theme;
import com.jfeather.Player.PlayerInstance;

public class LevelGen {
	
	private static final int MIN_PATH_LENGTH = 3;
	private static final int MAX_PATH_LENGTH = 8;
	private static final int PATH_WIDTH = 3;
	
	//TODO write several basic templates to base the levels around, probably in txt format using unicode stuff
	
	// Very basic method to generate a rectangle room with a varying theme
	public static LevelInstance genRoom(int floorNumber, PlayerInstance p, GameInstance ins, Theme theme) {
		Random rng = new Random();
		int width = rng.nextInt(10) + 5;
		int length = rng.nextInt(10) + 5;
		LevelInstance instance = new LevelInstance(floorNumber, p, ins, theme);
		Image[][] tiles = new Image[width][length];
		BufferedImage image = new BufferedImage(width * 48, length * 48, BufferedImage.TYPE_INT_ARGB);
		Image roomTile = genRoomTile(theme);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = roomTile;
			}
		}
		
		int widthCurr = 0;
		int lengthCurr = 0;
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				g2d.drawImage(tiles[i][j], widthCurr, lengthCurr, null);
				widthCurr += 48;
			}
			lengthCurr += 48;
			widthCurr = 0;
		}
		instance.addImage(image, instance.getCoords());
		instance.setFloor(floorNumber);
		return instance;
	}
	
	public static LevelInstance genHalls(int floorNumber, PlayerInstance p, GameInstance ins, Theme theme) {
		LevelInstance instance = new LevelInstance(floorNumber, p, ins, theme);
		ins.setBackground(theme.getBackground());
		int numberOfRooms = (int) (floorNumber * .5 + 10);
		BufferedImage[] rooms = new BufferedImage[numberOfRooms];
		int[][] roomBounds = new int[2][numberOfRooms];
		Random rng = new Random();
		
		for (int k = 0; k < numberOfRooms; k++) {
			roomBounds[0][k]= rng.nextInt(7) + 8;
			roomBounds[1][k] = rng.nextInt(7) + 8;
			rooms[k] = new BufferedImage(roomBounds[0][k] * 48, roomBounds[1][k] * 48, BufferedImage.TYPE_INT_ARGB);
			//LevelInstance instance = new LevelInstance(floorNumber, p, ins);
			Image[][] tiles = new Image[roomBounds[0][k]][roomBounds[1][k]];
			Image roomTile = genRoomTile(theme);
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[0].length; j++) {
					tiles[i][j] = roomTile;
				}
			}
			
			int widthCurr = 0;
			int lengthCurr = 0;
			Graphics2D g2d = (Graphics2D) rooms[k].getGraphics();
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[0].length; j++) {
					g2d.drawImage(tiles[i][j], lengthCurr, widthCurr, null);
					widthCurr += 48;
				}
				lengthCurr += 48;
				widthCurr = 0;
			}
		}		
		
		// Generate the bridge lengths
		int[] bridgeLengths = new int[numberOfRooms];
		for (int i = 0; i < numberOfRooms; i++)
			bridgeLengths[i] = rng.nextInt(MAX_PATH_LENGTH - MIN_PATH_LENGTH) + MIN_PATH_LENGTH;
				
		// Draw the first room in the center
		instance.addImage(rooms[0]);
		
		// Initialize the updating coordinates
		int currentX = instance.getX();
		int currentY = instance.getY();
		
		// Generate the path tile (is the same for every path, unlike floor tiles)
		Image pathTile = genPathTile(theme);
		
		// Make the first pathway
		int[] side = new int[numberOfRooms];
		side[0] = rng.nextInt(4);
		//side[0] = 3;
		Image path = null;
		switch (side[0]) {
			case 0:
				// Up
				path = drawPath(PATH_WIDTH, bridgeLengths[0], pathTile);
				currentX += (rooms[0].getWidth() / 2) - 48;
				currentY -= path.getHeight(null);
				break;
			case 1:
				// Down
				path = drawPath(PATH_WIDTH, bridgeLengths[0], pathTile);
				currentX += (rooms[0].getWidth() / 2) - 48;
				currentY += rooms[0].getHeight();
				break;
			case 2:
				// Left
				path = drawPath(bridgeLengths[0], PATH_WIDTH, pathTile);
				currentX -= path.getWidth(null);
				currentY += (rooms[0].getHeight() / 2) - 48;
				break;
			case 3:
				// Right
				path = drawPath(bridgeLengths[0], PATH_WIDTH, pathTile);
				currentX += rooms[0].getWidth();
				currentY += (rooms[0].getHeight() / 2) - 48;
				break;
		}
		//g2.drawImage(path, currentX, currentY, null);
		instance.addImage(path, currentX, currentY);
		
		// Make the pathways, starting from the second one
		for (int k = 1; k < numberOfRooms; k++) {
			// Make sure that the path doesn't overlap the previous one
			while (true) {
				side[k] = rng.nextInt(4);
				if (k >= 2) {
					// Make sure it doesn't approach itself and overlap
					if (!(side[k - 2] == 3 && side[k - 1] != 3 && side[k] ==  2)
							&& !(side[k - 2] == 2 && side[k - 1] != 2 && side[k] ==  3)
							&& !(side[k - 2] == 0 && side[k - 1] != 0 && side[k] ==  1)
							&& !(side[k - 2] == 1 && side[k - 1] != 1 && side[k] ==  0)
							&& !(side[k - 1] == 0 && side[k] == 1) && !(side[k - 1] == 1 && side[k] == 0) && !(side[k - 1] == 2 && side[k] == 3) && !(side[k - 1] == 3 && side[k] == 2))
						break;
				} else {
					// Make sure it doesn't go backwards
					if (!(side[k - 1] == 0 && side[k] == 1) && !(side[k - 1] == 1 && side[k] == 0) && !(side[k - 1] == 2 && side[k] == 3) && !(side[k - 1] == 3 && side[k] == 2)) {
						break;
					}
				} 			
			}
			
			// Draw the new room, and update coords as required
			switch (side[k - 1]) {
				case 0:
					// Up
					currentX -= rooms[k].getWidth() / 2;
					currentY -= bridgeLengths[k - 1] * 48;
					break;
				case 1:
					// Down
					currentX -= rooms[k].getWidth() / 2;
					currentY += bridgeLengths[k - 1] * 48;
					break;
				case 2:
					// Left
					currentX -= rooms[k].getWidth();
					currentY -= rooms[k].getHeight() / 2;
					break;
				case 3:
					// Right
					currentX += bridgeLengths[k - 1] * 48; // No change needed
					currentY -= rooms[k].getHeight() / 2;
					break;
			}
			instance.addImage(rooms[k], currentX, currentY);
			
			// Draw the new pathway
			switch (side[k]) {
				case 0:
					// Up
					path = drawPath(3, bridgeLengths[k], pathTile);
					currentX += (rooms[k].getWidth() / 2) - 48;
					currentY -= path.getHeight(null);
					break;
				case 1:
					// Down
					path = drawPath(3, bridgeLengths[k], pathTile);
					currentX += (rooms[k].getWidth() / 2) - 48;
					currentY += rooms[k].getHeight();
					break;
				case 2:
					// Left
					path = drawPath(bridgeLengths[k], 3, pathTile);
					currentX -= path.getWidth(null);
					currentY += (rooms[k].getHeight() / 2) - 48;
					break;
				case 3:
					// Right
					path = drawPath(bridgeLengths[k], 3, pathTile);
					currentX += rooms[k].getWidth();
					currentY += (rooms[k].getHeight() / 2) - 48;
					break;
			}
			instance.addImage(path, currentX, currentY);
		}
		
		// TODO draw the final boss room at the end of each level
		switch (side[side.length - 1]) {
		case 0:
			// Up
			currentX -= rooms[side.length - 1].getWidth() / 2;
			currentY -= bridgeLengths[side.length - 1] * 48;
			break;
		case 1:
			// Down
			currentX -= rooms[side.length - 1].getWidth() / 2;
			currentY += bridgeLengths[side.length - 1] * 48;
			break;
		case 2:
			// Left
			currentX -= rooms[side.length - 1].getWidth();
			currentY -= rooms[side.length - 1].getHeight() / 2;
			break;
		case 3:
			// Right
			currentX += bridgeLengths[side.length - 1] * 48;
			currentY -= rooms[side.length - 1].getHeight() / 2;
			break;
		}
		genRoomTile(theme);
		instance.setFloor(floorNumber);
		return instance;
	}
	
	public static int sum(int[] arr) {
		int sum = 0;
		for (int n: arr)
			sum += n;
		return sum;
	}
	
	public static Image drawPath(int width, int length, Image tile) {
		Image[][] tiles = new Image[length][width];
		BufferedImage image = new BufferedImage(width * 48, length * 48, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = tile;
			}
		}
		
		int widthCurr = 0;
		int lengthCurr = 0;
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				g2d.drawImage(tiles[i][j], widthCurr, lengthCurr, null);
				widthCurr += 48;
			}
			lengthCurr += 48;
			widthCurr = 0;
		}
		
		return image;
	}
	
	public static Image genRoomTile(Theme theme) {
		File folder = new File(theme.getFolder() + "RoomTiles/");
		File[] arr = folder.listFiles();
		Random rng = new Random();
		if (arr.length == 0 || (arr.length == 1 && arr[0].getName().equals(".directory")))
			return new ImageIcon("Sprites/Level/InvalidTile.png").getImage();
		while (true) {
			int i = rng.nextInt(arr.length);
			if (!arr[i].getName().equals(".directory")) {
				//System.out.println(arr[i].getName());
				return new ImageIcon(arr[i].getPath()).getImage();
			}
		}
	}
	
	public static Image genPathTile(Theme theme) {
		File folder = new File(theme.getFolder() + "PathTiles/");
		File[] arr = folder.listFiles();
		Random rng = new Random();
		if (arr.length == 0 || (arr.length == 1 && arr[0].getName().equals(".directory")))
			return new ImageIcon("Sprites/Level/InvalidTile.png").getImage();
		while (true) {
			int i = rng.nextInt(arr.length);
			if (!arr[i].getName().equals(".directory"))
				return new ImageIcon(arr[i].getPath()).getImage();
		}
	}
}
