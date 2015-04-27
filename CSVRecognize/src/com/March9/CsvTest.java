package com.March9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.print.attribute.standard.MediaSize.Other;

public class CsvTest {
	private String fileName = null;
	private BufferedReader bufferedReader = null;
	private Vector vector = new Vector();
	
	public CsvTest(String filename) throws IOException{
		this.fileName = filename;
		bufferedReader = new BufferedReader(new FileReader(fileName));
		String temp;
		while ((temp = bufferedReader.readLine())!= null) {
			if (!temp.startsWith("#")) {
				vector.add(temp);
			}
		}
		
	}
	
	public Vector getVector(){
		return vector;
	}
	
	public int getRowCount(){
		return vector.size();
	}
	
	public int getColumnCount(){
		if (!vector.toString().equals("[]")) {
			return vector.get(0).toString().split(",").length;
		}
		else if (vector.get(0).toString().trim().length()!=0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public String getRow(int index){
		if (this.getRowCount() == 0)
			return null;
		return (String) vector.get(index);
	}
	
	public String getColumn(int index){
		if (this.getColumnCount() == 0){
			return null;
		}
		
		StringBuffer scol = new StringBuffer();
		String temp = null;
		int column = this.getColumnCount();
		if (column >= 1) {
			for (Iterator it = vector.iterator();it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp.split(",")[index] + ",");
				
			}
		}
		
		String str = new String(scol.toString());
		str = str.substring(0,str.length()-1);
		return str;
	}
	
	public String getValueAt(int row, int col){
		String temp = null;
		int column = this.getColumnCount();
		if (column >= 1) {
			temp = vector.get(row).toString().split(",")[col];
		} else {
			temp = null;
		}
		return temp;
	}
	
	public void printAll(){
		Iterator it = vector.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
	public void CsvColse() throws IOException{
		this.bufferedReader.close();
	}
	
	public static void main(String[] args) throws IOException {
		CsvTest cu = new CsvTest("zuzel.csv");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
