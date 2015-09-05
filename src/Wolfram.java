import javax.swing.JOptionPane;


public class Wolfram 
{
	public static void main (String[] args)
	{
		Function func;
		while (true)
		{
			try 
			{
				String equation = JOptionPane.showInputDialog("Enter a Function:");
				func = Function.stringParse(equation).simplify();
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Function");
			}
		}
		System.out.println(func);
		System.out.println(func.eval(new Infinity()));
		
		
	}
}
