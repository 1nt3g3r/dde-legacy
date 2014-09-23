package ua.com.integer.dde.startpanel.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import ua.com.integer.dde.startpanel.FrameTools;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackSettingsEditor extends JDialog {
	private static final long serialVersionUID = -6430678242428018682L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JComboBox<String> comboBoxOutputFormat;
	private JComboBox<String> comboBoxMinFilter;
	private JComboBox<String> comboBoxMagFilter;
	private JComboBox<String> comboBoxAtlasMaxHeight;
	private JComboBox<String> comboBoxAtlasMaxWidth;
	private JComboBox<String> comboBoxAtlasMinHeight;
	private JComboBox<String> comboBoxAtlasMinWidth;
	private JCheckBox chckbxForcePotImages;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PackSettingsEditor dialog = new PackSettingsEditor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PackSettingsEditor() {
		getContentPane().setBackground(Color.GRAY);
		setTitle("DDE Pack Settings Editor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel outputFormatPanel = new JPanel();
			outputFormatPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			outputFormatPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(outputFormatPanel);
			outputFormatPanel.setLayout(new BoxLayout(outputFormatPanel, BoxLayout.X_AXIS));
			{
				JLabel lblOutputFormat = new JLabel("Output format");
				outputFormatPanel.add(lblOutputFormat);
			}
			{
				comboBoxOutputFormat = new JComboBox<String>();
				comboBoxOutputFormat.setBackground(Color.WHITE);
				comboBoxOutputFormat.setModel(new DefaultComboBoxModel<String>(new String[] {"jpg", "png"}));
				comboBoxOutputFormat.setSelectedIndex(1);
				outputFormatPanel.add(comboBoxOutputFormat);
			}
			FrameTools.setPreferredHeight(outputFormatPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(10);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel panelTextureFilterTitle = new JPanel();
			panelTextureFilterTitle.setBackground(Color.GRAY);
			contentPanel.add(panelTextureFilterTitle);
			panelTextureFilterTitle.setLayout(new BoxLayout(panelTextureFilterTitle, BoxLayout.X_AXIS));
			{
				JLabel lblTextureFilters = new JLabel("Texture filters");
				panelTextureFilterTitle.add(lblTextureFilters);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				panelTextureFilterTitle.add(horizontalGlue);
			}
		}
		{
			JPanel filterPanel = new JPanel();
			filterPanel.setBackground(Color.LIGHT_GRAY);
			filterPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(filterPanel);
			filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
			{
				JLabel labelMagFilter = new JLabel("Mag. filter");
				filterPanel.add(labelMagFilter);
			}
			{
				comboBoxMagFilter = new JComboBox<String>();
				comboBoxMagFilter.setModel(new DefaultComboBoxModel(TextureFilter.values()));
				filterPanel.add(comboBoxMagFilter);
			}
			{
				JLabel labelMinFilter = new JLabel("Min. filter");
				filterPanel.add(labelMinFilter);
			}
			{
				comboBoxMinFilter = new JComboBox<String>();
				comboBoxMinFilter.setModel(new DefaultComboBoxModel(TextureFilter.values()));
				filterPanel.add(comboBoxMinFilter);
			}
			FrameTools.setPreferredHeight(filterPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(10);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel potImagesPanel = new JPanel();
			potImagesPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			potImagesPanel.setBackground(Color.GRAY);
			contentPanel.add(potImagesPanel);
			potImagesPanel.setLayout(new BoxLayout(potImagesPanel, BoxLayout.X_AXIS));
			{
				chckbxForcePotImages = new JCheckBox("Force pot images");
				chckbxForcePotImages.setSelected(true);
				chckbxForcePotImages.setBackground(Color.GRAY);
				potImagesPanel.add(chckbxForcePotImages);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				potImagesPanel.add(horizontalGlue);
			}
		}
		{
			JPanel packSizeLabelPanel = new JPanel();
			packSizeLabelPanel.setBackground(Color.GRAY);
			contentPanel.add(packSizeLabelPanel);
			packSizeLabelPanel.setLayout(new BoxLayout(packSizeLabelPanel, BoxLayout.X_AXIS));
			{
				JLabel packSizeLabel = new JLabel("Max output atlas size");
				packSizeLabelPanel.add(packSizeLabel);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				packSizeLabelPanel.add(horizontalGlue);
			}
		}
		{
			JPanel packSizePanel = new JPanel();
			packSizePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			packSizePanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(packSizePanel);
			packSizePanel.setLayout(new BoxLayout(packSizePanel, BoxLayout.X_AXIS));
			{
				JLabel lblWidth = new JLabel("Max width");
				packSizePanel.add(lblWidth);
			}
			{
				comboBoxAtlasMaxWidth = new JComboBox();
				comboBoxAtlasMaxWidth.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64", "128", "256", "512", "1024", "2048", "4096"}));
				comboBoxAtlasMaxWidth.setSelectedIndex(6);
				packSizePanel.add(comboBoxAtlasMaxWidth);
			}
			{
				JLabel lblHeight = new JLabel("Max height");
				packSizePanel.add(lblHeight);
			}
			{
				comboBoxAtlasMaxHeight = new JComboBox();
				comboBoxAtlasMaxHeight.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64", "128", "256", "512", "1024", "2048", "4096"}));
				comboBoxAtlasMaxHeight.setSelectedIndex(6);
				packSizePanel.add(comboBoxAtlasMaxHeight);
			}
			FrameTools.setPreferredHeight(packSizePanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(10);
			contentPanel.add(verticalStrut);
		}
		
		//Min height
		{
			JPanel packSizeLabelPanel = new JPanel();
			packSizeLabelPanel.setBackground(Color.GRAY);
			contentPanel.add(packSizeLabelPanel);
			packSizeLabelPanel.setLayout(new BoxLayout(packSizeLabelPanel, BoxLayout.X_AXIS));
			{
				JLabel packSizeLabel = new JLabel("Min output atlas size");
				packSizeLabelPanel.add(packSizeLabel);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				packSizeLabelPanel.add(horizontalGlue);
			}
		}
		{
			JPanel minPackSizePanel = new JPanel();
			minPackSizePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			minPackSizePanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(minPackSizePanel);
			minPackSizePanel.setLayout(new BoxLayout(minPackSizePanel, BoxLayout.X_AXIS));
			{
				JLabel lblWidth = new JLabel("Min width");
				minPackSizePanel.add(lblWidth);
			}
			{
				comboBoxAtlasMinWidth = new JComboBox();
				comboBoxAtlasMinWidth.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64", "128", "256", "512", "1024", "2048", "4096"}));
				comboBoxAtlasMinWidth.setSelectedIndex(0);
				minPackSizePanel.add(comboBoxAtlasMinWidth);
			}
			{
				JLabel lblHeight = new JLabel("Min height");
				minPackSizePanel.add(lblHeight);
			}
			{
				comboBoxAtlasMinHeight = new JComboBox();
				comboBoxAtlasMinHeight.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64", "128", "256", "512", "1024", "2048", "4096"}));
				comboBoxAtlasMinHeight.setSelectedIndex(0);
				minPackSizePanel.add(comboBoxAtlasMinHeight);
			}
			FrameTools.setPreferredHeight(minPackSizePanel);
		}
		
		{
			Component verticalGlue = Box.createVerticalGlue();
			contentPanel.add(verticalGlue);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Save");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		pack();
	}

	public JButton getOkButton() {
		return okButton;
	}
	
	public Settings getSettings() {
		Settings sets = new Settings();
		sets.outputFormat = comboBoxOutputFormat.getSelectedItem().toString();
		sets.filterMag = TextureFilter.valueOf(comboBoxMagFilter.getSelectedItem().toString());
		sets.filterMin = TextureFilter.valueOf(comboBoxMagFilter.getSelectedItem().toString());
		sets.maxWidth = Integer.parseInt(comboBoxAtlasMaxWidth.getSelectedItem().toString());
		sets.maxHeight = Integer.parseInt(comboBoxAtlasMaxHeight.getSelectedItem().toString());
		sets.minWidth = Integer.parseInt(comboBoxAtlasMinWidth.getSelectedItem().toString());
		sets.minHeight = Integer.parseInt(comboBoxAtlasMinHeight.getSelectedItem().toString());
		sets.pot = chckbxForcePotImages.isSelected();
		return sets;
	}
	
	public void loadSettings(Settings sets) {
		comboBoxOutputFormat.setSelectedItem(sets.outputFormat);
		comboBoxMagFilter.setSelectedItem(sets.filterMag);
		comboBoxMinFilter.setSelectedItem(sets.filterMin);
		comboBoxAtlasMaxWidth.setSelectedItem(sets.maxWidth + "");
		comboBoxAtlasMaxHeight.setSelectedItem(sets.maxHeight + "");
		comboBoxAtlasMinWidth.setSelectedItem(sets.minWidth + "");
		comboBoxAtlasMinHeight.setSelectedItem(sets.minHeight + "");
		chckbxForcePotImages.setSelected(sets.pot);
	}
}
