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
        Vector<Float> environmentdata = new Vector<Float>();
        Vector<Integer> waterdata = new Vector<Integer>();
    	Vector<Float> apdsidata = new Vector<Float>();
		Vector<Waterpoint> waterpoints = new Vector<Waterpoint>();
    	int k=1;


    	try {
            s = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Alejandro\\workspace-mason\\TFM\\src\\pruebas\\Map.txt")));
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
        	s = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Alejandro\\workspace-mason\\TFM\\src\\pruebas\\water.txt")));
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
			if (meterNorth == 0) {System.out.println(i);System.out.println(meterNorth);System.out.println(meterEast);System.out.println(startDate);System.out.println(endDate);}
		}
		try {
        	s = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Alejandro\\workspace-mason\\TFM\\src\\pruebas\\adjustedPDSI.txt")));
        	apdsidata = new Vector<Float>();
        	while (s.hasNext()) {
        		apdsidata.add(Float.parseFloat(s.next()));
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
	        	s = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Alejandro\\workspace-mason\\TFM\\src\\pruebas\\model.demography.txt")));
	        	environmentdata = new Vector<Float>();
	        	while (s.hasNext()) {
	        		environmentdata.add(Float.parseFloat(s.next()));
	        		k++;
	        	}
	        }  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		
		//System.out.println(mapdata);
		
		//System.out.println(waterdata);
		System.out.println(waterpoints);

		//System.out.println(apdsidata);

		//System.out.println(environmentdata);

		//System.out.println(environmentdata.get(((1111 - 382)*15)+1));
		//System.out.println((int) Math.floor(45 + (37.6 + ((8982 - 7954) / 93.5))));

		//System.out.println(k);
	}

}
