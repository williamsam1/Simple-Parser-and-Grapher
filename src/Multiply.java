
public class Multiply extends Function
{
	public Function f1;
	public Function f2;
	
	public Multiply(Function a, Function b)
	{
		f1 = a.simplify();
		f2 = b.simplify();
	}
	
	public Function f1()
	{
		return f1;
	}
	
	public Function f2()
	{
		return f2;
	}
	
	public double eval(double x)
	{
		return (f1.eval(x)*f2.eval(x));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		Number n2 = f2.eval(n);
	
		if (n1 instanceof Indeterminate || n2 instanceof Indeterminate)
			return (new Indeterminate());
		else if ((n1 instanceof Infinity && n2 instanceof Infinity) || (n1 instanceof NegInfinity && n2 instanceof NegInfinity))
			return (new Infinity());
		else if ((n1 instanceof Infinity && n2 instanceof NegInfinity) || (n1 instanceof NegInfinity && n2 instanceof Infinity))
			return (new NegInfinity());
		else if (n1 instanceof Infinity && n2 instanceof Constant)
			{
				if (f2.eval(0) > 0)
					return (new Infinity());
				else if (f2.eval(0) < 0)
					return (new NegInfinity());
				else return (new Indeterminate());
			}
		else if (n1 instanceof NegInfinity && n2 instanceof Constant)
		{
			if (f2.eval(0) > 0)
				return (new NegInfinity());
			else if (f2.eval(0) < 0)
				return (new Infinity());
			else return (new Indeterminate());
		}
		else if (n2 instanceof Infinity && n1 instanceof Constant)
		{
			if (f1.eval(0) > 0)
				return (new Infinity());
			else if (f1.eval(0) < 0)
				return (new NegInfinity());
			else return (new Indeterminate());
		}
		else if (n2 instanceof NegInfinity && n1 instanceof Constant)
		{
			if (f1.eval(0) > 0)
				return (new NegInfinity());
			else if (f1.eval(0) < 0)
				return (new Infinity());
			else return (new Indeterminate());
		}
		else 
			return (new Constant(f1.eval(0) * f2.eval(0)));
			
	}
	
	public Function der()
	{
		return (new Add(new Multiply(f1, f2.der()), new Multiply(f2, f1.der()))).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		f2 = f2.simplify();
		if ((f1 instanceof Constant && f1.eval(0) == 0.0) || (f2 instanceof Constant && f2.eval(0) == 0.0))
			return (new Constant (0));
		else if (f1 instanceof Constant && f1.eval(0) == 1)
			return (f2);
		else if (f2 instanceof Constant && f2.eval(0) == 1)
			return (f1);
		else if (f1 instanceof VariableReturn && f2 instanceof VariableReturn)
			return (new Power(new VariableReturn(), new Constant(2)));
		else if (f1 instanceof Constant && f2 instanceof Constant)
			return (new Constant(f1.eval(0)*f2.eval(0)));
		else
			return (new Multiply(f1, f2));
	}
	
	public String toString()
	{
		/*
		if (f1 instanceof Constant && !(f2 instanceof Constant))
		{
			if (f1.eval(0) == -1.0)
				return ("-(" + f2.toString() + ")");
			else	
				return (f1.toString() + "(" + f2.toString() + ")");
		}
		else if (f2 instanceof Constant && !(f1 instanceof Constant))
			return (f2.toString() + "(" + f1.toString() + ")");
			*/
		return ("("+f1.toString()+") * ("+f2.toString()+")");
	}
}
