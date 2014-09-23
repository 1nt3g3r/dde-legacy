package ua.com.integer.dde.startpanel.extension;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

public class EclipseClasspath {
	private Document doc;
	
	public EclipseClasspath() {
		Element rootElement = new Element("classpath");
		doc = new Document(rootElement);
	}
	
	public EclipseClasspath(File classpathFile) throws ValidityException, ParsingException, IOException {
		Builder builder = new Builder();
		doc = builder.build(classpathFile);
	}
	
	public void addLib(String libPath, boolean exported) {
		Element classpath = doc.getRootElement();
		
		Element libClasspath = new Element("classpathentry");
		if (exported) {
			Attribute exportedAttribute = new Attribute("exported", "true");
			libClasspath.addAttribute(exportedAttribute);
		}
		
		Attribute kindAttribute = new Attribute("kind", "lib");
		libClasspath.addAttribute(kindAttribute);
		
		Attribute pathAttribute = new Attribute("path", libPath);
		libClasspath.addAttribute(pathAttribute);
		
		classpath.appendChild(libClasspath);
	}
	
	public boolean removeLib(String libPath) {
		Element classpath = doc.getRootElement();
		Elements elems = classpath.getChildElements();
		
		for(int i = 0, end = elems.size(); i < end; i++) {
			Element element = elems.get(i);
			if (element.getQualifiedName().equals("classpathentry")) {
				boolean isLib = false;
				boolean libPathFound = false;
				for(int j = 0, jEnd = element.getAttributeCount(); j < jEnd; j++) {
					Attribute attrib = element.getAttribute(j);
					
					if (attrib.getQualifiedName().equals("kind")) {
						if (attrib.getValue().equals("lib")) {
							isLib = true;
						}
					}
					
					if(attrib.getQualifiedName().equals("path")) {
						if (attrib.getValue().equals(libPath)) {
							libPathFound = true;
						}
					}
					
					if(isLib && libPathFound) {
						classpath.removeChild(i);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	class StringOutputStream extends OutputStream {
		private StringBuffer result = new StringBuffer();
		
		@Override
		public void write(int b) throws IOException {
			result.append((char) b);
		}
		
		public String getResult() {
			return result.toString();
		}
	}
	
	public String toXml() {
		StringOutputStream stream = new StringOutputStream();
		Serializer serializer = new Serializer(stream);
		serializer.setIndent(4);
	    serializer.setMaxLength(64);
	    try {
			serializer.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return stream.getResult();
	}
}
