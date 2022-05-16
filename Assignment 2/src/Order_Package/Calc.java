package Order_Package;

import java.math.RoundingMode;
import java.text.DecimalFormat;

//Written by Azim Amizie Bin Saharen B032110027

public class Calc
{
	
		public Double calcTotal (int quantity, Double price)
		{

			double subtotal = 0;
			subtotal = quantity*price;
			return subtotal;
		}
		
		public Double roundTotal (double raw_total)
		{

			double factor = (double) Math.pow(10, 1);
		    double price = raw_total * factor;
		    double tmp = Math.round(price);
			double answer=tmp/factor;
			double difference=answer-raw_total;
			DecimalFormat df = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);
			double round= Double.parseDouble(df.format(difference));
			return round;
		}
}
