
public class Sine extends Function {
	
	public Function f1;
	
	public Sine(Function a)
	{
		f1 = a.simplify();
	}
	
	public double eval(double x)
	{
		return (Math.sin(f1.eval(x)));
	}
	
	public Number eval(Number n)
	{
		Number n1 = f1.eval(n);
		
		if (n1 instanceof Constant)
			return (new Constant(eval(f1.eval(0))));
		else
			return (new Indeterminate());
	}
	
	public Function der()
	{
		return (new Multiply(f1.der(), new Cosine(f1))).simplify();
	}
	
	public Function simplify()
	{
		f1 = f1.simplify();
		if (f1 instanceof Constant)
		{
			return (new Constant(eval(0)));
		}
		else return (new Sine(f1));
	}
	
	public String toString()
	{
		return ("sin("+f1.toString()+")");
	}

}
