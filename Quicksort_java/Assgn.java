import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;


public class Assgn {
	String filename ;
	File inputFile;
	int n;
	int[] arr;
	
	public Assgn(String filename) {
		// TODO Auto-generated constructor stub
		this.filename = filename;
	}
	
	public boolean loadFile() {
		inputFile=new File(filename);
		return inputFile.exists();
	}
	
	public void readFile() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(inputFile);
		NodeList nlist=doc.getDocumentElement().getChildNodes();
		List<Integer> lst=new ArrayList<Integer>();
		for(int i=0;i<nlist.getLength();i++){
			Node node=nlist.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				lst.add(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
			}
		}
		
		n = lst.size();
		arr = new int[n];
		for(int i=0;i<n;i++){
			arr[i] = lst.get(i);
			System.out.println(arr[i]);
		}
	}
	
	public void sort() throws InterruptedException {
		Quicksort obj=new Quicksort(arr,0,n-1);
		Thread t=new Thread(obj);
		t.start();
		t.join();
		System.out.println("Output:");
		for(int i=0;i<n;i++){
			System.out.print(arr[i]+" ");
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException, ParserConfigurationException, SAXException {
		Assgn obj=new Assgn("/home/shraddha/Downloads/A2/input.xml");
		obj.loadFile();
		obj.readFile();
		obj.sort();
	}

}uri
