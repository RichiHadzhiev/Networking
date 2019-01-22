package ca.pfv.spmf.welwindow;

import java.io.Serializable;

public class Plugin implements Serializable {
	
	/** Serial ID */
	private static final long serialVersionUID = 8961825827831257902L;
	
	/** the plugin name */
	private String name;
	
	/** the plugin description */
	private String description;
	
	/** the author of the plugin */
	private String author;	
	
	/** the category of plugin */
	private String category;
	
	/** the plugin version */
	private String version;
	
	/** the url of the documentation **/
	private String urlOfDocumentation;
	
	/** the repository from where the plugin was downloaded from **/
	private String repositoryURL; 
	
	/** the url of the plugin in the repository */
	private String repositoryPluginFolder;
	
	// ===============================================================
	
	public String getRepositoryURL() {
		return repositoryURL;
	}
	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}
	public void setUrlOfDocumentation(String urlOfDocumentation) {
		this.urlOfDocumentation = urlOfDocumentation;
	}
	public String getUrlOfDocumentation() {
		return urlOfDocumentation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setRepositoryPluginFolder(String folderName) {
		repositoryPluginFolder = folderName;
	}
	
	public String getRepositoryPluginFolder() {
		return repositoryPluginFolder;
	}	
	
}
