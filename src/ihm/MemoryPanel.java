package ihm;
import learning.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import maze.*;
import perception.*;


/**

* *************************************
*/
public class MemoryPanel extends javax.swing.JPanel {
	Application _application;
        private static int _viewHigh=180;
        private static int _viewWidth=120;
	
	public MemoryPanel() {
		super();
	
	}
	public void setApplication(Application app){
		_application=app;
	}

        public void paint(Graphics g){
            super.paint(g);
            drawMemory(g);
        }

        public void drawMemory(Graphics g){ 
            if(_application.getLearner()!=null){
                int stepX = (int)(_viewWidth/_application.getMaze().getLength());
                int stepY = (int)(_viewHigh/_application.getMaze().getHigh());

                for(int i =0; i<_application.getLearner().getMemory().size();i++){
               // System.out.println("Appel de drawMemory de MemoryPanel : ");
               // System.out.println("_application.getLearner() ---> " + _application.getLearner().getClass().getName());
               // System.out.println("_application.getLearner().getMemory().get(i) ---> " + _application.getLearner().getMemory().get(i).getClass().getName());
                    Perception thePerception =((MemoryPattern)(_application.getLearner().getMemory().get(i))).getPerception();
                    //System.out.println(thePerception.getClass().getName());
                 //   System.out.println("thePerception.getClass().getName() ---> " + thePerception.getClass().getName());
                    
                    if(thePerception.getClass().getName()=="perception.SensorLearnerPerception"){
                        //PositionLearnerPerception theState = (PositionLearnerPerception) thePerception;
                        SensorLearnerPerception theState = (SensorLearnerPerception) thePerception;
                        float bestValue = _application.getLearner().getBestValueForState(theState);	
                        float colorValue = bestValue/_application.getLearner().getBestQuality();
                        if(colorValue<0) colorValue=0;
                        if(colorValue>1) colorValue=1;
                        g.setColor(new Color(colorValue,0,0));
                        // PositionLearnerPerception
                        //g.fill3DRect((int)(theState.getPosition().x*stepX),(int)(theState.getPosition().getY()*stepX),stepX-2,stepY-2,true);
                        // SensorLearnerPerception
                        g.fill3DRect((int)(theState.getCapteur1().x*stepX),(int)(theState.getCapteur1().getY()*stepX),stepX-2,stepY-2,true);
                        g.fill3DRect((int)(theState.getCapteur2().x*stepX),(int)(theState.getCapteur2().getY()*stepX),stepX-2,stepY-2,true);
                    }
                    
                    if(thePerception.getClass().getName()=="perception.PositionLearnerPerception"){
                        PositionLearnerPerception theState = (PositionLearnerPerception) thePerception;
                        //SensorLearnerPerception theState = (SensorLearnerPerception) thePerception;
                        float bestValue = _application.getLearner().getBestValueForState(theState);	
                        float colorValue = bestValue/_application.getLearner().getBestQuality();
                        if(colorValue<0) colorValue=0;
                        if(colorValue>1) colorValue=1;
                        g.setColor(new Color(colorValue,0,0));
                        // PositionLearnerPerception
                        g.fill3DRect((int)(theState.getPosition().x*stepX),(int)(theState.getPosition().getY()*stepX),stepX-2,stepY-2,true);
                        // SensorLearnerPerception
                        //g.fill3DRect((int)(theState.getCapteur1().x*stepX),(int)(theState.getCapteur1().getY()*stepX),stepX-2,stepY-2,true);
                        //g.fill3DRect((int)(theState.getCapteur2().x*stepX),(int)(theState.getCapteur2().getY()*stepX),stepX-2,stepY-2,true);

                    }
                }
            }
        }

        
}


