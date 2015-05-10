/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele.Calculateur;

import Space.Modele.OpenCL.JOCLTest;

/**
 *
 * @author cou8
 */
public interface BruteForceListener {
    public void Terminer(BruteForceTread currentTread);
    public void TerminerOpenCl(JOCLTest currentTread);
}
