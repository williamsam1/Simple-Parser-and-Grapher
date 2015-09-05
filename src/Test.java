import java.util.*;
import javax.swing.*;


public class Test 
{
	public static void main(String[] args)
	{
		Function func;
		Double xmin;
		Double xmax = Double.NEGATIVE_INFINITY;
		Double ymin;
		Double ymax = Double.NEGATIVE_INFINITY;
		
		while (true)
		{
			try 
			{
				String equation = JOptionPane.showInputDialog("Enter a Function:");
				func = stringParse(equation);
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Function");
				System.exit(1);
			}
			catch (NullPointerException e) {
				
			}
		}

		while (true)
		{
			try 
			{
				xmin = Double.parseDouble(JOptionPane.showInputDialog("Enter Lower X Bound:"));
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Number");
			}
		}
		
		while (true)
		{
			try 
			{
				while (xmax <= xmin)
				{
				xmax = Double.parseDouble(JOptionPane.showInputDialog("Enter Upper X Bound:"));
				if (xmax <= xmin)
					JOptionPane.showMessageDialog(null, "Error: Upper x bound must be greater than lower x bound\n Your lower bound: " + xmin);
				}
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Number");
			}
		}
		
		while (true)
		{
			try 
			{
				ymin = Double.parseDouble(JOptionPane.showInputDialog("Enter Lower Y Bound:"));
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Number");
			}
		}
		
		while (true)
		{
			try 
			{
				while (ymax <= ymin)
				{
				ymax = Double.parseDouble(JOptionPane.showInputDialog("Enter Upper Y Bound:"));
				if (ymax <= ymin)
					JOptionPane.showMessageDialog(null, "Error: Upper y bound must be greater than lower y bound\n Your lower bound: " + ymin);
				}
				break;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error with Number");
			}
		}
		
		JFrame f = new JFrame("Graph Window");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Program p = new Program(func, xmin, xmax, ymin, ymax, 1);
		f.add(p);
		f.setSize(750, 500);
		f.setVisible(true);
	}

	public static Function stringParse(String s)
	{	
		if (s.equals("x"))
			return (new VariableReturn());
		
		if (s.equals("e"))
			return new E();
		
		ArrayList<Integer> range = range(s);
		
		for (Integer i : range)
		{
			if (s.charAt(i) == '-' && i != 0)
			{
				return (new Subtract(stringParse(s.substring(0, i)), stringParse(negate(s.substring(i+1, s.length())))));
			}
			

			else if (s.charAt(i) == '+')
			{
				return (new Add(stringParse(s.substring(0, i)), stringParse(s.substring(i+1, s.length()))));
			}
		}
		
		for (Integer i : range)
		{
			if (s.charAt(i) == '/')
			{
				return (new Divide(stringParse(s.substring(0, i)), stringParse(s.substring(i+1, s.length()))));
			}
			
			else if (s.charAt(i) == '*')
				return (new Multiply(stringParse(s.substring(0, i)), stringParse(s.substring(i+1, s.length()))));		
		}
		
		for (Integer i : range)
		{
			if (s.charAt(i) == '^')
			{
				return (new Power(stringParse(s.substring(0, i)), stringParse(s.substring(i+1, s.length()))));
			}
		}
		
		if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')')
			return (stringParse(s.substring(1, s.length()-1)));

		if (s.length() > 5 && s.substring(0, 4).equals("sin(") && s.charAt(s.length() - 1) == ')')
		{
			return (new Sine(stringParse(s.substring(4, s.length()-1))));
		}
		
		if (s.length() > 5 && s.substring(0, 4).equals("cos(") && s.charAt(s.length() - 1) == ')')
		{
			return (new Cosine(stringParse(s.substring(4, s.length()-1))));
		}
		
		if (s.length() > 4 && s.substring(0, 3).equals("ln(") && s.charAt(s.length() - 1) == ')')
		{
			return (new NaturalLog(stringParse(s.substring(3, s.length()-1))));
		}
			
		return (new Constant(Integer.parseInt(s)));
	}
	
	public static String removeSpaces(String s)
	{
		int i = 0; 
		while (i < s.length())
		{
			if (s.charAt(i) == ' ')
			{
				s = s.substring(0, i) + s.substring(i+1);
				i--;
			}
			i++;
		}
		return s;
	}
	
	public static ArrayList<Integer> range(String s)
	{
		ArrayList<Integer> index = new ArrayList<Integer>();
		int paren = 0;
		for (int i = s.length() - 1; i >= 0; i--)
		{
			if (s.charAt(i) == '(')
				paren++;
			
			if (paren >= 0)
				index.add(i);
			
			if (s.charAt(i) == ')')
				paren--;
		}
		return index;
	}
	
	public static String negate(String s)
	{
		for (Integer i : range(s))
		{
			if (s.charAt(i) == '+')
				s = s.substring(0, i) + '-' + s.substring(i+1);
			else if (s.charAt(i) == '-')
				s = s.substring(0, i) + '+' + s.substring(i+1);
		}
		return s;
	}
	

}


