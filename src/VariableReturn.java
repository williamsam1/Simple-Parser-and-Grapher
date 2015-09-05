
public class VariableReturn extends Function {
	
	public double eval(double x)
	{
		return x;
	}
	
	public Number eval(Number n)
	{
		return n;
	}
	
	public Function der()
	{
		return (new Constant(1));
	}
	
	public Function simplify()
	{
		return this;
	}
	
	public String toString()
	{
		return ("x");
	}

}
