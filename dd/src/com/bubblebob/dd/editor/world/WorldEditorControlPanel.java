package com.bubblebob.dd.editor.world;

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
public class WorldEditorControlPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3553907661519111321L;
	private WorldEditorController controller;
	
	private JTextField mapNameTextField;
	
	public WorldEditorControlPanel(WorldEditorController controller){
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
		
		
		JButton buttonNew = new JButton("new");
		c.fill = GridBagConstraints.BOTH;
		c.gridx=1;
		c.gridy=7;
		c.gridwidth=1;
		buttonNew.setBackground(Color.LIGHT_GRAY);
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newMap();
			}
		});
		this.add(buttonNew,c);
		
		//...partie carte>

		
		this.setSize(200, 800);
	}

}
