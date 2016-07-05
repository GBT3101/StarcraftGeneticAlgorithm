package gPDataTypes;

import ec.gp.GPData;

public class IntData extends GPData
{
	public int x;    // return value
	
	public void copyTo(final GPData gpd)   // copy my stuff to another DoubleData
	    { ((IntData)gpd).x = x; }
}
