import javax.swing.JOptionPane;

public class SpecialTest
{
	public static void main(String[] args)
	{
		Function func;
		
		while (true)
		{
			try 
			{
				String equation = JOptionPane.showInputDialog("Enter a Function:");
				func = Function.stringParse(equation);
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Function");
			}
		}
		
		func = func.simplify();
		
		System.out.println("f(x) = " + func);
		System.out.println("f(inf) = " + func.eval(new Infinity()));
		System.out.println("f(-inf) = " + func.eval(new NegInfinity()));
		System.out.println("f(0) = " + func.eval(new Constant(0)));
		System.out.println("f(ind) = " + func.eval(new Indeterminate()));
	}
}
