package pruebas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

public class FilePruebas {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = null;
        Vector<Integer> mapdata = new Vector<Integer>();
        StringBuffer environmentdata = new StringBuffer();
        StringBuffer waterdata = new StringBuffer();
    	StringBuffer apdsidata = new StringBuffer();


    	try {
            s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/Map.txt")));
            mapdata = new Vector<Integer>();
            
            while (s.hasNext()) {
                mapdata.add(s.nextInt());
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		try {
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/water.txt")));
        	waterdata = new StringBuffer();
        	int cont = 1;
        	while (s.hasNext()) {
        		waterdata.append(s.next());
        		if (cont%6 == 0 && s.hasNext()) {
        			waterdata.append("/");
        		}
        		else if (s.hasNext()) {
                	waterdata.append(" ");
                }
        		cont++;
        	}
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		try {
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/adjustedPDSI.txt")));
        	apdsidata = new StringBuffer();
        	while (s.hasNext()) {
        		apdsidata.append(s.next());
        		if (s.hasNext()) {
        			apdsidata.append(" ");
                }
        	}
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		try {
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/environment.txt")));
        	environmentdata = new StringBuffer();
        	int cont = 1;
        	while (s.hasNext()) {
        		environmentdata.append(s.next());
        		if (cont%15 == 0 && s.hasNext()) {
        			environmentdata.append("/");
        		}
        		else if (s.hasNext()) {
        			environmentdata.append(" ");
                }
        		cont++;
        	}
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		
		System.out.println(mapdata);
		
		System.out.println(waterdata);

		System.out.println(apdsidata);

		//System.out.println(environmentdata);
	}

}
