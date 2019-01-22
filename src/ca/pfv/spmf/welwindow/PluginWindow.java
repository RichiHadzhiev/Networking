package ca.pfv.spmf.welwindow;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import ca.pfv.spmf.gui.Main;
import ca.pfv.spmf.gui.PreferencesManager;




public class PluginWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JButton jButton1setFolder;
	 JButton jButton2Install;
	 JButton jButton2Update;
	 JButton jButton3Remove;
	private JButton jButton4Webpage;
	private JButton jButton5Reset;
	private JLabel jLabel5;
	private JLabel jLabel7Description;
	private JLabel jLabel8Plugins;
	private JLabel jLabel9PluginFolder;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;	
	private JTextArea jTextArea1;
	private JTextField jTextField1Repository;	  
	private JTextField jTextField2Folder;	 
    DefaultTableModel tableModel;
    JTable jTable1;
	Welcome welcome;

    
	public PluginWindow(Welcome welcome) {
		this.setAlwaysOnTop(true);
		this.setModal(true);
		
        initComponents();
        this.welcome=welcome;
    }

	private void initComponents() {
		setTitle("SPMF-V." + Main.SPMF_VERSION
				+ "-Plugin Manager");
		this.setLocation(400, 100);
		this.setSize(880, 500);
		this.setResizable(false);

		jLabel5 = new JLabel();
		jLabel7Description = new JLabel();
		jLabel8Plugins = new JLabel();
		jLabel9PluginFolder = new JLabel();
		jButton1setFolder = new JButton();
		jButton2Install = new JButton();
		jButton2Update = new JButton();
		jButton3Remove = new JButton();
		jButton4Webpage = new JButton();
		jButton5Reset = new JButton();
		jTextArea1 = new JTextArea();
		jPanel1 = new JPanel();
		jScrollPane1 = new JScrollPane();
		tableModel = new DefaultTableModel();
		
		jTable1 = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		
		jTextField1Repository = new JTextField(
				PreferencesManager.getInstance().getRepositoryURL());
		

		jTextField2Folder = new JTextField(PluginManager.getPluginFolderPath().getAbsolutePath());

		jLabel5.setText("Plugin repository: ");
		jLabel7Description.setText("Description:");
		jLabel8Plugins.setText("Plugins:");
		jLabel9PluginFolder.setText("Plugin folder (on local computer):");
		jTextArea1.setLineWrap(true);
		jTextArea1.setEditable(false);
		jTextArea1.setFont(new Font("Arial", Font.PLAIN, 16));
		
		refreshPluginTable();

		Object[][] data = new Object[PluginManager.listPlugin.size()][4];
		jTable1.setModel(new DefaultTableModel(data, new String[] { "Name",
				"Author", "Category", "Installed?", "Version" }));
	    	 
        jScrollPane1.setViewportView(jTable1);
        
        
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& jTable1.rowAtPoint(e.getPoint()) == jTable1
								.getSelectedRow()) {
					jTextArea1.setText(PluginManager.listPlugin.get(
							jTable1.getSelectedRow()).getDescription());

					// if the user has selected something
					if (jTable1.getSelectedRow() != -1) {
						jButton2Install.setEnabled(true);
						jButton3Remove.setEnabled(false);
						jButton4Webpage.setEnabled(true);
						jButton2Update.setEnabled(false);
					}

					if (PluginManager
							.isPluginInstalled(PluginManager.listPlugin.get(
									jTable1.getSelectedRow()).getName())) {
						jButton2Update.setEnabled(true);
						jButton2Install.setEnabled(false);
						jButton3Remove.setEnabled(true);
					} else {
						jButton3Remove.setEnabled(false);
					}
				}
			}
		});

		jButton1setFolder.setText("Change");
		jButton1setFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1FolderActionPerformed(evt);
			}
		});

		jButton2Install.setText("Install");
		jButton2Install.setEnabled(false);
		jButton2Install.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonInstallActionPerformed(evt);
			}
		});
		
		jButton2Update.setText("Update");
		jButton2Update.setEnabled(false);
		jButton2Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonUpdateActionPerformed(evt);
			}
		});

		jButton3Remove.setText("Remove");
		jButton3Remove.setEnabled(false);
		jButton3Remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton3RemoveActionPerformed(evt);
			}
		});

		jButton4Webpage.setText("Plugin documentation");
		jButton4Webpage.setEnabled(false);
			jButton4Webpage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton4WebpageActionPerformed(evt);
			}
		});

		jButton5Reset.setText("Change");
		jButton5Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton5ChangeRepositoryActionPerformed(evt);
			}
		});
		
		
		jButton1setFolder.setSize(140, 20);
		jButton1setFolder.setLocation(700, 20);
		this.add(jButton1setFolder);


		jButton2Install.setSize(140, 30);
		jButton2Install.setLocation(160, 390);
		this.add(jButton2Install);
		
		jButton2Update.setSize(140, 30);
		jButton2Update.setLocation(310, 390);
		this.add(jButton2Update);

		jButton3Remove.setSize(140, 30);
		jButton3Remove.setLocation(460, 390);
		this.add(jButton3Remove);
		
		jButton4Webpage.setSize(200, 30);
		jButton4Webpage.setLocation(610, 390);
		this.add(jButton4Webpage);

		jButton5Reset.setSize(140, 20);
		jButton5Reset.setLocation(700, 50);
		this.add(jButton5Reset);

		jLabel9PluginFolder.setBounds(25, 20, 300, 20);
		this.add(jLabel9PluginFolder);

		jLabel8Plugins.setBounds(25, 80, 300, 20);
		this.add(jLabel8Plugins);

		jLabel7Description.setBounds(140, 300, 300, 20);
		this.add(jLabel7Description);


		jLabel5.setBounds(25, 50, 120, 20);
		this.add(jLabel5);

		jTextField1Repository.setBounds(140, 50, 550, 20);
		jTextField1Repository.setEditable(false);
		this.add(jTextField1Repository);
		
		jTextField2Folder.setBounds(220, 20, 470, 20);
		jTextField2Folder.setEditable(false);
		this.add(jTextField2Folder);

		jTextArea1.setSize(620, 75);
		jTextArea1.setLocation(220, 300);
		this.add(jTextArea1);

		jScrollPane1.setSize(700, 200);
		jScrollPane1.setLocation(140, 85);
		this.add(jScrollPane1);

		this.add(jPanel1);
		this.setVisible(true);
	}

	private void refreshPluginTable() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				PluginManager.pluginInit();
				Object[][] data = new Object[PluginManager.listPlugin.size()][4];
				for (int i = 0; i< data.length; i++) {
					String flag = PluginManager.isPluginInstalled(PluginManager.listPlugin.get(i)
							.getName()) ? "Yes" : "No";
					data[i] = new Object[] {
							PluginManager.listPlugin.get(i).getName(),
							PluginManager.listPlugin.get(i).getAuthor(),
							PluginManager.listPlugin.get(i).getCategory(), flag,
							PluginManager.listPlugin.get(i).getVersion() };
				}

				jTable1.setModel(new DefaultTableModel(data, new String[] {
						"Name", "Author", "Category", "Installed?", "Version" }

				));
				jTable1.setShowGrid(false);
				jTable1.setFont(new Font("Menu.font", Font.PLAIN, 12));
				jTable1.getColumnModel().getColumn(0).setPreferredWidth(550);
				jTable1.getColumnModel().getColumn(1).setPreferredWidth(270);
				jTable1.getColumnModel().getColumn(2).setPreferredWidth(220);
				jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
				jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
			}
		});
		t.start();
	}

		
	private void jButton1FolderActionPerformed(ActionEvent evt) {
		File path;
		path = PluginManager.getPluginFolderPath();

		JFileChooser chooser = new JFileChooser(path.getAbsolutePath());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if (f != null) {
			String selectedPath = f.getPath();
			PreferencesManager.getInstance().setPluginFolderFilePath(selectedPath);
			jTextField2Folder.setText(f.getPath());
		}
	}

	private void jButtonInstallActionPerformed(ActionEvent evt) {

		new DownloadWindow(PluginManager.getPluginFolderPath()
				.getAbsolutePath(), false, this).setVisible(true);
	}

	private void jButtonUpdateActionPerformed(ActionEvent evt) {

		new DownloadWindow(PluginManager.getPluginFolderPath()
				.getAbsolutePath(), true, this).setVisible(true);
	}

	private void jButton3RemoveActionPerformed(ActionEvent evt) {
		
		try {
			int idx = jTable1.getSelectedRow();		
			
			// Remove the plugin
			PluginManager.removePlugin(PluginManager.listPlugin.get(idx).getName());
			
      		jTable1.setValueAt("No", idx, 3);
      		jButton3Remove.setEnabled(false);
      		jButton2Update.setEnabled(false);
      		jButton2Install.setEnabled(true);
         }catch(Exception e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
    }

	private void jButton4WebpageActionPerformed(ActionEvent evt) {
		int idx = jTable1.getSelectedRow();
		// =================================
		// Create the URL:
		// http://www.philippe-fournier-viger.com/spmf/plugins/{pluginname}/documentation.php
		String url2 = PluginManager.listPlugin.get(idx).getUrlOfDocumentation();
		// =================================
		// String url2 = Plugins.url2.replace("{pluginname}",
		if (Desktop.isDesktopSupported()) {
			try {
				URI uri = URI.create(url2);
				Desktop dp = Desktop.getDesktop();
				if (dp.isSupported(Desktop.Action.BROWSE)) {
					dp.browse(uri);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Null!");
		}
	}
		
	private void jButton5ChangeRepositoryActionPerformed(ActionEvent evt) {
		
		String choice = JOptionPane.showInputDialog(this,"Enter the URL of a plugin repository.",
				"http://www.philippe-fournier-viger.com/spmf/plugins/");
		if(choice != null){
			// check if the URL is really a repository
			boolean isARepository = PluginManager.checkIfURLisAPluginRepository(choice);
			if(isARepository){

				// Remember the URL so that if we close the software we still remember it.
				PreferencesManager.getInstance().setRepositoryURL(choice);
				jTextField1Repository.setText(choice);
				jTextField1Repository.requestFocus();
				
				// Refresh the list of plugins in the JTABLE...
				refreshPluginTable();
			}else{
				JOptionPane.showMessageDialog(this, "The URL is not a valid plugin repository!");
			}
			
		}
	}

}
