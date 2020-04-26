/*
 * Created on 3 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package maze;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author deloor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Maze {
	private ArrayList _listBrick;
	private int _high;
	private int _length;
	
	public Maze(){
		
		_listBrick=new ArrayList();
		
		//Grand Labyrinthe 1
                
		_high=15;
		_length=20;
			for(int x=0; x<30;x++){
				_listBrick.add(new Brick(x,0, Color.green,-1));	
				_listBrick.add(new Brick(x,15,Color.green,-1));
			}
			for(int x=15; x<30;x++){
				_listBrick.add(new Brick(x,8, Color.green,-1));	
			}
			for(int y=0; y <=15;y++){
				_listBrick.add(new Brick(0,y, Color.green,-1));	
				_listBrick.add(new Brick(30,y,Color.green,-1));
			}
			for(int y=5; y <12;y++){
				_listBrick.add(new Brick(5,y, Color.green,-1));	
				_listBrick.add(new Brick(15,y,Color.green,-1));
			}
			for(int y=1; y <5;y++){
				_listBrick.add(new Brick(8,y, Color.green,-1));	
			}

			for(int y=10; y <15;y++){
				_listBrick.add(new Brick(10,y, Color.green,-1));	
			}	
			_listBrick.add(new Brick(19,14,Color.orange,10));
			//_listBrick.add(new Brick(4,4,Color.orange,10));
			//_listBrick.add(new Brick(12,4,Color.orange,10));
		
		//Grand Labyrinthe 2
        /*        
		_high=15;
		_length=20;
			for(int x=0; x<20;x++){
				_listBrick.add(new Brick(x,0, Color.green,-1));	
				_listBrick.add(new Brick(x,15,Color.green,-1));
			}
			for(int y=0; y <=15;y++){
				_listBrick.add(new Brick(0,y, Color.green,-1));	
				_listBrick.add(new Brick(20,y,Color.green,-1));
			}
			for(int y=5; y <15;y++){
				_listBrick.add(new Brick(5,y, Color.green,-1));	
				_listBrick.add(new Brick(15,y,Color.green,-1));
			}
			for(int y=1; y <5;y++){
				_listBrick.add(new Brick(7,y, Color.green,-1));	
			}	
			_listBrick.add(new Brick(19,14,Color.orange,10));
			//_listBrick.add(new Brick(4,4,Color.orange,10));
			//_listBrick.add(new Brick(12,4,Color.orange,10));
			*/
                
		// petit Labyrinthe
           /*     
		_high =10;
		_length = 10;
		for(int x=0; x<10;x++){
			_listBrick.add(new Brick(x,0, Color.green,-1));	
			_listBrick.add(new Brick(x,10,Color.green,-1));
		}
		for(int y=0; y <=10;y++){
			_listBrick.add(new Brick(0,y, Color.green,-1));	
			_listBrick.add(new Brick(10,y,Color.green,-1));
		}


		for(int y=3; y <9;y++){
			_listBrick.add(new Brick(8,y, Color.green,-1));	
			//_listBrick.add(new Brick(,y,Color.green,-1));
		}
                for(int y=3; y <8;y++){
			_listBrick.add(new Brick(4,y, Color.green,-1));
		}
		//for(int y=1; y <2;y++){
		//	_listBrick.add(new Brick(4,y, Color.green,-1));	
		//}	
		//_listBrick.add(new Brick(1,2,Color.orange,10));
		_listBrick.add(new Brick(9,9,Color.orange,10));
		//_listBrick.add(new Brick(12,4,Color.orange,10))
		*/
		
	}
	
	public ArrayList getListBrick(){
		return(_listBrick);
	}
	public boolean isThereABrickHere(Point p){
		for(int i =0; i<_listBrick.size();i++){
			Brick b = (Brick)_listBrick.get(i);
			if ((b.getPosition().equals(p)))
				return(true);
		}
		return false;
	}
	
	public Brick returnTheBrickHere(Point p){
		for(int i =0; i<_listBrick.size();i++){
			Brick b = (Brick)_listBrick.get(i);
			if ((b.getPosition().equals(p)))
				return(b);
		}
		return ((Brick) null);
	}
	
	public Point findAPositionFreeRandomly(){
		Point p = new Point();
		Random generator = new Random();
		do{
			p.setLocation(generator.nextInt(_length),generator.nextInt(_high));		
		}
		while(isThereABrickHere(p)!=false);
		return p;
	}
        
        public void rewardChange(){
            //retrait brick reward
            int nbRewardBrick=0;
            for(int  i = _listBrick.size()-1;i>=0;i--)
            {
                if (((Brick)(_listBrick.get(i))).getValue()==10){
                    nbRewardBrick++;
                    _listBrick.remove(i);
                }
            }
            for(int i=0;i<nbRewardBrick;i++){
                Point p = findAPositionFreeRandomly();
                _listBrick.add(new Brick((int)(p.getX()),(int)(p.getY()),Color.orange,10));
            }
                
        }
        
        public int getHigh(){
            return this._high;
        }
        
        public int getLength(){
            return this._length;
        }
	
}
