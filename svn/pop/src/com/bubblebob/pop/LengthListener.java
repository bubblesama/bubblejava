package com.bubblebob.pop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.bubblebob.pop.LengthView.Square;

public class LengthListener implements MouseListener,KeyListener,MouseMotionListener{
	public LengthListener(LengthView model) {
		super();
		this.model = model;
	}
	private LengthView model;
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		//		System.out.println("[LengthListener#] e.getX="+e.getX()+" getY="+e.getY());
		Square square = model.getProjectedSquare(e.getX(), e.getY());
		if (square != null){
			if (e.getButton() == MouseEvent.BUTTON1){
				square.up();
			}else{
				square.down();
			}
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {
		//		System.out.println("[LengthListener#keyPressed] "+e.getKeyCode());
		switch (e.getKeyCode()) {
		case 39:
			model.dx0 = -model.scaleX*model.ddx0;
			break;
		case 37:
			model.dx0 = model.scaleX*model.ddx0;
			break;
		case 40:
			model.dy0 = -model.scaleY*model.ddy0;
			break;
		case 38:
			model.dy0 = model.scaleY*model.ddy0;
			break;
		default:
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 39:
		case 37:
			model.dx0 = 0;
			break;
		case 40:
		case 38:
			model.dy0 = 0;
			break;
		default:
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		model.mouseX = e.getX();
		model.mouseY = e.getY();
	}
}
