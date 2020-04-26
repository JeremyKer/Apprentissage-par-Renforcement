/*
 * Application.java
 * Last release on June 2008
 * Created on 6 juin 2005, 17:57
 * Revised on September 2011
 */

package learning;

import ihm.*;
import java.util.*;
import maze.*;


/**
 *
 * @author De Loor
 */
public class Application {
    
        
    /** Creates a new instance of Application */
    public Application() {
        /** creation du labyrinthe  */
        _maze = new Maze();
        _timeOut=0;
        _timer = new Timer();
        
        /** création de l'ihm   */
        _view = new LearningInMazeView();
        _view.setApplication(this);
        _view.setVisible(true);
        _view.setSize(800,800);


        _agentTab = new ArrayList();
        _simulationStatut = "stop";

    }
    
    /**
     * Méthode de lancement de l'application, elle se contente de 
     * créer une application ..
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Application application = new Application();
        application.run();
    }
    
    public Maze getMaze(){return(_maze);}
    public void setTimeOut(long x){_timeOut = x;}
    public SarsaSituatedAgent getLearner(){return(_learner);}
    /**
     * envoie le statut de la simulation
     * Le statut de la simuation peut être "run", "stop" ou "pause"
     * il permet de savoir si l'on fait tourner l'algorithme ou non
     * théoriquement, c'est l'ihm qui le fixe
     */
    public String getSimulationStatut(){return _simulationStatut;}
    public LearningInMazeView getLearningInMazeView(){return _view;}
    
    public void addAgent(String name){
        SarsaSituatedAgent agent = new SarsaSituatedAgent(this,_view,_maze,name,"perception.PositionLearnerPerception");
        _agentTab.add((SarsaSituatedAgent)agent); 
        for(int i=0;i<_agentTab.size();i++)
            if(_agentTab.get(i) instanceof SarsaSituatedAgent) ((SarsaSituatedAgent)_agentTab.get(i)).setUnSelected();
        agent.setSelected();
        agent.startLife();
        _learner = agent;
        _view.setNewInformations(_learner);
    }
    
    public void stopSimulation(){
        _simulationStatut="stop";
        for(int i=0;i<_agentTab.size();i++){
            ((SarsaSituatedAgent)_agentTab.get(i)).setCompteur(0);
            ((SarsaSituatedAgent)_agentTab.get(i)).setNbEpisode(0);  
        }
    }
    
    public void startSimulation(){
        if(_simulationStatut.equals("stop")){
            for(int i=0;i<_agentTab.size();i++)
            {
                try{
                SarsaSituatedAgent sarsa = (SarsaSituatedAgent)(_agentTab.get(i));
                sarsa.clearTrace();
                sarsa.clearMemory();
                }
                catch(Exception e){ System.out.println(e.getMessage()); }
                _view.drawGraph();
            }
        }
        _simulationStatut="run";
    }
    
    public void pauseSimulation(){
        _simulationStatut="pause";

    } 
    
    public void run(){
        
        while(true){
            //System.out.println(_simulationStatut);
            if(_simulationStatut.equals("run"))
            {
                //System.out.println("run");
                for(int i=0;i<_agentTab.size();i++){
                    //System.out.println(i);
                ((SarsaSituatedAgent)_agentTab.get(i)).oneStep();
                }
                try{ Thread.sleep(_timeOut); }
                catch(Exception e) {System.out.println("Time Out problem");}
                //_timer.wait(_timeOut);

            }
            else
            {
                try{ Thread.sleep(_timeOut); }
                catch(Exception e) {System.out.println("Time Out problem");}
                //System.out.println("noRun");
            }
    }
}
   public void reset(){
   
        _simulationStatut = "stop";
       
        /** creation du labyrinthe  */
        _maze = new Maze();
        _timeOut=0;
        _timer = new Timer();
        
        /** création de l'ihm   */
        

        _agentTab = new ArrayList();
       
        _view.setApplication(this);
         
        _view.drawGraph();
         
        _view.setVisible(true);
        _view.setSize(800,800);
   }
   
   public void changeSelectedAgent(String name){
     
        for(int i=0;i<_agentTab.size();i++)
            if(_agentTab.get(i) instanceof SarsaSituatedAgent) ((SarsaSituatedAgent)_agentTab.get(i)).setUnSelected();
        
        for(int i=0;i<_agentTab.size();i++)
            if((_agentTab.get(i) instanceof SarsaSituatedAgent) && (((SarsaSituatedAgent)_agentTab.get(i)).getName().equals(name)))
             {
                _learner = ((SarsaSituatedAgent)_agentTab.get(i));
                _learner.setSelected();
                _view.setNewInformations(_learner);
            }
    }
    public void changeSimulationStatut(String statut){
        _simulationStatut=statut;
    }
    
    public ArrayList getAgentTab() {
        return _agentTab;
    }
    
    public void rewardChange(){
        _maze.rewardChange();
        _view.repaint();
        for(int i=0;i<_agentTab.size();i++) {
            if((_agentTab.get(i) instanceof SarsaSituatedAgent))
             {
                ((SarsaSituatedAgent)_agentTab.get(i)).clearTrace();
            }
        }
    }
    
    private Maze _maze;
    private LearningInMazeView _view;
    private ArrayList _agentTab;
    private SarsaSituatedAgent _learner; 
    private String _simulationStatut;
    long _timeOut;
    Timer _timer;
}


