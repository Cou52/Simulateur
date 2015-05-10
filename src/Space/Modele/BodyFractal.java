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
public class BodyFractal extends Body{
    public int sommet =-1;
    public BodyFractal(Vector3f pPosition , int psommet)
    {   
        super(pPosition);      
        speed = new Vector3f(0 , 0 ,0);
        sommet = psommet;
    }
}
