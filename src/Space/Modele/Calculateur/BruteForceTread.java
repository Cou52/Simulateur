/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele.Calculateur;
import Space.Modele.Body;
import Space.Modele.Calculateur.BruteForceListener;
import java.util.ArrayList;
/**
 *
 * @author cou8
 */
public class BruteForceTread implements Runnable{
    public Body bodies[];
     public Body buffer[];
     public int calculDe = 0;
     public int calculA = 0;
     public int currentTime = 0;
     public double nextFrame = 1.00f;
     public float deltaTime = 1.00f;
     private boolean roule = false;
    
     
     public ArrayList<BruteForceListener> listListenner = new ArrayList<BruteForceListener>();;
     public  BruteForceTread(int pcalculDe , int pcalculA ,Body pbodies[])
     { 
         calculDe = pcalculDe;
         calculA = pcalculA;
         bodies = pbodies;
         
     }    
     /**
      * A finir
      * @param pcalculDe
      * @param pcalculA
      * @param pbodies
      * @param deltaTime 
      */
     public  BruteForceTread(int pcalculDe , int pcalculA ,Body pbodies[] ,float pdeltaTime)
     { 
         calculDe = pcalculDe;
         calculA = pcalculA;
         bodies = pbodies;
         deltaTime = pdeltaTime;
         
     } 
     public void addBruteForceListener(BruteForceListener listener)
     {
         listListenner.add(listener);
     }
     
     public void finishN2()
     {  
           for(BruteForceListener listener : listListenner) {
                listener.Terminer(this);
            }
     }   
     public void setRoule(boolean proule)
      {  
          roule =proule;
          System.out.println(roule);
      }  
     
       public boolean getRoule()
      {  
         return  roule ;
      } 
     
     @Override
     public void run()
     {
       
         
         
                for (int i = calculDe; i <= calculA; i++) 
                {
                     bodies[i].resetForce();
                    //Notice-2 loops-->N^2 complexity
                     for (int j = 0; j < bodies.length; j++) 
                     {
                        if (i != j) bodies[i].addForce(bodies[j]);
                     }
                }
                
                  finishN2();
                  //Attendre les instruction du tread principal(BruteForceCalculateur avant de continuer)
                  roule =false;
            }
       

    }
   
  
  

