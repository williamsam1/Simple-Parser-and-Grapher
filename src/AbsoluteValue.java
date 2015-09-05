
public class AbsoluteValue extends Function {
	
	public Function f1;
	
	public AbsoluteValue(Function a)
	{
		f1 = a.simplify();
	}
	
	public double eval(double x)
	{
		return (Math.abs(f1.eval(x)));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		
		if (n1 instanceof Constant)
			return (new Constant(eval(f1.eval(0))));
		else if (n1 instanceof Infinity || n1 instanceof NegInfinity)
			return (new Infinity());
		else
			return (new Indeterminate());
	}
	
	public Function der()
	{
		f1 = f1.simplify();
		return (new Divide(new Multiply(f1, f1.der()), new AbsoluteValue(f1))).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		if (f1 instanceof Constant)
		{
			return (new Constant(eval(0)));
		}
		else return (new AbsoluteValue(f1));
	}
	
	public String toString()
	{
		return ("abs("+f1.toString()+")");
	}

}
