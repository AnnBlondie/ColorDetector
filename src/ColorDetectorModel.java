import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class ColorDetectorModel {
	private Map<String, Color> primaryColors = new HashMap<String, Color>();
	private Map<Color, String> allColors = new HashMap<Color, String>();
	
	ColorDetectorModel(){
		setPrimaryColors();
		setAllColors();
	}
	
	private void setPrimaryColors(){
		primaryColors.put("red",		new Color(255,   0,   0));
		primaryColors.put("green",		new Color(  0, 255,   0));
		primaryColors.put("blue",		new Color(  0,   0, 255));
		primaryColors.put("yellow",		new Color(255, 255,   0));
		primaryColors.put("white",		new Color(255, 255, 255));
		primaryColors.put("black",		new Color(  0,   0,   0));
		primaryColors.put("gray",		new Color(128, 128, 128));
		primaryColors.put("cyan",		new Color(	0, 255, 255));
		primaryColors.put("darkGray",	new Color( 64,  64,  64));
		primaryColors.put("lightGray",	new Color(192, 192, 192));
		primaryColors.put("magenta",	new Color(255,   0, 255));
		primaryColors.put("orange",		new Color(255, 200,   0));
		primaryColors.put("pink",		new Color(255, 175, 175));
	}
	
	public String getPrimaryColor(Color color){
		String colorName = "color unknown :(";
		int distance=256*256*3;
		for(String col:primaryColors.keySet()){
			if(colorDistance(color, primaryColors.get(col))<distance){
				colorName=col;
				distance=colorDistance(color, primaryColors.get(col));
			}
		}
		return colorName;
	}

	private void setAllColors() {
			File file = createFile("allColors.txt");
			String[] line = new String[5];
			String colorNameEng = "White";
			String colorNameRus = "Белый";
			int colorRed = 255;
			int colorGreen = 255;
			int colorBlue = 255;
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				boolean next = true;
				String colorLine = "";
				
				while (next) {
					
					try {
						colorLine = reader.readLine();
					} catch (IOException e) {
						//this.view.setOutput("Can't find colors.txt! Check file path.");
						System.out.println("Can't find colors.txt! Check file path.");
					}
					
					if (colorLine == null) {
						next = false;
					} else {
						line = colorLine.split("\t");
						colorNameEng = line[0];
						colorNameRus = line[1];
						colorRed = Integer.parseInt(line[2]);
						colorGreen = Integer.parseInt(line[3]);
						colorBlue = Integer.parseInt(line[4]);
						allColors.put(new Color(colorRed, colorGreen, colorBlue),
								colorNameEng + " \\ " + colorNameRus);
					}
				}
			} catch (FileNotFoundException e1) {
				//this.view.setOutput("Can't find colors.txt! Check file path.");
				System.out.println("Can't find colors.txt! Check file path.");
			}
	}
	
	public String getColorName(Color color){
		String colorName = "color unknown :(";
		int distance=256*256*3;
		if (allColors.containsKey(color)) {
			colorName = allColors.get(color);
		} else {
			for (Color col : allColors.keySet()) {
				if(colorDistance(color, col) < distance){
					colorName = allColors.get(col);
					distance = colorDistance(color, col);
				}
			}
			colorName = "approximately " + colorName;
		}
		return colorName;
	}
    
	protected File createFile(String name) {
        java.net.URL imgURL = ColorDetector.class.getResource(name);
    	File file = null;
        if (imgURL != null) {
			try {
				file = new File(imgURL.toURI());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            return file;
        } else {
            return file;
        }
    }

	private int colorDistance(Color first, Color second){
		return (first.getRed()-second.getRed())*(first.getRed()-second.getRed())
				+(first.getBlue()-second.getBlue())*(first.getBlue()-second.getBlue())
				+(first.getGreen()-second.getGreen())*(first.getGreen()-second.getGreen());
	}
}
