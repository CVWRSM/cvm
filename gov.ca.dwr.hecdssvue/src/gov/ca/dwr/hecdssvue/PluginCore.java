package gov.ca.dwr.hecdssvue;

import java.util.ArrayList;

public class PluginCore {
	public static String ID_DSSVue_DSSTableView="gov.ca.dwr.hecdssvue.views.DSSTableView";
	public static String ID_DSSVue_DSSCatalogView="gov.ca.dwr.hecdssvue.views.DSSCatalogView";
	
	public static boolean dssEditable=false;
	
	public static int chartType=0;
	public static ArrayList<Integer> months= new ArrayList<Integer>(){{
	    add(10);
	    add(11);
	    add(12);
	    add(1);
	    add(2);
	    add(3);
	    add(4);
	    add(5);
	    add(6);
	    add(7);
	    add(8);
	    add(9);
	}};
}
