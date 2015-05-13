/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur.Interface;
import Space.Modele.Calculateur.BruteForceCalculator;
import java.util.EventListener;

/**
 *
 * @author cou8
 */
public interface CalculatorListener extends EventListener {
    public void CalculateurHasStopped(BruteForceCalculator calculateur);
}
