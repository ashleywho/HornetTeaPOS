package Order_Package;

//Written by Azim Amizie Bin Saharen B032110027

public class Product
{
	private int ItemProductID;
	private String Name;
	private String LabelName;
	private double Price;
	
	public void SetItemProductID(int id)
	{
		this.ItemProductID = id;
	}

	public int GetItemProductID()
	{
		return this.ItemProductID;
	}

	public void SetName(String name)
	{
		this.Name = name;
	}

	public String GetName()
	{
		return this.Name;
	}

	public void SetLabelName(String labelName)
	{
		this.LabelName = labelName;
	}
	
	public String GetLabelName()
	{
		return this.LabelName;
	}
	
	public void SetPrice(double price)
	{
		this.Price = price;
	}
	
	public double GetPrice()
	{
		return this.Price;
	}
}
