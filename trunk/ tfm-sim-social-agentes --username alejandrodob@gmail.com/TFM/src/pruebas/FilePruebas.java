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
        Vector<Integer> waterdata = new Vector<Integer>();
    	StringBuffer apdsidata = new StringBuffer();
		Vector<Waterpoint> waterpoints = new Vector<Waterpoint>();


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
        	waterdata = new Vector<Integer>();
        	while (s.hasNext()) {
        		waterdata.add(s.nextInt());

        	}
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		
		//create the waterpoints
		int i = 0;
		while (i+6 <= waterdata.size()) {
			int sarg = waterdata.get(i); i++;
			int meterNorth = waterdata.get(i); i++;
			int meterEast = waterdata.get(i); i++;
			int typeWater = waterdata.get(i); i++;
			int startDate = waterdata.get(i); i++;
			int endDate = waterdata.get(i); i++;
			int x = (int) (25 + ((meterEast - 2392) / 93.5));
			int y = (int) Math.floor(45 + (37.6 + ((meterNorth - 7954) / 93.5)));
			waterpoints.add(new Waterpoint(x,y,sarg,meterNorth,meterEast,typeWater,startDate,endDate));
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
		
		//System.out.println(mapdata);
		
		System.out.println(waterdata);
		System.out.println(waterpoints);

		//System.out.println(apdsidata);

		//System.out.println(environmentdata);
	}

}
