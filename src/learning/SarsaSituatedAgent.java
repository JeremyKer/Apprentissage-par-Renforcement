/*
 * Created on 4 mai 2005
 * Revised on September 2011
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package learning;

import ihm.*;
import maze.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import maze.SituatedAgent;
import perception.*;

/**
 * @author deloor
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SarsaSituatedAgent extends SituatedAgent {
    private String _perceptionClass;
    private ArrayList _memory;
    private float _bestQuality = 10;
    private Perception _S;
    private Perception _SP;
    private String _AP;
    private float _alpha = (float) 0.2;
    private float _lambda = (float) 0.2;
    private boolean _autoEpsilon = true;
    private float _epsilon = (float) 0.5;
    private float _slow = 50;

    private int _nbStep = 1000;
    private int _nbEpisodeMax = 200;
    private int _nbStepTotal = 0;
    private int _nbEpisode = 0;

    float _moyennePonderee;
    float _moyenneTrace;
    float _ancienneMoyenne = _moyenneTrace;
    int _action;
    int _compteurByEpisode = 0;
    int _nStepTemp = 0;

    private ArrayList _trace;

    // thread
    private Thread _myThread;
    private boolean running;

    private LearningInMazeView _view;
    private boolean selected;
    private Application _application;

    private String _name; // son nom, id
    private boolean _onAppli = false; // visible sur l'interface

    public SarsaSituatedAgent(Application application, LearningInMazeView viewer, Maze m, String name,
            String perceptionClass) {
        super(m);
        _memory = new ArrayList();
        _trace = new ArrayList();
        _application = application;
        _name = name;
        _view = viewer;
        _perceptionClass = perceptionClass;

    }

    public void init() {
        super.init();
        Class<?> classPerception = null;
        try {
            classPerception = Class.forName(_perceptionClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            _S = (Perception) (classPerception.newInstance());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {

        }
        ((Perception) _S).setAgent((SituatedAgent) this);
        ((Perception) _S).updatePerception();

    }

    // public void startLife(){
    // MyThread = new Thread(this);
    // MyThread.start();
    // running= true;
    // }

    public void startLife() {
        Point p = new Point();
        p = _maze.findAPositionFreeRandomly();
        Random generator = new Random();
        _compteurByEpisode = 0;
        _nStepTemp = 0;
        _nbEpisode = 0;
        init();
    }

    public void oneStep() {
        if (_nStepTemp < _nbStep) {
            // if(isSelected())
            _view.eraseMe(this);
            if (_application.getSimulationStatut() == "run") {
                sarsaAlgorithmeStep();
                _nStepTemp++;
                _nbStepTotal++;
                // Reward de 10 == recompense == gagné == changement de position
                if (_R == 10) {
                    Point p = new Point(_nbEpisode, _nStepTemp);
                    _trace.add(p);
                    linearizeTrace(2000);
                    _position = _maze.findAPositionFreeRandomly();
                    // >> Auto Epsilon <<
                    if (this.getAutoEpsilon())
                        this.computeAutoEpsilon();
                    // _epsilon= generator.nextFloat();
                    _nStepTemp = 0;
                    init();
                    _nbEpisode++;
                    _view.drawGraph();
                    _R = 0;
                    // Thread.yield();
                }
            }
            if (isSelected()) {
                _view.setNewInformations(this);
                _view.drawLearner();
                _view.setNbStep(_nbStepTotal);
            }
        } else {
            Point p = new Point(_nbEpisode++, _nStepTemp);
            _nStepTemp = 0;
            _trace.add(p);
            linearizeTrace(2000);
            _position = _maze.findAPositionFreeRandomly();
            _nbEpisode++;
            _view.drawGraph();
            if (isSelected()) {
                _view.setNbEpisode(_nbEpisode);
                _view.eraseLearner();
            }
            // Thread.yield();
        }
    }

    private void computeAutoEpsilon() {
        int N = _trace.size();
        int _nbReward = 0;
        // On récupère le nb de reward dans la trace
        for (int i = 0; i < N - 1; i++) {
            Point p = (Point) _trace.get(i);
            if (p.getY() < 1000) {
                _nbReward++;
            }
        }
        System.out.println("Nombre d'objectif atteint dans trace : " + _nbReward);
        // On calcule la moyenne des points contenus dans la trace
        _moyenneTrace = (float) ((float) _nbReward / (float) N);
        System.out.println("Moyenne Succès : " + _moyenneTrace);
        float epsilon = this.getEpsilon();
        System.out.println("Epsilon : " + epsilon);
        float coeff = 0.005f;
        if (_moyenneTrace > _ancienneMoyenne) {
            _ancienneMoyenne = _moyenneTrace;
            // On évite d'avoir un epsilon négatif...
            if (epsilon > 0)
            {
                epsilon -= coeff;
            }
        } else if (_moyenneTrace < _ancienneMoyenne) {
            _ancienneMoyenne = _moyenneTrace;
            // On évite d'avoir un epsilon suppérieur à 0.6 car sinon ca part... 
            if (epsilon < 0.6)
            {
                epsilon += coeff;
            }
        }
        if (_moyenneTrace<0.5 & epsilon<0.1){
            epsilon = 0.3f;
        }
        this.setEpsilon(epsilon);
        System.out.println("");
        /*
         * if (_nbEpisode > 1) { _moyennePonderee = (float) ((float) _nbSuccess /
         * (float) _nbEpisode); // Nombre de tentatives
         * System.out.println("Nombre de tentatives : " + _nbEpisode);
         * System.out.println("Moyenne Succès : " + _moyennePonderee); if
         * (_moyennePonderee > 0.2) { float ep = this.getEpsilon();
         * System.out.println("Epsilon : " + ep); if (ep > 0)// On évite d'avoir un
         * epsilon négatif... { ep = ep - 0.01*(1-epsilon);// On décrémente un pôtit peu
         * epsilon } this.setEpsilon(ep); } else// Sinon 50/50 Exploration {
         * this.setEpsilon(0.5f); } System.out.println(""); }
         */
    }

    /**
     * Pour ne pas saturer la mémoire, cette méthode réduit lengthnombre de points
     * de la trace d'apprentissage. Pour cela elle effectue une moyenne des points
     * qu'elle supprime et les remplace par un nouveau point caractérisant cette
     * moyenne
     */

    public void linearizeTrace2(int sizeMax) {
        if (this._trace.size() > 2 * sizeMax) {
            ArrayList newTrace = new ArrayList();
            double nextTrace = ((Point) _trace.get(_trace.size() - 1)).getX();
            double step = nextTrace / sizeMax;
            int cpt = 0;
            double nextStep = step;
            double valTemp = 0;
            for (int i = 0; i < _trace.size(); i++) {
                cpt++;
                if (((Point) _trace.get(i)).getX() <= nextStep) {
                    valTemp += ((Point) _trace.get(i)).getY();
                } else {
                    valTemp = valTemp / cpt;
                    cpt = 0;
                    newTrace.add(new Point((int) nextStep, (int) valTemp));
                    valTemp = ((Point) _trace.get(i)).getY();
                    nextStep += step;
                }
            }
            _trace = newTrace;
        }
    }

    public void linearizeTrace(int sizeMax) {
        if (this._trace.size() > 2 * sizeMax) {
            ArrayList newTrace = new ArrayList();
            for (int i = (_trace.size() / 2); i < _trace.size(); i++) {
                newTrace.add(_trace.get(i));
            }
            _trace = newTrace;
        }
    }

    /**
     * la "bestQuality" est la valeur la plus élevée de qualité trouvée en mémoire
     * _memory
     */
    public float getBestQuality() {
        return _bestQuality;
    }

    /**
     * Cette méthode retourne la qualité la plus élevée pour un état donné Elle
     * recherche toutes les qualités associées à cet état (généralement autant qu'il
     * y a d'actions ...
     */

    public float getBestValueForState(Perception aState) {
        float value = -1000;
        for (int i = 0; i < _memory.size(); i++) {
            if (((MemoryPattern) (_memory.get(i))).getPerception().equals(aState))
                if ((((MemoryPattern) (_memory.get(i))).getQualitie()) > value)
                    value = ((MemoryPattern) (_memory.get(i))).getQualitie();
        }
        return value;
    }

    public ArrayList getMemory() {
        return _memory;
    }

    public void clearMemory() {
        _memory.clear();
    }

    public void setAutoEpsilon(boolean value) {
        _autoEpsilon = value;
    }

    public boolean getAutoEpsilon() {
        return _autoEpsilon;
    }

    public void setEpsilon(float value) {
        _epsilon = value;
        if (_application.getLearner() == this)
            _application.getLearningInMazeView().setNewInformations(this);
    }

    public float getEpsilon() {
        return _epsilon;
    }

    public void setAlpha(float value) {
        _alpha = value;
    }

    public float getAlpha() {
        return _alpha;
    }

    public void setLambda(float value) {
        _lambda = value;
    }

    public float getLambda() {
        return _lambda;
    }

    public void setCompteur(int value) {
        _nbStepTotal = value;
    }

    public int getCompteur() {
        return _nbStepTotal;
    }

    public void setNbEpisode(int value) {
        _nbEpisode = value;
    }

    public int getNbEpisode() {
        return _nbEpisode;
    }

    public void setSlow(long value) {
        _slow = value;
    }

    public float getSlow() {
        return _slow;
    }

    public ArrayList getTrace() {
        return _trace;
    }

    public void clearTrace() {
        _trace.clear();
    }

    public void createNewMemoryWith(Perception perception) {
        String action;
        // System.out.println("Appel de createNewMemory avec un argument de type " +
        // perception.getClass().getName());

        Perception copyState = perception.copy();

        for (int i = 0; i < _possibleActions.size(); i++) {
            action = (String) _possibleActions.get(i);
            float quality = _randomGenerator.nextFloat()*10;
            MemoryPattern mp = new MemoryPattern(perception, quality, action);
            _memory.add(mp);
        }
    }

    public void displayMemory() {

        for (int i = 0; i < this._memory.size(); i++) {
            // System.out.println("Memory Pattern : ");
            MemoryPattern mp = (MemoryPattern) _memory.get(i);
            String s;
            s = mp.getPerception().getClass().getName();
            // s = String.valueOf(((mp.getPerception().getPosition().x)));
            // s+=" - ";
            // s+= String.valueOf((((mp.getPerception().getPosition().y))));
            s += "  : ";
            s += String.valueOf(mp.getAction());
            s += " -->  ";
            s += String.valueOf(mp.getQualitie());
            // System.out.println(s);
        }

    }

    // init pour d�marrage

    public boolean existeAMemorieWith(Perception state) {
        for (int i = 0; i < this._memory.size(); i++) {
            Perception lns = ((MemoryPattern) (_memory.get(i))).getPerception();
            if (lns.equals(state))
                return true;

        }
        return false;
    }

    public void learn() {
        float QSA = getQSA(_S, _A);
        float QSAPrime = getQSA(_SP, _AP);
        float newQSA = QSA + _alpha * (_R + _lambda * QSAPrime - QSA);
        if (newQSA > _bestQuality) {
            _bestQuality = newQSA;
        }
        setQSA(_S, _A, newQSA);
        Class<?> classPerception = null;
        try {
            classPerception = Class.forName(_perceptionClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            _S = (Perception) (classPerception.newInstance());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        _S = _SP.copy();
        _A = new String(_AP);
    }

    public void sarsaAlgorithmeStep() {
        runAction();
        chooseAPAction();
        learn();
    }

    private void printSarsaState() {
        System.out.println("Sarsa state");
        _S.display();
        System.out.println(_A);
        _SP.display();
        System.out.println(_AP);

    }

    private void setQSA(Perception state, String action, float value) {
        for (int i = 0; i < _memory.size(); i++) {
            MemoryPattern mp = (MemoryPattern) _memory.get(i);
            if (mp.getPerception().equals(state) && mp.getAction().equals(action)) {
                mp.setQualitie(value);
                return;
            }
        }
    }

    private float getQSA(Perception perception, String action) {
        float value = 0;
        for (int i = 0; i < getMemory().size(); i++) {
            MemoryPattern mp = (MemoryPattern) getMemory().get(i);
            if (mp.getPerception().equals(perception) && mp.getAction().equals(action)) {
                value = mp.getQualitie();
            }
        }
        return value;
    }

    public void chooseAPAction() {
        // Exploration ou exploitation

        float choose = _randomGenerator.nextFloat();
        if (choose < _epsilon) {
            chooseAPActionRandomly();
            // System.out.println("Random Action ");
        } // exploration

        else {
            chooseAPGreedyAction();
            // System.out.print("Greedy Action ");
        } // exploitation ;
          // System.out.println("Sarsa State : ");
          // printSarsaState();
    }

    public void chooseAPActionRandomly() {

        int action = _randomGenerator.nextInt(_possibleActions.size());
        _AP = (String) _possibleActions.get(action);
        // System.out.println("Random = "+String.valueOf(action));
    }

    public void chooseAPGreedyAction() {
        float q = -100;
        for (int i = 0; i < _memory.size(); i++) {
            MemoryPattern mp = (MemoryPattern) _memory.get(i);
            if (mp.getPerception().equals(_SP)) {
                if (q < mp.getQualitie()) {
                    q = mp.getQualitie();
                    _AP = mp.getAction();
                }
            }

        }
    }

    public void runAction() {
        super.runAction();
        Class<?> classPerception = null;
        try {
            classPerception = Class.forName(_perceptionClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            _SP = (Perception) (classPerception.newInstance());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {

        }
        ((Perception) _SP).setAgent((SituatedAgent) this);
        ((Perception) _SP).updatePerception();

        if (!(existeAMemorieWith(_SP))) {
            createNewMemoryWith(_SP);
        }
    }

    public String getName() {
        return _name;
    }

    public void setSelected() {
        selected = true;
    }

    public void setUnSelected() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
}
