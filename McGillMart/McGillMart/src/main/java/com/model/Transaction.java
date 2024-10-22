package com.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;

// line 33 "model.ump"
// line 62 "model.ump"
public class Transaction
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Transaction Attributes
  private double amount;
  private Date dateOfPurchase;

  //Autounique Attributes
  private int id;

  //Transaction Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transaction(double aAmount, Date aDateOfPurchase, User aUser)
  {
    amount = aAmount;
    dateOfPurchase = aDateOfPurchase;
    id = nextId++;
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create history due to user. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAmount(double aAmount)
  {
    boolean wasSet = false;
    amount = aAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateOfPurchase(Date aDateOfPurchase)
  {
    boolean wasSet = false;
    dateOfPurchase = aDateOfPurchase;
    wasSet = true;
    return wasSet;
  }

  public double getAmount()
  {
    return amount;
  }

  public Date getDateOfPurchase()
  {
    return dateOfPurchase;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_SetOneToMany */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    if (aUser == null)
    {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removeHistory(this);
    }
    user.addHistory(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removeHistory(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "amount" + ":" + getAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateOfPurchase" + "=" + (getDateOfPurchase() != null ? !getDateOfPurchase().equals(this)  ? getDateOfPurchase().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}