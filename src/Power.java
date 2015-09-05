
public class Power extends Function
{
	public Function f1;
	public Function f2;
	
	public Power(Function a, Function b)
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
		return (Math.pow(f1.eval(x), f2.eval(x)));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		Number n2 = f2.eval(n);
		if (n1 instanceof Constant && ((Constant) n1).eval(0) == 0.0 && n2 instanceof Constant && ((Constant) n2).eval(0) == 0.0)
			return (new Indeterminate());
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) == 1.0 && n2 instanceof Infinity)
			return (new Constant(1));
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) > 0.0 && n2 instanceof NegInfinity)
			return (new Constant(0));
		else if (n1 instanceof Infinity && n2 instanceof Constant && ((Constant) n2).eval(0) == 0.0)
			return (new Indeterminate());
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) < 0.0 && n2 instanceof Infinity)
			return (new Indeterminate());
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) > 1.0 && n2 instanceof Infinity)
			return (new Infinity());
		else if (n1 instanceof Constant && ((Constant) n1).eval(0) < 1.0 && ((Constant) n1).eval(0) > 0 && n2 instanceof Infinity)
			return (new Constant(0));
		else if (n1 instanceof Infinity && n2 instanceof Constant && ((Constant) n2).eval(0) < 0.0)
			return (new Constant(0));
		else if (n1 instanceof Infinity && n2 instanceof Constant && ((Constant) n2).eval(0) > 0.0)
			return (new Infinity());
		else if (n1 instanceof NegInfinity && n2 instanceof Constant && ((Constant) n2).eval(0)%2 == 0)
			return (new Infinity());
		else if (n1 instanceof NegInfinity && n2 instanceof Constant && ((Constant) n2).eval(0)%2 == 1)
			return (new NegInfinity());
		else if (n1 instanceof Indeterminate || n2 instanceof Indeterminate)
			return (new Indeterminate());
		else 
			return (new Constant(Math.pow(f1.eval(0),f2.eval(0))));

	}
	
	public Function der()
	{
		f1.simplify();
		f2.simplify();
		if (f1 instanceof VariableReturn && f2 instanceof Constant)
			return (new Multiply(f2, new Power(new VariableReturn(), new Constant(f2.eval(0) - 1)))).simplify();
		else if (f1 instanceof Constant && f2 instanceof VariableReturn)
			return (new Multiply(new NaturalLog(new Constant(f1.eval(0))), new Power(f1, f2))).simplify();
		else if (f1 instanceof Constant)
			return (new Multiply(f2.der(), new Power(f1, f2))).simplify();
		return (new Multiply(new Power(f1, new Subtract(f2, new Constant(1))), new Add(new Multiply(f2, f1.der()), new Multiply(new Multiply(f1, f2.der()),new NaturalLog(f1))))).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		f2 = f2.simplify();
		if (f2 instanceof Constant && f2.eval(0) == 1.0)
			return (f1);
		else if (f2 instanceof Constant && f2.eval(0) == 0)
			return (new Constant(1));
		else if (f1 instanceof Constant && f2 instanceof Constant)
			return (new Constant(Math.pow(f1.eval(0),f2.eval(0))));
		return (new Power(f1, f2));
	}
	
	public String toString()
	{
		if (f2 instanceof Constant && !(f1 instanceof Constant))
			return ("(" + f1.toString()+ ")^" + f2.toString());
		else
			return ("(" + f1.toString() + ") ^ (" + f2.toString() + ")");
	}
}
