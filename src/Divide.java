
public class Divide extends Function
{
	public Function f1;
	public Function f2;
	
	public Divide(Function a, Function b)
	{
		f1 = a.simplify();
		f2 = b.simplify();
	}
	
	public double eval(double x)
	{
		return (f1.eval(x)/f2.eval(x));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		Number n2 = f2.eval(n);
	
		if ((n1 instanceof Indeterminate || n2 instanceof Indeterminate) || (n1 instanceof Infinity && n2 instanceof Infinity) || (n1 instanceof Infinity && n2 instanceof NegInfinity) || (n2 instanceof Infinity && n1 instanceof Infinity) || (n2 instanceof Infinity && n1 instanceof NegInfinity))
			return (new Indeterminate());
		else if (n1 instanceof Infinity)
			return (new Infinity());
		else if (n1 instanceof NegInfinity)
			return (new NegInfinity());
		else if (n2 instanceof Infinity || n2 instanceof NegInfinity)
			return (new Constant(0));
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) != 0.0 && n2 instanceof Constant && ((Constant) n2).eval(0) == 0.0)
			return (new Indeterminate());
		else 
			return (new Constant(f1.eval(0) / f2.eval(0)));
	}
	
	public Function der()
	{
		return (new Divide(new Subtract(new Multiply(f2, f1.der()), new Multiply(f1, f2.der())), new Power(f2, new Constant(2)))).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		f2 = f2.simplify();
		if (f1.equals(f2))
			return (new Constant(1));
		if (f1 instanceof Constant && f1.eval(0) == 0.0)
			return (new Constant (0));
		else if (f2 instanceof Constant && f2.eval(0) == 1)
			return (f1);
		else if (f1 instanceof VariableReturn && f2 instanceof VariableReturn)
			return (new Constant(1));
		else if (f1 instanceof Power && f2 instanceof Power && (((Power)f1)).f1() instanceof VariableReturn && (((Power)f1)).f2() instanceof Constant && (((Power)f2)).f1() instanceof VariableReturn && (((Power)f2)).f2() instanceof Constant)
			return (new Power(new VariableReturn(), new Constant((((Power)f1)).f2().eval(0) - (((Power)f2)).f2().eval(0))));
		else if (f1 instanceof Constant && f2 instanceof Constant )
		{
			double a = f1.eval(0);
			double b = f2.eval(0);
			double t;
		    while (b != 0)
		    {
		       t = b;
		       b = a%b;
		       a = t;
		    }
		    return (new Divide(new Constant(f1.eval(0)/a), new Constant(f2.eval(0)/a)));
		}
		else
			return (new Divide(f1, f2));
	}
	
	public String toString()
	{
		return ("("+f1.toString()+") / ("+f2.toString()+")");
	}
}
