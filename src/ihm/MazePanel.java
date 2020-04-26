/*
 * Created on 4 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ihm;
import learning.*;
import maze.*;
import java.awt.Color;
import java.awt.Graphics;



import javax.swing.JPanel;

/**
 *
 *
 * Ce panel dessine le labyrinte associe a l'application
 * Il affiche ou efface le Learner pour animer l'apprentissage
 * @author De Loor
 */
public class MazePanel extends JPanel {
	private Application _application;
	private static int _viewHigh=120;
        private static int _viewWidth=120;
	public void paint(Graphics g){
		int stepX = (int)(_viewWidth/_application.getMaze().getLength());
                int stepY = (int)(_viewHigh/_application.getMaze().getHigh());
		for(int i =0; i<_application.getMaze().getListBrick().size();i++){
			Brick b = (Brick)(_application.getMaze().getListBrick().get(i));
			g.setColor(b.getColor());
			g.fill3DRect((int)(b.getPosition().getX()*stepX),(int)(b.getPosition().getY()*stepY),stepX-2,stepY-2,true);
			
		}
		
		}
	
	public void eraseLearner(){
                int stepX = (int)(_viewHigh/_application.getMaze().getLength());
                int stepY = (int)(_viewWidth/_application.getMaze().getHigh());
		SarsaSituatedAgent learner = _application.getLearner();
		Graphics g=this.getGraphics();
		g.clearRect((int)(learner.getPosition().getX()*stepX),(int)(learner.getPosition().getY()*stepY),stepX,stepY);
		//g.fillOval((int)(learner.getPosition().getX()*10+2),(int)(learner.getPosition().getY()*10+2),5,5);
		
	}

        public void eraseMe(SarsaSituatedAgent learner){
                int stepX = (int)(_viewHigh/_application.getMaze().getLength());
                int stepY = (int)(_viewWidth/_application.getMaze().getHigh());
		Graphics g=this.getGraphics();
		g.clearRect((int)(learner.getPosition().getX()*stepX),(int)(learner.getPosition().getY()*stepY),stepX,stepY);
		//g.fillOval((int)(learner.getPosition().getX()*10+2),(int)(learner.getPosition().getY()*10+2),5,5);

	}
	
	public void drawLearner(){
                int stepX = (int)(_viewHigh/_application.getMaze().getLength());
                int stepY = (int)(_viewWidth/_application.getMaze().getHigh());
		SarsaSituatedAgent learner = _application.getLearner();
		Graphics g=this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillOval((int)(learner.getPosition().getX()*stepX),(int)(learner.getPosition().getY()*stepY+2),stepX-2,stepY-2);
                //for(int i =0; i<1000000;i++){};
		
	}

        public void drawMe(SarsaSituatedAgent learner){
                int stepX = (int)(_viewHigh/_application.getMaze().getLength());
                int stepY = (int)(_viewWidth/_application.getMaze().getHigh());
		Graphics g=this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillOval((int)(learner.getPosition().getX()*stepX),(int)(learner.getPosition().getY()*stepY+2),stepX-2,stepY-2);
                //for(int i =0; i<1000000;i++){};
        }

	public void setApplication(Application a){
		_application = a;
	}
}
