/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.vue.BoiteDialog;
import static Space.vue.JPanelInterface.BRUTE_FORCE_GRAVITE;
import static Space.vue.JPanelInterface.FRACTAL_TRIANGLE;
import static Space.vue.JPanelInterface.OPTION_GENERAL;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
/**
 *
 * @author cou8
 */
public class OptionCalculateur extends  JDialog{
     public static String OK_TEXT = "Ok";
      public static int NOMBRE_PARTICULE_MINIMUN = 2;
     public static String ANNULER_TEXT = "Annuler";
     public static String LABEL_PARTICULE_TEXT = "Nombre de particule";
      public static String LABEL_QUALITY_TEXT = "Qualité graphique";
      public static String TITRE = "Options";
    JTextField nombreDeParticule;
    JTextField highQuality;
    JPanel bas;
    JPanel Millieu;
    JPanel MillieuGauche;
    JPanel MillieuDroit;
    JPanel panel;
    
    JLabel labeNombreParticule;
    JLabel labelQuality;
    JLabel vide;
    
    JComboBox quality;
    String[] qualityStrings = { "élevé", "bas"};
    public JButton Ok;
    public JButton Annuler;
    public OptionCalculateur()
    {
        this.setSize(400, 300);
      vide = new JLabel("\n");

        Ok = new JButton(OK_TEXT);
        Annuler = new JButton(ANNULER_TEXT);
        this.setTitle(TITRE);
        // Fill Swing window with canvas and swing components
        panel = new JPanel(new FlowLayout()); 
        panel.setLayout(new BorderLayout());
        labeNombreParticule = new JLabel(LABEL_PARTICULE_TEXT);
        nombreDeParticule  = new JTextField();
       // nombreDeParticule.setEnabled(true);
         quality = new JComboBox(qualityStrings);
         labelQuality = new JLabel(LABEL_QUALITY_TEXT);
        Millieu = new JPanel(new GridLayout(1, 2));
        MillieuDroit = new JPanel(new GridLayout(10, 1));
        MillieuGauche = new JPanel(new GridLayout(10, 1));
        JLabel videGauche = new JLabel("");
         MillieuGauche.add(videGauche);
             MillieuGauche.add(labeNombreParticule);
         MillieuGauche.add(labelQuality);
    
       
        MillieuDroit.add(vide);
        MillieuDroit.add(nombreDeParticule);
        MillieuDroit.add(quality);
        Millieu.add(MillieuGauche); 
        Millieu.add(MillieuDroit);
        
        bas = new JPanel(new GridLayout(1, 2));
        bas.add(Ok);
        bas.add(Annuler);
        panel.add(bas , BorderLayout.SOUTH);
        panel.add(Millieu , BorderLayout.CENTER);
        this.add(panel);
           this.setVisible(true);
        this.setModal(true);  
        
    }   
    public String getQuality()
    { 
        return (String)quality.getSelectedItem();
    }     
    public int getNombreParticuleSelectionner()
    {  
       int retour = -1;
       boolean valide = true;
       int nombreParticule = -1;
       try
       {
             nombreParticule = Integer.parseInt(nombreDeParticule.getText());
       }
       catch(NumberFormatException E)
       {  
            JOptionPane.showMessageDialog(null , "Vous devez incrire un nombre" + " dans le champ : " +  LABEL_PARTICULE_TEXT, "Erreur de saisies" , 0);
            valide = false;
       }
      if (valide)
      { 
          if  (nombreParticule  < NOMBRE_PARTICULE_MINIMUN)
          {
               JOptionPane.showMessageDialog(null , "Minimun de " +  NOMBRE_PARTICULE_MINIMUN + "Particules", "Erreur de saisies" , 0);
          }
          else
          {
               retour = nombreParticule;
          }
             
      }       
       return retour;
    }
         
    //For testing purpose
   public static void main(String[] args)
   {
        //OptionCalculateur inter = new OptionCalculateur();
       
   }  
   
 
}
