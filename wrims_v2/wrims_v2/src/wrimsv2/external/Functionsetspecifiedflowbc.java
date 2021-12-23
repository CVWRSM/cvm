package wrimsv2.external;

import java.util.*;

public class Functionsetspecifiedflowbc extends ExternalFunction{
	private final boolean DEBUG = false;


	public Functionsetspecifiedflowbc(){

	}

	public void execute(Stack stack) {

		//values in reverse order:
		Object param3 = stack.pop();
		Object param2 = stack.pop();
		Object param1 = stack.pop();

		//cast params to correct types:
		float Flow = ((Number) param3).floatValue();
		int Layer = ((Number) param2).intValue();
		int Node = ((Number) param1).intValue();

		float result = setspecifiedflowbc(Node, Layer, Flow);

		// push the result on the Stack
		stack.push(new Float(result));

	}

	public native float setspecifiedflowbc(int Node, int Layer, float Flow);
}
