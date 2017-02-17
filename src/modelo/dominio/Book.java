package modelo.dominio;

import java.io.*;
import java.util.Date;


@SuppressWarnings("serial")
public class Book implements Serializable {
	private int bookNumber;
	private boolean isPaid;
	private Date bookDate;
	private String telephone;
	private Offer offer;
	
	public Book() {
	}

	public Book(String telephone, Offer offer) {	
		//this.bookNumber = BookManager.getNumber();
		this.telephone=telephone;
		this.offer = offer;
		//this.price = price;
		//Booking date is assigned to actual date
		this.bookDate= new java.util.Date(System.currentTimeMillis());
		this.isPaid=false;

	}
	
	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public void imprimete(){
		System.out.println(bookNumber);
		System.out.println(isPaid);
		System.out.println(bookDate);
		System.out.println(telephone);
		System.out.println(offer);
		
	}

	

	/**
	 * Get the book number
	 * 
	 * @return book number
	 */
	public int getBookNumber() {
		return this.bookNumber;
	}

	/**
	 * Set a offer object 
	 * 
	 * @param Offer object
	 * 
	 */
	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	/**
	 * Get the offer object
	 * 
	 * @return Offer object
	 */
	public Offer getOffer() {
		return this.offer;
	}

	

	/**
	 * This method returns a price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return this.offer.getPrice();
	}
	
	/**
	 * This method sets a book date
	 * 
	 * @param bookDate
	 */
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	/**
	 * This method returns a book date
	 * 
	 * @return book date
	 */
	public Date getBookDate() {
		return this.bookDate;
	}
	
	/**
	 * This method sets a telephone number
	 * 
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * This method returns a telephone number
	 * 
	 * @return telephone number
	 */
	public String getTelephone() {
		return this.telephone;
	}
	
	/**
	 * This method puts the reserve as paid 
	 * 
	 * @param none
	 */
	public void paid() {
		this.isPaid = true;
	}

	/**
	 * This method puts the reserve as not paid
	 * 
	 * @return none
	 */
	public void notPaid() {
		this.isPaid=false;
	}
	
	/**
	 * This method returns the status of a book 
	 * 
	 * @param none
	 */
	public boolean isPaid() {
		return isPaid;
	}
	
}