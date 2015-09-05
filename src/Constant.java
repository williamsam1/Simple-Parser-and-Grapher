
public class Constant extends Function implements Number
{
	double c;
	
	public Constant(double con)
	{
		c = con;
	}
	
	public double eval(double x)
	{
		return c;
	}
	
	public Number eval(Number n)
	{
		return this;
	}
	
	public Function der()
	{
		return new Constant(0);
	}
	
	public Function simplify()
	{
		return this;
	}
	
	public String toString()
	{
		return (Double.toString(c));
	}
}
