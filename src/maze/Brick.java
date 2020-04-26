/*
 * Created on 3 mai 2005
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package maze;

import java.awt.Color;
import java.awt.Point;

/**
 * @author deloor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Brick {
	private Point _position;
	private Color _color;
	private int _value;
	
	public Brick(int x, int y, Color c, int value)
	{
		_position = new Point(x,y);
		_color = c;
		_value = value;
	}
	
	public Point getPosition(){
		return(_position);
	}
	
	
	public Color getColor(){
		return(_color);
	}
	
	public int getValue(){
		return(_value);
	}
}
