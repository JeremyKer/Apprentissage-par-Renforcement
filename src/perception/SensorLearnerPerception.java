package perception;

import java.awt.Point;
//import maze.*;

public class SensorLearnerPerception extends Perception {

    private Point _capteur1; // gauche
    private Point _capteur2; // droite
    // private maze.SituatedAgent _myAgent;

    public SensorLearnerPerception(maze.SituatedAgent a) {
        _capteur1 = new Point();
        _capteur1 = a.getPosition();
        _capteur1.setLocation(_capteur1.getX()-1, _capteur1.getY());

        _capteur2 = new Point();
        _capteur2 = a.getPosition();
        _capteur2.setLocation(_capteur2.getX()+1, _capteur2.getY());
        setAgent(a);

    }

    public SensorLearnerPerception() {

    }

    public SensorLearnerPerception(SensorLearnerPerception p) {
        _capteur1 = new Point(p._capteur1);
        _capteur2 = new Point(p._capteur2);
        setAgent(p.getAgent());
    }

    public SensorLearnerPerception copy() {
        SensorLearnerPerception plp = new SensorLearnerPerception();
        plp._capteur1 = _capteur1;
        plp._capteur2 = _capteur2;
        plp.setAgent(getAgent());
        return plp;
    }

    public void updatePerception() {
        _capteur1 = getAgent().getPosition();
        _capteur1.setLocation(_capteur1.getX()-1, _capteur1.getY());
        _capteur2 = getAgent().getPosition();
        _capteur2.setLocation(_capteur2.getX()+1, _capteur2.getY());

    }

    public boolean equals(Perception s) {
        return ((((SensorLearnerPerception)(s))._capteur1.getX()==_capteur1.getX()&&(((SensorLearnerPerception)(s))._capteur1.getY()==_capteur1.getY())))
             &&((((SensorLearnerPerception)(s))._capteur2.getX()==_capteur2.getX()&&(((SensorLearnerPerception)(s))._capteur2.getY()==_capteur2.getY())));
    }

    public Point getCapteur1(){
        return _capteur1;
    }
    public Point getCapteur2(){
        return _capteur2;
    }

    public void display(){
        System.out.println("Etat Gauche: "+String.valueOf(_capteur1.getX())+" "+String.valueOf(_capteur1.getY()));
        System.out.println("Etat Droite: "+String.valueOf(_capteur2.getX())+" "+String.valueOf(_capteur2.getY()));

    }
    
}
