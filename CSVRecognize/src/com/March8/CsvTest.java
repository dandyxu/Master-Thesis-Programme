package com.March8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class CsvTest {
	private String fileName=null;
    private BufferedReader bufferedReader=null;
    private Vector v = new Vector();

    public CsvTest(String filename) throws IOException
    {
        this.fileName=filename;
        bufferedReader=new BufferedReader(new FileReader(fileName));
        String stemp;
        while((stemp=bufferedReader.readLine())!=null)
        {
            if(!stemp.startsWith("#"))  //Using "#" as a header to explain comments
               v.add(stemp);
        }
    }
    
    public Vector getVector()
    {
        return v;
    }
    
    //Get row count number
    public int getRowCount()
    {
        return v.size();
    }
    
    //Get specific row
    public String getRow(int index)
    {
        if(this.getRowCount()==0)
            return null;
        return (String)v.get(index);
    }
    
    //Get specific column
    public String getColumn(int index)
    {
        if(this.getColumnCount()==0)
        {
            return null;
        }
        StringBuffer scol = new StringBuffer();
        String temp = null;
        int column=this.getColumnCount();
        if(column >= 1)
        {
            for(Iterator it=v.iterator();it.hasNext();)
            {
                temp=it.next().toString();
                scol=scol.append(temp.split(",")[index]+",");
                
            }
        }
        
        String str=new String(scol.toString());
        str=str.substring(0,str.length()-1);
        return str;
    }
    
    //Get column count number
    public int getColumnCount()
    {
        if(!v.toString().equals("[]"))
        {
            if(v.get(0).toString().contains(","))
            {
                return v.get(0).toString().split(",").length;
            }
            else if(v.get(0).toString().trim().length()!=0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
            
        }
        else
        {
            return 0;
            
        }
    }
    
    //Get specific row and column value
    public String getValueAt(int row,int col)
    {
        String temp=null;
        int column=this.getColumnCount();
        if(column>=1)
        {
            temp=v.get(row).toString().split(",")[col];
        }
        else
        {
            temp=null;
        }
        return temp;
        
    }
    
    
//    public void insertRow(Vector v) throws IOException
//    {
//        BufferedWriter bw=new BufferedWriter(new FileWriter(this.fileName,true));
//        StringBuffer temp=new StringBuffer();
//        Iterator it=v.iterator();
//        temp.append(it.next().toString());
//        if(v.size()>1)
//        {
//            while(it.hasNext())
//            {
//                temp.append(","+it.next().toString());
//            }
//        }
//        bw.write(temp.toString());
//        bw.newLine();
//        bw.flush();
//        bw.close();
//    }
    
//    public void deleteRow(int index) throws IOException
//    {
//        v.remove(index);
//        
//        BufferedWriter bw=new BufferedWriter(new FileWriter(this.fileName));
//        for(Iterator it=v.iterator();it.hasNext();)
//        {
//            bw.write(it.next().toString());
//            bw.newLine();
//            
//        }
//        bw.flush();
//        bw.close();
//    }
    
    public void printAll()
    {
        Iterator it = v.iterator();
        while(it.hasNext())
        {
            System.out.println(it.next().toString());
        }
    }
    
    public void CsvClose() throws IOException
    {
        this.bufferedReader.close();
    }

	
	public static void main(String[] args) throws IOException {
		CsvTest cu=new CsvTest("zuzel.csv");
		Vector v=new Vector();
//        v.add("user5");
//        v.add("pwd5");
		
//		String s11=cu.getValueAt(2, 6);
//      System.out.println(s11);
        
        String s1 = null;
        for (int i = 0,j = 6;i<30; i++) {
			s1 = cu.getValueAt(i,j);
			System.out.println(s1);
		}
        
        String s2 = null;
        for (int i = 0, j = 13; i<30;i++) {
			s2 = cu.getValueAt(i, j);
			System.out.println(s2);
		}
        
        String s3 = null;
        for (int i = 0, j = 23; i < 30; i++) {
			s3 = cu.getValueAt(i, j);
			System.out.println(s3);
		}
        
//      String arr1=cu.getRowCount(0);
//      System.out.println(arr1);
        //System.out.println(cu.getColumn(5));
        
       //cu.insertRow(v);
        //cu.printAll();
		
	}
}
