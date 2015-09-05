import javax.swing.JOptionPane;

public class DerTest
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
		Function f1 = func.simplify();
		Function f2 = f1.der(1);
		System.out.println("f(x) =x " + f1);
		System.out.println("f'(x) = " + f2);
		
		Function.graph(new Function[] {f1, f2});
		
	}
}
