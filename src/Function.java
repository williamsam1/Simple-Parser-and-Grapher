import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public abstract class Function 
{
	public abstract double eval(double x);
	
	public abstract Number eval (Number n);
	
	public abstract Function der();
	
	public abstract Function simplify();
	
	public Function der(int n)
	{
		Function f = this;
		for (int i = 0; i < n; i++)
		{
			f = f.der().simplify();
		}
		return f;
	}
	
	public boolean equals(Function f)
	{
		if (this instanceof Add && f instanceof Add)
		{
			Add f1 = (Add)this;
			Add f2 = (Add)f;
			return (f1.f1.equals(f2.f1) && f1.f2.equals(f2.f2));
		}
		else if (this instanceof Subtract && f instanceof Subtract)
		{
			Subtract f1 = (Subtract)this;
			Subtract f2 = (Subtract)f;
			return (f1.f1.equals(f2.f1) && f1.f2.equals(f2.f2));
		}
		else if (this instanceof Multiply && f instanceof Multiply)
		{
			Multiply f1 = (Multiply)this;
			Multiply f2 = (Multiply)f;
			return (f1.f1.equals(f2.f1) && f1.f2.equals(f2.f2));
		}
		else if (this instanceof Divide && f instanceof Divide)
		{
			Divide f1 = (Divide)this;
			Divide f2 = (Divide)f;
			return (f1.f1.equals(f2.f1) && f1.f2.equals(f2.f2));
		}
		else if (this instanceof Power && f instanceof Power)
		{
			Power f1 = (Power)this;
			Power f2 = (Power)f;
			return (f1.f1.equals(f2.f1) && f1.f2.equals(f2.f2));
		}
		else if (this instanceof Cosine && f instanceof Cosine)
		{
			Cosine f1 = (Cosine)this;
			Cosine f2 = (Cosine)f;
			return (f1.f1.equals(f2.f1));
		}
		else if (this instanceof Sine && f instanceof Sine)
		{
			Sine f1 = (Sine)this;
			Sine f2 = (Sine)f;
			return (f1.f1.equals(f2.f1));
		}
		else if (this instanceof NaturalLog && f instanceof NaturalLog)
		{
			NaturalLog f1 = (NaturalLog)this;
			NaturalLog f2 = (NaturalLog)f;
			return (f1.f1.equals(f2.f1));
		}
		else if (this instanceof Constant && f instanceof Constant)
		{
			return (f.eval(0) == eval(0));
		}
		else if (this instanceof AbsoluteValue && f instanceof AbsoluteValue)
		{
			AbsoluteValue f1 = (AbsoluteValue)this;
			AbsoluteValue f2 = (AbsoluteValue)f;
			return (f1.f1.equals(f2.f1));
		}
		else if (this instanceof VariableReturn && f instanceof VariableReturn)
			return true;
		return false;
	}
	
	public static Function stringParse(String s)
	{	
		s = removeSpaces(s);
		if (s.equals("x"))
			return (new VariableReturn());
		else if (s.equals("e"))
			return (new E());
		
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
		
		if (s.length() > 5 && s.substring(0, 4).equals("abs(") && s.charAt(s.length() - 1) == ')')
		{
			return (new AbsoluteValue(stringParse(s.substring(4, s.length()-1))));
		}
		
		if (s.length() > 4 && s.substring(0, 3).equals("ln(") && s.charAt(s.length() - 1) == ')')
		{
			return (new NaturalLog(stringParse(s.substring(3, s.length()-1))));
		}
			
		return (new Constant(Double.parseDouble(s)));
	}
	
	private static ArrayList<Integer> range(String s)
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
	
	private static String negate(String s)
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
	
	private static String removeSpaces(String s)
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
	
	public static void graph(Function[] f)
	{
		graph(new ArrayList<Function>(Arrays.asList(f)));
	}
	
	public static void graph(ArrayList<Function> fl)
	{
		ArrayList<Function> l = fl;
		Double xmin;
		Double xmax = Double.NEGATIVE_INFINITY;
		Double ymin;
		Double ymax = Double.NEGATIVE_INFINITY;
		
final class Program extends JPanel{
			
			/**
	 * 
	 */
	private static final long serialVersionUID = 6093719617903696574L;
			ArrayList<Function> nf;
			Double xmin;
			Double xmax;
			Double ymin;
			Double ymax;
			Double tickSpace;
			
			
			public Program(ArrayList<Function> nf, Double xmi, Double xma, Double ymi, Double yma, double tickSize)
			{
				this.nf = nf;
				xmin = xmi;
				xmax = xma;
				ymin = ymi;
				ymax = yma;
				tickSpace = tickSize;
			}
			
			public void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				this.setBackground(Color.WHITE);
				
				g.setColor(Color.BLACK);
				g.drawLine(mapX(xmin), mapY(0.0), mapX(xmax), mapY(0.0));
				g.drawLine(mapX(0), mapY(ymin), mapX(0), mapY(ymax));
				
				
				for (Function f : nf)
				{

				int lastY = mapY(f.eval(xmin));
				
				for (int i = 1; i < getWidth(); i++)
				{
					int newY = mapY(f.eval(invmapX(i)));
					g.drawLine(i-1, lastY, i, newY);
					lastY = newY;
				}
				
				
				
				double x = 0;
				while (x <= xmax)
				{
					x += tickSpace;
					g.drawLine(mapX(x), mapY(0)-10, mapX(x), mapY(0)+10);
				}
				
				x = 0;
				while (x >= xmin)
				{
					x -= tickSpace;
					g.drawLine(mapX(x), mapY(0)-10, mapX(x), mapY(0)+10);
				}
				
				
				double y = 0;
				while (y <= xmax)
				{
					y += tickSpace;
					g.drawLine(mapX(0)-10, mapY(y), mapX(0)+10, mapY(y));
				}
				
				y= 0;
				while (y >= xmin)
				{
					y -= tickSpace;
					g.drawLine(mapX(0)-10, mapY(y), mapX(0)+10, mapY(y));
				}	
				}
				
			}
			
			public int mapX (double x)
			{
				return (int)((getWidth()/(xmax-xmin))*(x-xmin));
			}
			
			public double invmapX (int x)
			{
				return (x*(xmax-xmin)/getWidth()+xmin);
			}
			
			public int mapY (double y)
			{
				return (int)((-1*getHeight()/(ymax-ymin))*(y-ymax));
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
		
		JFrame w = new JFrame("Graph Window");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Program p = new Program(l, xmin, xmax, ymin, ymax, 1);
		w.add(p);
		w.setSize(750, 500);
		w.setVisible(true);

	}
	
	public static void graph(Function f)
	{
		Double xmin;
		Double xmax = Double.NEGATIVE_INFINITY;
		Double ymin;
		Double ymax = Double.NEGATIVE_INFINITY;
		
final class Program extends JPanel{
			
			/**
	 * 
	 */
	private static final long serialVersionUID = -3861785431575884073L;
			Function f;
			Double xmin;
			Double xmax;
			Double ymin;
			Double ymax;
			Double tickSpace;
			
			
			public Program(Function f, Double xmi, Double xma, Double ymi, Double yma, double tickSize)
			{
				this.f = f;
				xmin = xmi;
				xmax = xma;
				ymin = ymi;
				ymax = yma;
				tickSpace = tickSize;
			}
			
			public void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				this.setBackground(Color.WHITE);
				
				g.setColor(Color.BLACK);
				g.drawLine(mapX(xmin), mapY(0.0), mapX(xmax), mapY(0.0));
				g.drawLine(mapX(0), mapY(ymin), mapX(0), mapY(ymax));
				
				
			

				int lastY = mapY(f.eval(xmin));
				
				for (int i = 1; i < getWidth(); i++)
				{
					int newY = mapY(f.eval(invmapX(i)));
					g.drawLine(i-1, lastY, i, newY);
					lastY = newY;
				}
				
				
				
				double x = 0;
				while (x <= xmax)
				{
					x += tickSpace;
					g.drawLine(mapX(x), mapY(0)-10, mapX(x), mapY(0)+10);
				}
				
				x = 0;
				while (x >= xmin)
				{
					x -= tickSpace;
					g.drawLine(mapX(x), mapY(0)-10, mapX(x), mapY(0)+10);
				}
				
				
				double y = 0;
				while (y <= xmax)
				{
					y += tickSpace;
					g.drawLine(mapX(0)-10, mapY(y), mapX(0)+10, mapY(y));
				}
				
				y= 0;
				while (y >= xmin)
				{
					y -= tickSpace;
					g.drawLine(mapX(0)-10, mapY(y), mapX(0)+10, mapY(y));
				}	
				
				
			}
			
			public int mapX (double x)
			{
				return (int)((getWidth()/(xmax-xmin))*(x-xmin));
			}
			
			public double invmapX (int x)
			{
				return (x*(xmax-xmin)/getWidth()+xmin);
			}
			
			public int mapY (double y)
			{
				return (int)((-1*getHeight()/(ymax-ymin))*(y-ymax));
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
		
		JFrame w = new JFrame("Graph Window");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Program p = new Program(f, xmin, xmax, ymin, ymax, 1);
		w.add(p);
		w.setSize(750, 500);
		w.setVisible(true);

	}
	

}
