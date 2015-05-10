/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele.Calculateur;

/**
 *
 * @author cou8
 */

import Space.Modele.Body;
import Space.Modele.BodyFractal;
import Space.Modele.bufferBody;
import com.jme3.math.Vector3f;


public class fractal_triangle_mainCalculator implements Runnable 
{
    public static int treadOpenClTriangle = 2048;
    public int nombrePoint;
    public int pointRendu;
    public Body ListePoint[];
    public int size;
    public Vector3f point1;
    public Vector3f point2;
    public Vector3f point3;
    public Vector3f point4;  
    public Vector3f PointSelection;
    public bufferBody memoire ;
    public bufferBody memoireDernierCalcule ;
    
    
    public fractal_triangle_mainCalculator(int pnombrepoint , Vector3f positionPoint0 , int pSize)
    {
        if (3  <= pnombrepoint &&   1 <= pSize)
        {    
            nombrePoint = pnombrepoint;
            ListePoint = new Body[pnombrepoint];
            if (positionPoint0 != null)
            {
                size = pSize;
                point1 = positionPoint0;
                initialise4Sommet();
            }
        }
        else
         { 
            // throw new Exception("Nombre de point ou taille invalide");
         }  
    }
    private void initialise4Sommet()
    {    
        point2 = new Vector3f();
        point3 = new Vector3f();
        point4 = new Vector3f();
        
        point2.x = point1.x + size;
        point2.y = point1.y;
        point2.z = point1.z;
        
        point3.x = point1.x + (size * 0.5f);
        point3.y = (float)(point1.y + (size * Math.sqrt(3)/2));
        point3.z = point1.x;
        
        point4.x = point1.x + (size * 0.5f);
        point4.y = point1.y + (float)(size * Math.sqrt(3) /6);
        point4.z = point1.z + (float)(size * Math.sqrt(6)/3);
        
        ListePoint[0] = new Body(point1);
        ListePoint[1] = new Body(point2);
        ListePoint[2] = new Body(point3);
        ListePoint[3] = new Body(point4);
        PointSelection = new Vector3f(0 , 0 ,0);
        PointSelection.x = (point1.x + point2.x) / 2;
        PointSelection.y = (point1.y + point2.y)/ 2;
        PointSelection.z = (point1.z + point2.z)/ 2;
        pointRendu = 4;
        ListePoint[4] = new Body(new Vector3f(PointSelection.x , PointSelection.y , PointSelection.z));
       
    }    
    public void run()
    {    
        for (int i = pointRendu ; i < nombrePoint ; i++)
        {
            
            Vector3f p = new Vector3f(PointSelection.x , PointSelection.y ,PointSelection.z);
            Vector3f nextPoint = new Vector3f();
            int Selection = (int)(Math.random() * (4 - 0) + 0);
            Vector3f SommetSelectionner =  ListePoint[Selection].position;
            
      
            nextPoint.x = (p.x + SommetSelectionner.x) / 2;
            nextPoint.y = (p.y + SommetSelectionner.y)/ 2;
            nextPoint.z = (p.z + SommetSelectionner.z)/ 2;
            PointSelection = new Vector3f(nextPoint.x , nextPoint.y , nextPoint.z);
             ListePoint[i] = new BodyFractal(new Vector3f(PointSelection.x , PointSelection.y , PointSelection.z),Selection );
         } 
        
         Body buffer[] = new Body[nombrePoint];
          for (int i = 0 ;i < nombrePoint ; i++)
          {  
             buffer[i] = new Body(new Vector3f(ListePoint[i].position.x, ListePoint[i].position.y , ListePoint[i].position.z));
          }
          memoire = new bufferBody( buffer);
    }   
    

    //Test 
    public static void main(String args[])
    {
        fractal_triangle_mainCalculator test = new fractal_triangle_mainCalculator(4 , new Vector3f(1 , 4, 1) , 1);
       
        for (int i = 0; i < 4; i++) 
        {   
            System.out.println(test.ListePoint[i].position);
            for (int j = 0; j < 4; j++) 
            {
              if (j != i)     
              {
                System.out.println(test.ListePoint[i].distance(test.ListePoint[j]));
              }
            }
          
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
