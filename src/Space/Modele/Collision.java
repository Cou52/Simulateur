/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele;

import com.jme3.math.Vector3f;

/**
 *
 * @author cou8
 */
public class Collision {
    public float u0;    
    public float u1 ;       
     public boolean checkifColision(Body bodiesA ,Body bodiesB ,float delattime )
      {
         float ra = bodiesA.radiussize;
         float rb = bodiesB.radiussize;
         Vector3f B0 = bodiesA.getPosition();
         Vector3f B1 = bodiesB.getPosition();
          
         Vector3f A0 = (bodiesA.getPosition().add(bodiesA.vectorUpdate(delattime)));
         Vector3f A1 = (bodiesB.getPosition().add(bodiesB.vectorUpdate(delattime)));
          u0 = 0;
          u1 = 0;
         
         Vector3f va = A1.subtract(A0);
         Vector3f vb = B1.subtract(B0);
         Vector3f AB = B0.subtract(A0);
         
         Vector3f vab = va.subtract(vb);
         
         float rab = ra + rb;
         
         float a = vab.dot(vab);
         float b = vab.dot(AB) * 2;
         
         float c = AB.dot(AB) -(rab * rab);
         
         if( AB.dot(AB) <= rab * rab) 
         {
             u0 = 0;
             u1 = 0;
             return true; 
         } 
         
         if (QuadraticFormula(a ,b ,c ,u0 , u1))
         {  
             if (u0 > u1)
             {
                 SWAP(u0 , u1);
                  return true; 
             }   
         }  
         
         return false;
          
         
     }
     
    public boolean  QuadraticFormula(float a ,float b, float c, float r1 , float r2)
     {     

        boolean retour = false;
        float q = b*b-4*a*c;
        if( q >= 0 ) 
         {  
             float sq = (float)Math.sqrt(q);
             float d = 1/ (2*a);
             r1 = (-b + sq) * d;
             r2 = (-b - sq) * d;
             retour = true;
             
         } 
        return retour;
     }  
    public void SWAP(float a ,float b)
    { 
        float t = a;
        a = b;
        b = t;
    }      
}
