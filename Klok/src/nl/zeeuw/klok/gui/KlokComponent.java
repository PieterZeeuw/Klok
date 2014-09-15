package nl.zeeuw.klok.gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;

/**
 * @author Pieter
 * 
 * @date 14 sep. 2014
 */
public class KlokComponent extends JComponent {
    private static final long serialVersionUID = -8727509918087576531L;
    private Dimension size;
    
    private Date time;
    private DateFormat formatH;
    private DateFormat formatM;
    private DateFormat formatS;
    private Point mid;
    private int radius;
    
    public KlokComponent (Dimension size) {
	this.size=size;
	setSize(size);
	formatH = new SimpleDateFormat ("HH");
	formatM = new SimpleDateFormat ("mm");
	formatS = new SimpleDateFormat ("ss");
	time = new Date();
	
	mid = new Point(size.width/2,size.height/2);
	radius = (int) ( Math.min(size.width, size.height) * 0.5);
	radius = radius -25;
    }
    
    public void updateTime () {
	time = new Date();
    }
    
    

    /** 
     * Overridden
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
	return this.size;
    }



    /** 
     * Overridden
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D)g;
	
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        createClock (g2);
	
	g2.setStroke(new BasicStroke(4));
	addUurWijzer (g2, 45, formatH.format(time),formatM.format(time));
	g2.setStroke(new BasicStroke(2));
	addMinuutWijzer (g2, 20, formatM.format(time));
	g2.setStroke(new BasicStroke(1));
	addMinuutWijzer (g2, 10, formatS.format(time));
    }
    
    private void createClock (Graphics2D g2) {
	//Teken een rondje.
	g2.drawOval(mid.x - radius, mid.y-radius, 2 * radius, 2 * radius);
	
	//Teken tijdsaanduidingen op het rondje. 60 in totaal. Voor de kwartierwijzers, gebruik een dikkere lijn
	//Voor de 5 min wijzers gebruik een langere lijn.
	int j = 5;
	for (int i = 0; i < 60; i++) {
	    j = 5;
	    if (i % 5 == 0)
		j = 15;
	    if (i % 15 == 0)
		g2.setStroke(new BasicStroke(2));
	    else
		g2.setStroke(new BasicStroke(1));
	    
	    g2.drawLine( 
		    mid.x + ( (int) (radius * Math.sin( Math.toRadians( i * 6 ) ) ) ),
		    mid.y - ( (int) (radius * Math.cos( Math.toRadians( i * 6 ) ) ) ),
		    mid.x + ( (int) ((radius-j) * Math.sin( Math.toRadians( i * 6 ) ) ) ),
		    mid.y - ( (int) ((radius-j) * Math.cos( Math.toRadians( i * 6 ) ) ) )
		    );
	}
	
	g2.fillOval(mid.x - 5, mid.y - 5, 10, 10);
    }
    
    /**
     * De uurwijzer. Heeft ook een minuten-string nodig zodat de wijzer niet op een uur blijft staan.
     * @param g2
     * @param wijzerLength
     * @param timeStrH
     * @param timeStrM
     */
    private void addUurWijzer (Graphics2D g2, int wijzerLength, String timeStrH, String timeStrM) {
	//Het uur wordt bepaald (0-11)
	int time = (Integer.valueOf(timeStrH) % 12) * 5;
	// Minuten String naar int.
	int timeM = Integer.valueOf(timeStrM );
	//Tijd tussen het uur, per 24 minuten schuift de uur wijzer 1 plekje op
	time = time + (timeM / 12) ;
	
	//Teken de lijn, vanaf middenpunt naar de ingegeven waarde voor tijd.
	g2.drawLine( 
		    mid.x ,
		    mid.y ,
		    mid.x + ( (int) ((radius-wijzerLength) * Math.sin( Math.toRadians( time * 6 ) ) ) ),
		    mid.y - ( (int) ((radius-wijzerLength) * Math.cos( Math.toRadians( time * 6 ) ) ) )
		    );
    }
    
    /**
     * Voor het genereren van een wijzer die de minuten kan weergeven.
     * Wordt ook voor de secondewijzer gebruikt!
     * @param g2
     * @param wijzerLength
     * @param timeStr
     */
    private void addMinuutWijzer (Graphics2D g2, int wijzerLength, String timeStr) {
	//Converteer string naar int.
	int time = Integer.valueOf(timeStr);
	
	//Teken de lijn, van midden naar de plek van de ingeven tijd.
	g2.drawLine( 
		    mid.x ,
		    mid.y ,
		    mid.x + ( (int) ((radius-wijzerLength) * Math.sin( Math.toRadians( time * 6 ) ) ) ),
		    mid.y - ( (int) ((radius-wijzerLength) * Math.cos( Math.toRadians( time * 6 ) ) ) )
		    );
    }
    
    

}
