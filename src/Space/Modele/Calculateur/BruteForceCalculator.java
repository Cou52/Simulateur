/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele.Calculateur;

import Space.Modele.Body;
import Space.Modele.bufferBody;
import Space.Modele.OpenCL.JOCLTest;
import com.jme3.math.Vector3f;

/**
 *
 * @author cou8
 */
public class BruteForceCalculator  implements Runnable, BruteForceListener{
    public bufferBody memoire ;
    public bufferBody memoireDernierCalcule ;
    public boolean roule = false;
    public Body bodies[];
    public  JOCLTest openCl[];
    public int maxBuffer = 10;
    public int nbthreadCl = 1;
    public int nbThreadFinish = 0;
    public  boolean threadFini = false;
    public float dt = 0.016f;
    
    public void Terminer(BruteForceTread currentTread)
    {
        
                
    }        
   
    public synchronized  void TerminerOpenCl(JOCLTest currentTread)
    {
     
        nbThreadFinish++;
        if (nbThreadFinish == nbthreadCl)
        { 
            nbThreadFinish = 0;
            threadFini = true;
        }   
     
    }
    
    public  BruteForceCalculator(int nombreParticule)
    {        
        bodies = new Body[nombreParticule];
    }
    public void init()
    {
     
          
        Body buffer[] = new Body[bodies.length];
        bodies[0] = new Body(new Vector3f (0, 1 ,0) ,new Vector3f (0, 0 ,0) , 4 * 10e12f);
        buffer[0] = new Body(new Vector3f (0, 1 ,0) ,new Vector3f (0, 0 ,0) , 4 * 10e12f);
         int  iterator = 0;
           for (int i = 0 ;i < 200 ; i++)
           {  
                for (int j = 0 ;j < 50 ; j++)
                { 
                     for (int z = 0 ;z < 10 ; z++)
                    { 
                       
                        iterator++;
                        if (iterator < bodies.length)
                        {
                            bodies[iterator] = new Body(new Vector3f (i * 4, -2 * j * 4 , z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                            buffer[iterator] = new Body(new Vector3f (i * 4, -2 * j * 4 , z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        }
                        //System.out.println(iterator);
                        //System.out.println(bodies[iterator].position);
                    }
                }
           }   
           
           
            for (int i = 0 ;i < 200 ; i++)
           {  
                for (int j = 0 ;j < 50 ; j++)
                { 
                     for (int z = 0 ;z < 10 ; z++)
                    { 
                         iterator++;
                        if (iterator < bodies.length)
                       {
                            bodies[iterator] = new Body(new Vector3f (-i * 4 -20, 2 * j * 4 , -z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                            buffer[iterator] = new Body(new Vector3f (-i * 4  -20, 2 * j * 4 , -z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        //System.out.println(iterator);
                        //System.out.println(bodies[iterator].position);
                        }
                    }
                }
           }
           memoire = new bufferBody(buffer);
           memoireDernierCalcule = memoire;
           InitialiserThreadOpenCl();
       }
      
             
    
    public void run()
    { 
         System.out.println("3");
        partirLesThread();
        while (roule != false)
        {    
               if (memoire.getNumberBufferAfterHim() < maxBuffer) //System.out.println(memoire.getNumberBufferAfterHim());
                { 
                    synchronized(this)
                    {
                         if (threadFini)
                         {    
                             threadFini = false;


                             miseAjour();
                             partirLesThread();

                         }
                    }
                }  
        }
         System.out.println("die");
    }   
    public void miseAjour()
    { 
           Body buffer[] = new Body[bodies.length];
         
            for (int i = 0; i < bodies.length; i++) 
            {
                
                  bodies[i].update(dt);
                  buffer[i] = new Body(new Vector3f(bodies[i].position) ,new Vector3f(bodies[i].speed) ,  bodies[i].mass);
                  bodies[i].resetForce();
            }
             bufferBody frame = new bufferBody(buffer);
             memoireDernierCalcule.setNextBuffer(frame);
             memoireDernierCalcule = frame;
            
    }      
   public void InitialiserThreadOpenCl()
   {  
        openCl = new JOCLTest[nbthreadCl];
        for (int i = 0 ;i < nbthreadCl ; i++) 
        {  
            openCl[i] = new JOCLTest(i ,nbthreadCl );
            openCl[i].addBruteForceListener(this);
        }  
   } 
   
   public void partirLesThread()
   {
       for (int i = 0 ;i < nbthreadCl ; i++) 
      {  
          openCl[i].bodies = bodies;
          Thread t = new Thread(openCl[i]);
          t.start();
      }  
   }       
    
   public void requestNewFrame()
   {   
 
       if (memoire.getNextBuffer() != null)
         {
              memoire = memoire.getNextBuffer();
              
         }
   }     
   
   public bufferBody frameActuel()
    {  
        return memoire; 
    }
}
