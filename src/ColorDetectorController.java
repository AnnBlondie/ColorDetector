import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorDetectorController {
	private Map<String, Color> primaryColors = new HashMap<String, Color>();
	private Map<String, Color> longNameColors = new HashMap<String, Color>();
	private ColorDetector view;
	
	ColorDetectorController(ColorDetector view){
		this.view=view;
		
		setPrimaryColors();
		//setLongNameColors();	
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
	
	private String getPrimaryColor(Color color){
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
	
	private int colorDistance(Color first, Color second){
		return (first.getRed()-second.getRed())*(first.getRed()-second.getRed())
				+(first.getBlue()-second.getBlue())*(first.getBlue()-second.getBlue())
				+(first.getGreen()-second.getGreen())*(first.getGreen()-second.getGreen());
	}
}
