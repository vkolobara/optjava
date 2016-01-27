package hr.fer.zemris.optjava.gp;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class AntMap {

	private int height;
	private int width;
	private int foodNum;
	private boolean[][] foodMap;
	
	public AntMap(String path) throws IOException {
		initMap(path);
	}

	private void initMap(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		
		Iterator<String> it = lines.iterator();
		String line = it.next();
		it.remove();
		String dims[] = line.split("x");
		height = Integer.parseInt(dims[0]);
		width = Integer.parseInt(dims[1]);
		foodNum = 0;
		foodMap = new boolean[height][width];
		
		int i=0;
		while (it.hasNext()) {
			line = it.next();
			int j=0;
			for (char c : line.toCharArray()) {
				if (c == '1') {
					foodMap[i][j] = true;
					foodNum++;
				}
				j++;
			}
			
			i++;
		}
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public boolean[][] getFoodMap() {
		return foodMap;
	}
	
	public int getFoodNum() {
		return foodNum;
	}

}
