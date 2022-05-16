package Order_Package;

import Database_Package.Database;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

//Written by Azim Amizie Bin Saharen B032110027

public class Order_Interface extends JFrame
{
		private JPanel contentPane;
		private JTable table;
		JLabel label_total = new JLabel("Grand Total");
		Product Product = new Product();
		Calc calculate = new Calc();

		ArrayList<Integer> array_id = new ArrayList<Integer>();
		ArrayList<String> array_name = new ArrayList<String>();
		ArrayList<Integer> array_quantity = new ArrayList<Integer>();
		ArrayList<Double> array_price = new ArrayList<Double>();
		
		int temp1;
		String temp2;
		Double temp3;
		Double roughTotal;
		Double calculatedTotal;
		Double round;
		

		private JTextField textField;
		private static String test="";
		private static ServerSocket socket;
		private static Socket s;
		private static DataOutputStream OutStream;
		
		public static void main(String[] args) throws IOException 
		{
			
			socket= new ServerSocket (9999);
			s=socket.accept();		
			OutStream=new DataOutputStream(s.getOutputStream());

			test = "Order counter connected to server";
			OutStream.writeUTF(test);		

			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					try 
					{
						Order_Interface frame = new Order_Interface();
						frame.setVisible(true);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			});
		}

		public Order_Interface() 
		{
			roughTotal=0.0;
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 515, 384);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
			internalFrame.setBounds(10, 10, 471, 327);
			contentPane.add(internalFrame);
			internalFrame.getContentPane().setLayout(null);

			JLabel lblAmountRm = new JLabel("Amount : RM");
			lblAmountRm.setFont(new Font("Calibri", Font.PLAIN, 20));
			lblAmountRm.setBounds(58, 51, 146, 47);
			internalFrame.getContentPane().add(lblAmountRm);

			textField = new JTextField();
			textField.setFont(new Font("Calibri", Font.PLAIN, 20));
			textField.setBounds(58, 108, 276, 47);
			internalFrame.getContentPane().add(textField);
			textField.setColumns(10);

			JButton btnCancel = new JButton("Cancel");
			btnCancel.setFont(new Font("Calibri", Font.PLAIN, 24));
			btnCancel.setBounds(58, 191, 119, 53);
			internalFrame.getContentPane().add(btnCancel);

			JButton btnConfirm = new JButton("Confirm");
			btnConfirm.setFont(new Font("Calibri", Font.PLAIN, 24));
			btnConfirm.setBounds(215, 191, 119, 53);
			internalFrame.getContentPane().add(btnConfirm);

			JLabel label_total_amount = new JLabel("Grand Total: RM");
			label_total_amount.setFont(new Font("Calibri", Font.PLAIN, 20));
			label_total_amount.setBounds(58, 10, 361, 47);
			internalFrame.getContentPane().add(label_total_amount);

			JLabel label_menu = new JLabel("Drinks Menu:");
			label_menu.setFont(new Font("Calibri", Font.PLAIN, 20));
			label_menu.setBounds(35, 28, 124, 38);
			contentPane.add(label_menu);

			JComboBox comboBox = new JComboBox();
			comboBox.setFocusable(false);
			comboBox.setBounds(35, 76, 321, 36);
			contentPane.add(comboBox);

			try 
			{
				Database dbCtrl = new Database();
				Connection connection = dbCtrl.getConnection();

				String sql = "Select * from itemproduct;";
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next())
				{
					String tempp = rs.getString("Name");
					Product.SetName(tempp);

					comboBox.addItem(Product.GetName());
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			JButton button_add = new JButton("Add");
			button_add.setFocusable(false);
			button_add.setFont(new Font("Calibri", Font.PLAIN, 20));
			button_add.setBounds(366, 76, 89, 36);
			contentPane.add(button_add);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(35, 155, 421, 120);
			contentPane.add(scrollPane);

			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"Number", "ID", "Name", "Quantity", "Total"}
			));
			scrollPane.setViewportView(table);

			label_total.setHorizontalAlignment(SwingConstants.RIGHT);
			label_total.setFont(new Font("Calibri", Font.PLAIN, 20));
			label_total.setBounds(248, 285, 207, 30);
			contentPane.add(label_total);

			JButton button_cancel = new JButton("Cancel");
			button_cancel.setFont(new Font("Calibri", Font.PLAIN, 20));
			button_cancel.setBounds(35, 279, 89, 36);
			contentPane.add(button_cancel);

			JButton button_paycash = new JButton("Pay Cash");
			button_paycash.setFont(new Font("Calibri", Font.PLAIN, 20));
			button_paycash.setBounds(134, 279, 116, 36);
			contentPane.add(button_paycash);

			button_add.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					String name;
					name = comboBox.getSelectedItem().toString();

					try 
					{
						Database dbCtrl = new Database();
						Connection connection = dbCtrl.getConnection();

						String sql = "Select * from itemproduct where Name = '"+ name +"'";
						Statement st = connection.createStatement();
						ResultSet rs = st.executeQuery(sql);
						
						while(rs.next())
						{
							int tempp = rs.getInt("ItemProductId");
							String temp_name = rs.getString("Name");
							Double temp_price = rs.getDouble("Price");

							if(array_id.contains(tempp))
							{
								temp1 = array_id.indexOf(tempp);
								int current_quantity = array_quantity.get(temp1);
								int new_quantity = current_quantity + 1;
								array_quantity.set(temp1, new_quantity);
								test = "Order counter added a new item (id: "+tempp+") into order list.";
								OutStream.writeUTF(test);
							}
							else
							{
								array_id.add(tempp);
								array_name.add(temp_name);
								array_quantity.add(1);
								array_price.add(temp_price);
								test = "Order counter added a new item (id: "+tempp+") into order list.";
								OutStream.writeUTF(test);
							}
							DisplayTable();
						}
					} 
					catch (Exception ee) 
					{
						JOptionPane.showMessageDialog(null, ee, ee + "", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			
			table.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					JTable target = (JTable)e.getSource();
		            int roww = target.getSelectedRow();
		            
		            if (array_quantity.get(roww) > 1)
		            {
		            	int new_quantity = array_quantity.get(roww) - 1;
		            	array_quantity.set(roww, new_quantity);
		            	
		            	test = "Order counter removed a item (row: "+roww+") from order list.";
						try 
						{
							OutStream.writeUTF(test);
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
		            }

		            else
		            {
		            	array_id.remove(roww);
		            	array_name.remove(roww);
		            	array_quantity.remove(roww);
		            	array_price.remove(roww);

		            	test = "Order counter removed a item (row: "+roww+") from order list.";
						try 
						{
							OutStream.writeUTF(test);
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
		            }

		            DisplayTable();
				}
			});
			
			button_cancel.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					array_id.clear();
	            	array_name.clear();
	            	array_quantity.clear();
	            	array_price.clear();
	            	DisplayTable();
	            	label_total.setText( "Total Amount");	
	            	roughTotal=0.0;
	            	
	            	test = "Order counter removed all item of the order list.";
					try 
					{
						OutStream.writeUTF(test);
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			});

			button_paycash.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					if(roughTotal<=0.0)
					{
						JOptionPane.showMessageDialog(null, "Cart Empty");
		            	test = "Order counter unable to process payment due to reason don't have any item in list.";
						try 
						{
							OutStream.writeUTF(test);
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
					}

					else
					{
						button_paycash.setVisible(false);
						button_cancel.setVisible(false);
						label_total_amount.setText("Total Amount: RM "+calculatedTotal.toString());
						internalFrame.setVisible(true);

		            	test = "Order counter processing payment.";
						try 
						{
							OutStream.writeUTF(test);
						} 
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			});
			
			btnCancel.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					button_paycash.setVisible(true);
					button_cancel.setVisible(true);
					internalFrame.setVisible(false);
					textField.setText(null);

	            	test = "Order counter cancel payment.";
					try 
					{
						OutStream.writeUTF(test);
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			});
			
			btnConfirm.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					try
					{
						double amount=Double.parseDouble(textField.getText());

						if(amount<roughTotal)
						{
							JOptionPane.showMessageDialog(null, "Insufficient Amount");
							
			            	test = "Order counter receive not enough amount for payment.";
							OutStream.writeUTF(test);
						}

						else 
						{
								calculatedTotal = Math.floor(calculatedTotal * 1e2) / 1e2;
								double change_amount=amount-calculatedTotal;
								DecimalFormat df = new DecimalFormat("#.#");
								df.setRoundingMode(RoundingMode.CEILING);
								JOptionPane.showMessageDialog(null, "Balance : RM "+df.format(change_amount));
								try 
								{

									int ordernum=0;
									try
									{
										Database dbCtrl = new Database();
										Connection connection = dbCtrl.getConnection();
										
										String sql = "SELECT * FROM `order`";
										Statement st = connection.createStatement();
										ResultSet rs = st.executeQuery(sql);
										
										while(rs.next())
										{
											ordernum=rs.getInt("OrderNumber");
										}
								
									}
									catch(Exception b)
									{
										JOptionPane.showMessageDialog(null, "Yataa"+b);
									}

									ordernum=ordernum+1;
									
									java.util.Date date = new java.util.Date();
									Object param = new java.sql.Timestamp(date.getTime());

									double receiveamount=Double.parseDouble(textField.getText());
									
									int totalquantity=0;
									for(int i=0;i<array_id.size();i++)
									{
										totalquantity = totalquantity+array_quantity.get(i);
									}

									double servicetax=temp3*0.06;

									Database dbCtrl1 = new Database();
									Connection conn = dbCtrl1.getConnection();
									String sql="INSERT INTO `order` (`OrderId`, `OrderNumber`, `TransactionDate`, `GrandTotal`, `TenderedCash`, `Change`, `TotalOrderItem`, `SubTotal`, `Rounding`, `ServiceTax`) VALUES (NULL, '"+ordernum+"', current_timestamp(), '"+calculatedTotal+"', '"+receiveamount+"', '"+change_amount+"', '"+totalquantity+"', '"+temp3+"', '"+round+"', '"+servicetax+"')";
									PreparedStatement statement = conn.prepareStatement(sql);
									statement.execute();

									int orderid=0;
									Database dbCtrl = new Database();
									Connection connection = dbCtrl.getConnection();
									
									String sql3 = "SELECT * FROM `order`";
									Statement st = connection.createStatement();
									ResultSet rs = st.executeQuery(sql3);
									
									while(rs.next())
									{
										orderid=rs.getInt("OrderId");
									}

									Database dbCtrl2 = new Database();
									Connection conne = dbCtrl2.getConnection();
									
									for (int i=0; i<array_id.size();i++)
									{
										int itemid=array_id.get(i);
										int subquantity=array_quantity.get(i);
										double subtotal= array_price.get(i) * subquantity;
										
										String sql2="INSERT INTO `orderitem` (`OrderItem`, `ItemProduct`, `Order`, `Quantity`, `SubTotalAmount`, `OrderStatus`, `ReadyTime`) VALUES (NULL, '"+itemid+"', '"+orderid+"', '"+subquantity+"', '"+subtotal+"', 'Processing', NULL)";
										PreparedStatement statement1 = conne.prepareStatement(sql2);
										statement1.execute();
									}
								} 
								catch (Exception i) 
								{
									JOptionPane.showMessageDialog(null, i);
								}

								button_paycash.setVisible(true);
								button_cancel.setVisible(true);
								internalFrame.setVisible(false);
								textField.setText(null);
								array_id.clear();
				            	array_name.clear();
				            	array_quantity.clear();
				            	array_price.clear();
				            	DisplayTable();
				            	label_total.setText( "Grand Total");	
				            	roughTotal=0.0;
								test = "Order counter transaction payment made successfully.";
								OutStream.writeUTF(test);
								PrintReceipt();
								test = "Order counter generate receipt for cashier to print.";
								OutStream.writeUTF(test);	  
						}          	
					}
					catch(Exception a)
					{
						JOptionPane.showMessageDialog(null, "Invalid Input Of Amount");
						test = "Order counter receive invalid input for payment.";
						try 
						{
							OutStream.writeUTF(test);
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
					}
				}
			});
		}

		public void DisplayTable()
		{
				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				temp3 = 0.0;
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setRowCount(0);
				Object[] row = new Object[5];

				for (int i=0; i<array_id.size();i++)
				{
					row[0] = i+1;
					row[1] = array_id.get(i);
					row[2] = array_name.get(i);
					row[3] = array_quantity.get(i);

					double subtotal=calculate.calcTotal(array_quantity.get(i), array_price.get(i));
					row[4] = df.format(subtotal);
					model.addRow(row);
					temp3 = temp3 + subtotal;
				}
				roughTotal= temp3 * 1.06;
				roughTotal = Double.parseDouble(df.format(roughTotal)) ;

				round = calculate.roundTotal(roughTotal);
				round = Math.floor(round * 1e2) / 1e2;
				calculatedTotal = roughTotal + round;
				calculatedTotal = Math.floor(calculatedTotal * 1e2) / 1e2;
				label_total.setText( "Total Amount : RM " + calculatedTotal);
		}

		public void PrintReceipt()
		{

			int order_id = 0;
			int order_number = 0;
			String date="";
			double calculatedTotal=0;
			double tender_cash=0;
			double change=0;
			int total_quantity=0;
			double sub_total=0;
			double rounding =0;
			double service_tax=0;

			int item_id;
			int item_quantity;
			double item_subtotal;

			String item_name;
			String item_details = "";

			try
			{
				Database dbCtrl = new Database();
				Connection connection = dbCtrl.getConnection();		
				String sql = "SELECT * FROM `order`";
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next())
				{
					order_id =rs.getInt("OrderId");
					order_number =rs.getInt("OrderNumber");
					date =rs.getString("TransactionDate");
					calculatedTotal =rs.getDouble("GrandTotal");
					tender_cash =rs.getDouble("TenderedCash");
					change =rs.getDouble("Change");
					total_quantity =rs.getInt("TotalOrderItem");
					sub_total =rs.getDouble("SubTotal");
					rounding =rs.getDouble("Rounding");
					service_tax =rs.getDouble("ServiceTax");
				}
				try
				{
					Database dbCtrl1 = new Database();
					Connection connection1 = dbCtrl1.getConnection();

					String sql1 = "SELECT * FROM `orderitem` where `Order` = '"+order_id+"'";
					Statement st1 = connection1.createStatement();
					ResultSet rs1 = st1.executeQuery(sql1);
					
					while(rs1.next())
					{
						item_id =rs1.getInt("ItemProduct");
						item_quantity =rs1.getInt("Quantity");
						item_subtotal =rs1.getDouble("SubTotalAmount");
						
						try
						{
							Database dbCtrl2 = new Database();
							Connection connection2 = dbCtrl2.getConnection();

							String sql2 = "SELECT * FROM `itemproduct` where `ItemProductId` = '"+item_id+"'";
							Statement st2 = connection2.createStatement();
							ResultSet rs2 = st2.executeQuery(sql2);
							
							while(rs2.next())
							{
								item_name =rs2.getString("Name");

								item_details = item_details + item_name + "  x" + item_quantity + "   RM " + item_subtotal + "\n";
							}
						}
						catch(Exception x)
						{
							JOptionPane.showMessageDialog(null, x);
						}
					}
				}
				catch(Exception y)
				{
					JOptionPane.showMessageDialog(null, y);
				}
			}
			catch(Exception z)
			{
				JOptionPane.showMessageDialog(null, z);
			}
			

			UIManager.put("OptionPane.okButtonText", "PRINT");
			JOptionPane.showMessageDialog(null, 
			"-----------------------------------------------------------------\n" +
			"Your order number is: "+ order_number + "\n" +
			"-----------------------------------------------------------------\n" +
			"HornetTea                                 \n" +
			"FICTS                                     \n" +
			"Fakulti Teknologi Maklumat dan Komunikasi \n" +
			"Universiti Teknikal Malaysia Melaka       \n" +
			"Hang Tuah Jaya, 76100 Durian Tunggal      \n" +
			"Melaka, Malaysia                          \n" +
			"-----------------------------------------------------------------\n" +
			"Invoice                                   \n" +
			"                                          \n" +
			"Bill No: " + order_id + "\n" +
			"Date: " + date + "\n" +
			"                                          \n" +
			"Details                                   \n" +
			"-----------------------------------------------------------------\n" +
			"Item Name Qty Price(RM)                   \n" +
			"-----------------------------------------------------------------\n" +
			item_details +
			"-----------------------------------------------------------------\n" +
			"Total Item                                           " + total_quantity + "\n" +
			"                                          \n" +
			"                     Sub total                       " + sub_total + "\n" +
			"                     Service Tax (6%)         " + service_tax + "\n" +
			"                     Rounding                      " + rounding + "\n" +
			"-----------------------------------------------------------------\n" +
			"                     Grand Total                  " + calculatedTotal+ "\n" +
			"                                          \n" +
			"                     Tendered Cash            " + tender_cash + "\n" +
			"                     Change                          " + change + "\n" +
			"-----------------------------------------------------------------\n" +
			"            Thank you and have a good day       \n",
		    "RECEIPT", JOptionPane.PLAIN_MESSAGE);
		}
}
