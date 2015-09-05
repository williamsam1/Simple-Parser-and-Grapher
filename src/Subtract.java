
public class Subtract extends Function
{
	public Function f1;
	public Function f2;
	
	public Subtract(Function a, Function b)
	{
		f1 = a.simplify();
		f2 = b.simplify();
	}
	
	public double eval(double x)
	{
		return (f1.eval(x)-f2.eval(x));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		Number n2 = f2.eval(n);
		
		if ((n1 instanceof Indeterminate) || (n2 instanceof Indeterminate) || (n1 instanceof Infinity && n2 instanceof Infinity) || (n2 instanceof NegInfinity && n1 instanceof NegInfinity))
			return (new Indeterminate());
		else if (n1 instanceof Infinity || n2 instanceof NegInfinity)
			return (new Infinity());
		else if (n2 instanceof Infinity || n1 instanceof NegInfinity)
			return (new NegInfinity());
		else 
			return (new Constant(f1.eval(0) + f2.eval(0)));
	}
	
	public Function der()
	{
		return new Subtract(f1.der(), f2.der()).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		f2 = f2.simplify();
		if (f1 instanceof Constant && f2 instanceof Constant)
			return (new Constant(f1.eval(0) - f2.eval(0)));
		else if (f1 instanceof Constant && f1.eval(0) == 0.0)
			return (f2);
		else if (f2 instanceof Constant && f2.eval(0) == 0.0)
			return (f1);
		else if (f1 instanceof VariableReturn && f2 instanceof VariableReturn)
			return (new Constant(0));
		else
			return (new Subtract(f1, f2));
	}
	
	public String toString()
	{
		return (f1.toString() + " - " + f2.toString());
	}
}
