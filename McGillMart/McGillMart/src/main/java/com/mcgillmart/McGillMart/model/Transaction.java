package com.mcgillmart.McGillMart.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction
{
  @Id
  @GeneratedValue
  private int id;

  private double amount;
  private LocalDate dateOfPurchase;
  private String description;

  @ManyToOne
  private User user;

  public Transaction(double aAmount, LocalDate aDateOfPurchase, String aDescription, User aUser)
  {
    amount = aAmount;
    dateOfPurchase = aDateOfPurchase;
    description = aDescription;
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create history due to user. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Transaction() { 
    
  }


  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAmount(double aAmount)
  {
    boolean wasSet = false;
    amount = aAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateOfPurchase(LocalDate  aDateOfPurchase)
  {
    boolean wasSet = false;
    dateOfPurchase = aDateOfPurchase;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }


  public int getId()
  {
    return id;
  }

  public double getAmount()
  {
    return amount;
  }

  public LocalDate getDateOfPurchase()
  {
    return dateOfPurchase;
  }

  public String getDescription()
  {
    return description;
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


  @SuppressWarnings("unlikely-arg-type")
  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "amount" + ":" + getAmount()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateOfPurchase" + "=" + (getDateOfPurchase() != null ? !getDateOfPurchase().equals(this)  ? getDateOfPurchase().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}