package Order_Package;

import java.util.Date;

//Written by Azim Amizie Bin Saharen B032110027

public class Order_Item
{
	private int orderItemId;
	private Product Product;
	private int quantity;
	private double subTotalAmount;
	private int sequenceNumber;
	private String orderStatus;
	private Date readTime;
	
	public void SetOrderItemId(int id)
	{
		this.orderItemId = id;
	}

	public int GetOrderItemId()
	{
		return this.orderItemId;
	}
	
	public void SetProduct(Product product)
	{
		this.Product = product;
	}

	public Product GetProduct()
	{
		return this.Product;
	}
	
	public void SetQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public int GetQuantity()
	{
		return this.quantity;
	}
	
	public void SetSubTotalAmount(double subTotalAmount)
	{
		this.subTotalAmount = subTotalAmount;
	}

	public double GetsubTotalAmount()
	{
		return this.subTotalAmount;
	}
	
	public void SetSequenceNumber(int sequenceNumber)
	{
		this.sequenceNumber = sequenceNumber;
	}

	public double GetSequenceNumber()
	{
		return this.sequenceNumber;
	}
	
	public void SetOrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public String orderStatus()
	{
		return this.orderStatus;
	}
	
	public void SetReadTime(Date readTime)
	{
		this.readTime = readTime;
	}

	public Date GetreadTime()
	{
		return this.readTime;
	}
}
