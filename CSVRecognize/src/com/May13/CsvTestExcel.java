package com.May13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CsvTestExcel {
	
	//Declaration of reading stream
	private BufferedReader inStream = null;
	
	//Declaration of returning vector
	private Vector <String> vContent = null;
	 
	/**
	 * Constructor
	 * find the local csv File
	 * @param csvFileName
	 * @throws FileNotFoundException
	 */
	public CsvTestExcel(String csvFileName) throws FileNotFoundException{
		inStream = new BufferedReader(new FileReader("zuzel.csv"));
	}
	
	/**
	 * Return a row value of vector
	 * @return vContent
	 */
	public Vector<String> getVContent(){
		return this.vContent;
	}
	
	/**
	 * Read next row, fill in vector with this row's content
	 * @return vContent containing next line vContent
	 * @throws IOException
	 * @throws Exception
	 */
	public Vector<String> getLineContentVector() throws IOException, Exception{
		if (this.readCSVNextRecord()) {
			return this.vContent;
		}
		return null;
	}
	
	/**
	 * Close stream
	 */
	public void close(){
		if (inStream != null) {
			try {
				inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is used to read next logic row in csv file
	 * Content is stored in vector
	 * @return Return value is used to flag whether read to the end
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean readCSVNextRecord()throws IOException, Exception{
		//if stream is not initial, return false
		if (inStream == null) {
			return false;
		}
		
		//if vContent is not initial, then init vContent
		if (vContent == null) {
			vContent = new Vector<String>();
		}
		
		//remove former elements
		vContent.removeAllElements();
		
		//Declaration of logic line
		String logicLineStr = "";
		
		//Used to store rows which already read
		StringBuilder strb = new StringBuilder();
		
		//Declaration whether it is the flag of logic line
		boolean isLogicLine = false;
		
		try {
			while (!isLogicLine) {
				String newLineStr = inStream.readLine();
				if (newLineStr == null) {
					strb = null;
					vContent = null;
					isLogicLine = true;
					break;
				}
				
				if (!strb.toString().equals("")) {
					strb.append("/r/n");
				}
				
				strb.append(newLineStr);
				String oldLineString = strb.toString();
				
				if (oldLineString.indexOf(",") == -1) {
					if (containsNumber(oldLineString,"\"")%2 == 0) {
						isLogicLine = true;
						break;
					}
					else {
						if (oldLineString.startsWith("\"")) {
							if (oldLineString.equals("\"")) {
								continue;
							}else {
								String tempOldString = oldLineString.substring(1);
								if (isQuoteAdjacent(tempOldString)) {
									continue;
								}else {
									isLogicLine = true;
									break;
								}
							}
						}
					}
				}else {
					String tempOldLineStr = oldLineString.replace("\"\"","");
					int lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
					if (lastQuoteIndex == 0) {
						continue;
					}else if (lastQuoteIndex == -1) {
						isLogicLine = true;
						break;
					}else {
						tempOldLineStr = tempOldLineStr.replace("\",\"","");
						lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
						if (lastQuoteIndex == 0) {
							continue;
						}
						if (tempOldLineStr.charAt(lastQuoteIndex - 1) == ',') {
							continue;
						}else {
							isLogicLine = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (inStream != null) {
				inStream.close();
			}
			throw e;
		}
		
		if (strb == null) {
			return false;
		}
		
		logicLineStr = strb.toString();
		if (logicLineStr != null) {
			while (!logicLineStr.equals("")) {
				String[] ret = readAtomString(logicLineStr);
				String atomString = ret[0];
				logicLineStr = ret[1];
				vContent.add(atomString);
			}
		}
		
		return true;
	}
	
	/**
	 * Read one logic line's first string and return the rest string
	 * @param lineString
	 * @return first string and the rest logic line's content
	 */
	public String[] readAtomString(String lineString){
		String atomString = ""; //atom string willing to read
		String orgString = "";  //store original string
		String[] ret = new String[2]; //array to be returned
		boolean isAtom = false; //the flag of atom string
		String[] commaStr = lineString.split(",");
		while (!isAtom) {
			for (String str: commaStr) {
				if (!atomString.equals("")) {
					atomString = atomString + ",";
				}
				atomString = atomString + str;
				orgString = atomString;
				if (!isQuoteContained(atomString)) {
					isAtom = true;
					break;
				}else {
					if (!atomString.startsWith("\"")) {
						isAtom = true;
						break;
					}else if (atomString.startsWith("\"")) {
						if (containsNumber(atomString,"\"")%2 == 0) {
							String temp = atomString;
							if (temp.endsWith("\"")) {
								temp = temp.replace("\"\"", "");
								if (temp.equals("")) {
									atomString = "";
									isAtom = true;
									break;
								}else {
									temp = temp.substring(1, temp.lastIndexOf("\""));
									if (temp.indexOf("\"")>-1) {
										temp = atomString;
										temp = temp.substring(1);
										temp = temp.substring(0,temp.indexOf("\"")) + temp.substring(temp.indexOf("\"") + 1);
										atomString = temp;
										isAtom = true;
										break;
									}else {
										temp = atomString;
										temp = temp.substring(1,temp.lastIndexOf("\""));
										temp = temp.replace("\"\"", "\"");
										atomString = temp;
										isAtom = true;
										break;
									}
								}
							}else {
								temp = temp.substring(1,temp.indexOf("\"",1)) + temp.substring(temp.indexOf("\"",1) + 1);
								atomString = temp;
								isAtom = true;
								break;
							}
						}else {
							if (!atomString.equals("\"")) {
								String tempAtomStr = atomString.substring(1);
								if (!isQuoteAdjacent(tempAtomStr)) {
									tempAtomStr = atomString.substring(1);
									int tempQutoIndex = tempAtomStr.indexOf("\"");
									tempAtomStr = tempAtomStr.substring(0, tempQutoIndex) + tempAtomStr.substring(tempQutoIndex + 1);
									
									atomString = tempAtomStr;
									isAtom = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		
		if (lineString.length() > orgString.length()) {
			lineString = lineString.substring(orgString.length());
		}else {
			lineString = "";
		}
		
		if (lineString.startsWith(",")) {
			if (lineString.length() > 1) {
				lineString = lineString.substring(1);
			}else {
				lineString = "";
			}
		}
		ret[0] = atomString;
		ret[1] = lineString;
		
		return ret;
	}
	
	public int containsNumber(String parentStr, String parameter){
		int containNumber = 0;
		if (parentStr == null || parentStr.equals("")) {
			return 0;
		}
		if (parameter == null || parameter.equals("")) {
			return 0;
		}
		for (int i = 0; i < parentStr.length(); i++) {
			i = parentStr.indexOf(parameter, i);
			if (i > -1) {
				i = i + parameter.length();
				i--;
				containNumber = containNumber + 1;
			}else {
				break;
			}
		}
		return containNumber;
	}
	
	public boolean isQuoteAdjacent(String p_String){
		boolean ret = false;
		String temp = p_String;
		temp = temp.replace("\"\"", "");
		if (temp.indexOf("\"")== -1) {
			ret = true;
		}
		return ret;
	}
	
	public boolean isQuoteContained(String p_String){
		boolean ret = false;
		if (p_String == null || p_String.equals("")) {
			return false;
		}
		if (p_String.indexOf("\"") > -1) {
			ret = true;
		}
		return ret;
	}
	
	public boolean readCSVFileTitle() throws IOException, Exception{
		String strValue = "";
		boolean isLineEmpty = true;
		do{
			if (!readCSVNextRecord()) {
				return false;
			}
			if (vContent.size() > 0) {
				strValue = (String) vContent.get(0);
			}
			for (String str: vContent) {
				if (str != null && !str.equals("")) {
					isLineEmpty = false;
					break;
				}
			}
		} while(strValue.trim().startsWith("#") || isLineEmpty);
			return true;
	}

}
