package com.bubblebob.dd.editor.dungeon;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bubblebob.dd.model.dungeon.map.DungeonMapFactory;


/**
 * Partie de l'interface contenant les controles
 * @author bubblebob
 *
 */
public class DungeonEditorControlPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3553907661519111321L;
	private DungeonEditorController controller;
	
	private JTextField mapNameTextField;
	private JTextField mapWidthTextField;
	private JTextField mapHeightTextField;
	
	public DungeonEditorControlPanel(DungeonEditorController controller){
		super();
		this.controller = controller;
		initSwing();
	}
	
	
	
	public void initSwing(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//<partie fichier...
		final JLabel fileLabel = new JLabel("File");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=2;
		this.add(fileLabel,c);
		
		mapNameTextField = new JTextField(12);
		c.gridx=1;
		c.gridy=2;
		c.gridheight=1;
		c.gridwidth=2;
		this.add(mapNameTextField,c);
		
		JButton buttonTextSave = new JButton("save");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=3;
		c.gridwidth=1;
		buttonTextSave.setBackground(Color.LIGHT_GRAY);
		buttonTextSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveMapAsText(mapNameTextField.getText());
			}
		});
		this.add(buttonTextSave,c);
		
		JButton buttonFromText = new JButton("load");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=2;
		c.gridy=3;
		c.gridwidth=1;
		buttonFromText.setBackground(Color.LIGHT_GRAY);
		buttonFromText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadTextMap(mapNameTextField.getText());
			}
		});
		this.add(buttonFromText,c);
		//...partie fichier>
		//<partie carte...
		final JLabel mapLabel = new JLabel("Map");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=4;
		c.gridwidth=2;
		this.add(mapLabel,c);
		
		final JLabel widthLabel = new JLabel("width");
		c.gridx=1;
		c.gridy=5;
		c.gridheight=1;
		c.gridwidth=1;
		this.add(widthLabel,c);
		
		mapWidthTextField = new JTextField(12);
		c.gridx=2;
		c.gridy=5;
		c.gridheight=1;
		c.gridwidth=1;
		mapWidthTextField.setText("11");
		this.add(mapWidthTextField,c);
		
		final JLabel heightLabel = new JLabel("height");
		c.gridx=1;
		c.gridy=6;
		c.gridheight=1;
		c.gridwidth=1;
		this.add(heightLabel,c);
		
		mapHeightTextField = new JTextField(12);
		c.gridx=2;
		c.gridy=6;
		c.gridheight=1;
		c.gridwidth=1;
		mapHeightTextField.setText("11");
		this.add(mapHeightTextField,c);
		
		JButton buttonNew = new JButton("new");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=7;
		c.gridwidth=1;
		buttonNew.setBackground(Color.LIGHT_GRAY);
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newMap(Integer.parseInt(mapWidthTextField.getText()), Integer.parseInt(mapHeightTextField.getText()));
			}
		});
		this.add(buttonNew,c);
		
		//...partie carte>
		//<partie outils...
		final JLabel toolsLabel = new JLabel("Tools");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=8;
		c.gridwidth=2;
		this.add(toolsLabel,c);
		
		JButton buttonHollow = new JButton("hollow");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=9;
		c.gridwidth=1;
		buttonHollow.setBackground(Color.LIGHT_GRAY);
		buttonHollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.hollowMap();
			}
		});
		this.add(buttonHollow,c);
		
		JButton buttonRotate = new JButton("rotate");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=2;
		c.gridy=9;
		c.gridwidth=1;
		buttonRotate.setBackground(Color.LIGHT_GRAY);
		buttonRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.quarterRotate();
			}
		});
		this.add(buttonRotate,c);
		
		//...partie outils>
		
		this.setSize(200, 800);
	}

}
