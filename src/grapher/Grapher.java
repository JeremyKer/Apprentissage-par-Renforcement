/*
 * Grapher.java
 *
 * Created on 2 juin 2005, 13:49
 */

package grapher;

//import maze.*;
import learning.*;

import org.jfree.data.*;


import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*; 
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.chart.ChartPanel.*;
import org.jfree.chart.*;
import org.jfree.util.*;

import java.util.*;
import java.lang.Object;

/**
 *
 * 
 * Le Grapher exploite la librairie jfreeChart pour representer l'evolution de l'apprentissage.
 * Actuellement elle se base sur le tableau _trace des SarsaSituatedAgent pour effectuer son trace 
 */
public class Grapher {
    
    /** Cree un Grapher */
    public Grapher() {

        courbe1 = new XYSeries("COURBE1");
        courbe2 = new XYSeries("COURBE2");
        
        for(int i=1; i<10;i++)
        {
           //courbe1.add(i, i*i -2*i - 10) ;
           //courbe2.add(i,i*5 );
            }
        
        xyDataset = new XYSeriesCollection(courbe1);
        xyDataset.addSeries(courbe2);
 
       graph = ChartFactory.createXYLineChart
                                    ("Apprentissage",  // Title
                                    "Nb Buts atteints",           // X-Axis label
                                    "Nb Pas/But",           // Y-Axis label
                                    (XYDataset)xyDataset,          // Dataset
                                   PlotOrientation.VERTICAL,         // Orientation
                                   true,               // Show legend
                                    true,              // Tools Tips
                                    false               // Urls) ;
                );
        
       // pieChart ou graph
      
      cPanel = new ChartPanel(graph);
    }
    
    
    /**
     * Permet de re-afficher les courbes associees au Grapher
     * Actuellement, chaque SarsaSituatedAgent de l'_application (classe Application)
     * voit sa _trace affichee.
     */
    public void RefreshGraph(){
        ArrayList AgentTab = _application.getAgentTab();
        xyDataset = new XYSeriesCollection();
        for(int i=0;i<AgentTab.size();i++)
        {
          //XYSeries courbe = new XYSeries(AgentTab.get(i).);
         SarsaSituatedAgent agent = (SarsaSituatedAgent)(_application.getAgentTab().get(i));
         XYSeries courbe = new XYSeries(agent.getName());
          for(int j=0; j<(agent.getTrace().size());j++){
              courbe.add(((Point)(agent.getTrace().get(j))).getX(),((Point)(agent.getTrace().get(j))).getY());
          }
          xyDataset.addSeries(courbe);
        }
        graph = ChartFactory.createXYLineChart
                                    ("Apprentissage",  // Title
                                    "Nb Buts atteints",   // X-Axis label
                                    "Nb Pas/But",           // Y-Axis label
                                    (XYDataset)xyDataset,          // Dataset
                                    PlotOrientation.VERTICAL,         // Orientation
                                    true,               // Show legend
                                    true,              // Tools Tips
                                    false               // Urls) ;
                );
      
        ((ChartPanel) cPanel).setChart(graph);
        ((ChartPanel) cPanel).repaint(); // mip
        
        //DEBUG System.out.println("Rafraichissement Graphe");
        //DEBUG System.out.flush();
    }
    
    /**
     * Affecte une application au grapher
     * @param ap est l'instance qui sera affectee.
     **/
    public void setApplication(Application ap){
        _application = ap;
    }
    
    /**
     * Panel dessinant le graphe, destine a l'IHM
     * @see ihm.LearningInMazeView
     **/
     public ChartPanel cPanel;
     public JFreeChart graph = null;
     private XYSeries courbe1 ;
     private XYSeries courbe2 ;
     private XYSeriesCollection xyDataset = null;
     private Application _application;
}
