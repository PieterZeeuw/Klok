package nl.zeeuw.klok.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Pieter
 * 
 * @date 14 sep. 2014
 */
public class KlokFrame extends JFrame {
    private static final long serialVersionUID = -9129492645822296779L;
    KlokComponent klok;
    
    public KlokFrame () {
	super("Klok");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setSize(500,500);
	setVisible(true);
	setLocationRelativeTo(null);
	
	GridLayout layout = new GridLayout(2,0);
	this.setLayout(layout);
	
	JPanel p1 = new JPanel ();
	p1.setSize(300, 300);
	p1.setPreferredSize(new Dimension(300,300));
	
	klok = new KlokComponent (new Dimension(250,250));
	
	p1.add(klok);
	
	JPanel p2 = new JPanel ();
	p2.setPreferredSize(new Dimension(100,50));
	JButton btn = new JButton("Start");
	btn.setPreferredSize(new Dimension(100,50));
	btn.addActionListener(new ActionListener () {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		Timer t = new Timer(999, new ActionListener () {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			klok.updateTime();
			klok.repaint();
		    }
		    
		});	
		t.start();
	    }	    
	});
	p2.add(btn);
		
	add(p1);
	add(p2);
	
	repaint();
	p1.repaint();
	p2.repaint();
	
	pack();
    }

}
