
public class NaturalLog extends Function {
	
	public Function f1;
	
	public NaturalLog(Function a)
	{
		f1 = a.simplify();
	}
	
	public double eval(double x)
	{
		return (Math.log(f1.eval(x)));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		
		if (n1 instanceof Infinity || n1 instanceof NegInfinity)
			return (new Infinity());
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) == 0.0)
			return (new NegInfinity());
		else if (n1 instanceof Indeterminate)
			return (new Indeterminate());
		else 
			return (new Constant(eval(f1.eval(0))));
	}
	
	public Function der()
	{
		return (new Divide(f1.der(), f1)).simplify();
	}
	
	public Function simplify()
	{
		if (f1.eval(0) == Math.E)
			return (new Constant(1));
		return (new NaturalLog(f1.simplify()));
	}
	
	public String toString()
	{
		return ("ln("+f1.toString()+")");
	}

}
