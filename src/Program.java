import java.awt.*;
import javax.swing.*;


public class Program extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2869878079810227508L;
	Function f;
	Double xmin;
	Double xmax;
	Double ymin;
	Double ymax;
	Double tickSpace;
	
	
	public Program(Function nf, Double xmi, Double xma, Double ymi, Double yma, double tickSize)
	{
		f = nf;
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
