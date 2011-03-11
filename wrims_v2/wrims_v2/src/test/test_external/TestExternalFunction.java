package test.test_external;

import org.testng.annotations.*;
import org.testng.Assert;

import wrimsv2.external.*;

import java.util.*;


public class TestExternalFunction {

	@Test
	public void sample()
	{
		Assert.assertEquals(1,1);
        System.out.println("@Test sample: 1==1");
	}

	@Test
	public void testAnnEC(){
		
		new LoadInterfaceDll("InterfaceToAnn.dll");
		
		ExternalFunctionTable eft=new ExternalFunctionTable();
		ExternalFunction ef=eft.externalFunctionsHashtable.get("annec");
		Stack stack= new Stack();
		stack.add(81183.7421875);
		stack.add(57666.609375);
		stack.add(33755.21875);
		stack.add(35595.9726563);
		stack.add(12703.9482422);
		stack.add(11567.1210938);
		stack.add(10971.9179688); 
		stack.add(1835.09558105);
		stack.add(2110.24755859);
		stack.add(6749.43554688);
		stack.add(11402.3476563);
		stack.add(10534.6083984);
		stack.add(7340.38232422); 
		stack.add(8440.99023438);
		stack.add(5723.10986328);
		stack.add(0.0);
		stack.add(0.0);
		stack.add(0.0);
		stack.add(0.0);
		stack.add(26.0);
		stack.add(-3725.58007813);
		stack.add(183.309997559);
		stack.add(1131.44995117);
		stack.add(1359.26000977);
		stack.add(3874.82983398); 
		stack.add(52724.8515625);
		stack.add(6683.39941406);
		stack.add(2338.921875);
		stack.add(4785.66699219);
		stack.add(753.499328613);
		stack.add(77.187461853);
		stack.add(79.5642929077);
		stack.add(0.0);
		stack.add(149.219497681);
		stack.add(343.878936768);
		stack.add(331.338287354);
		stack.add(337.781890869);
		stack.add(358.810211182);
		stack.add(311.293121338);
		stack.add(470.136566162); 
		stack.add(29);
		stack.add(31);
		stack.add(30);
		stack.add(31);
		stack.add(30);
		stack.add(3);
		stack.add(1);
		stack.add(7);
		stack.add(1996);
		ef.execute(stack);
		
		if (stack.size()!=1){
			System.out.println("stack size <>1");
		}else{
			System.out.println(((Number) stack.pop()).floatValue());
		}
		
		stack = new Stack();
		stack.add(57666.609375);
		stack.add(33755.21875);
		stack.add(35595.9726563);
		stack.add(12703.9482422);
		stack.add(21248.9335938);
		stack.add(10971.9179688); 
		stack.add(1835.09558105);
		stack.add(2110.24755859);
		stack.add(6749.43554688);
		stack.add(11190.7050781);
		stack.add(10534.6083984);
		stack.add(7340.38232422);
		stack.add(8440.99023438); 
		stack.add(5723.10986328);
		stack.add(2537.1784668);
		stack.add(0.0);
		stack.add(0.0);
		stack.add(0.0);
		stack.add(26.0);
		stack.add(31.0);
		stack.add(183.309997559);
		stack.add(1131.44995117);
		stack.add(1359.26000977);
		stack.add(3874.82983398);
		stack.add(4374.95019531);
		stack.add(6683.39941406);
		stack.add(2338.921875);
		stack.add(4785.66699219);
		stack.add(753.499328613);
		stack.add(0.0);
		stack.add(79.5642929077);
		stack.add(0.0);
		stack.add(149.219497681);
		stack.add(343.878936768);
		stack.add(256.333435059); 
		stack.add(337.781890869);
		stack.add(358.810211182);
		stack.add(311.293121338);
		stack.add(470.136566162);
		stack.add(602.596130371);
		stack.add(31);
		stack.add(30);
		stack.add(31);
		stack.add(30);
		stack.add(31);
		stack.add(3);
		stack.add(1);
		stack.add(8);
		stack.add(1996);
		
		ef.execute(stack);
		
		if (stack.size()!=1){
			System.out.println("stack size <>1");
		}else{
			System.out.println(((Number) stack.pop()).floatValue());
		}
	}
	
	@Test
	public void testANNLineGen(){
		new LoadInterfaceDll("InterfaceToAnn.dll");		
		ExternalFunctionTable eft=new ExternalFunctionTable();
		ExternalFunction ef=eft.externalFunctionsHashtable.get("annlinegen");
		
		Stack stack= new Stack();
		stack.add(6472.58740234);
		stack.add(8065.77587891);
		stack.add(9674.55859375);
		stack.add(7614.22070313); 
		stack.add(1844.08239746);
		stack.add(829.421325684);
		stack.add(0.0);
		stack.add(2808.64648438);  
		stack.add(1565.21533203);
		stack.add(799.454162598);
		stack.add(645.070129395);
		stack.add(816.196533203);
		stack.add(1269.01464844); 
		stack.add(0.0);
		stack.add(26.0);
		stack.add(31.0);
		stack.add(31.0);
		stack.add(30.0);
		stack.add(1061.79003906);
		stack.add(3956.91992188);
		stack.add(3897.29638672);
		stack.add(2362.15014648);
		stack.add(1330.7467041);
		stack.add(200.450531006);
		stack.add(241.239242554);
		stack.add(0.0);
		stack.add(54.8862113953);
		stack.add(-141); 
		stack.add(330.817657471);
		stack.add(365.760253906);
		stack.add(384.316558838);
		stack.add(375.362304688);
		stack.add(346.135650635); 
		stack.add(603.290405273);
		stack.add(648.479736328);
		stack.add(647.118896484);
		stack.add(647.933044434);
		stack.add(637.05291748); 
		stack.add(31);
		stack.add(30);
		stack.add(31);
		stack.add(31);
		stack.add(30);
		stack.add(964.91);
		stack.add(10000);
		stack.add(12000);
		stack.add(2);
		stack.add(2); 
		stack.add(1);
		stack.add(12);
		stack.add(1990);
		stack.add(3);
		ef.execute(stack);
		
		if (stack.size()!=1){
			System.out.println("stack size <>1");
		}else{
			System.out.println(((Number) stack.pop()).floatValue());
		}		
	}
}