package com.mcgillmart.McGillMart.model;

import java.util.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.sql.Date;

public class User
{

  private static Map<String, User> usersByEmail = new HashMap<String, User>();

  @Id
  @GeneratedValue
  private int id;

  private String email;
  private String name;
  private String password;
  private String phoneNumber;

  @OneToOne
  private ShoppingCart shoppingCart;

  @OneToMany
  private List<Transaction> history;

  @ManyToOne
  @JoinColumn(name="mcgill_mart_id")
  private McGillMart mcGillMart;

  public User(String aEmail, String aName, String aPassword, String aPhoneNumber, ShoppingCart aShoppingCart, McGillMart aMcGillMart)
  {
    name = aName;
    password = aPassword;
    phoneNumber = aPhoneNumber;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (aShoppingCart == null || aShoppingCart.getUser() != null)
    {
      throw new RuntimeException("Unable to create User due to aShoppingCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    shoppingCart = aShoppingCart;
    history = new ArrayList<Transaction>();
    boolean didAddMcGillMart = setMcGillMart(aMcGillMart);
    if (!didAddMcGillMart)
    {
      throw new RuntimeException("Unable to create user due to mcGillMart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public User(int aId, String aEmail, String aName, String aPassword, String aPhoneNumber, int aIdForShoppingCart, McGillMart aMcGillMart)
  {
    id = aId;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    name = aName;
    password = aPassword;
    phoneNumber = aPhoneNumber;
    shoppingCart = new ShoppingCart(aIdForShoppingCart, this);
    history = new ArrayList<Transaction>();
    boolean didAddMcGillMart = setMcGillMart(aMcGillMart);
    if (!didAddMcGillMart)
    {
      throw new RuntimeException("Unable to create user due to mcGillMart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    String anOldEmail = getEmail();
    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
      return true;
    }
    if (hasWithEmail(aEmail)) {
      return wasSet;
    }
    email = aEmail;
    wasSet = true;
    if (anOldEmail != null) {
      usersByEmail.remove(anOldEmail);
    }
    usersByEmail.put(aEmail, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithEmail(String aEmail)
  {
    return usersByEmail.get(aEmail);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public ShoppingCart getShoppingCart()
  {
    return shoppingCart;
  }
  /* Code from template association_GetMany */
  public Transaction getHistory(int index)
  {
    Transaction aHistory = history.get(index);
    return aHistory;
  }

  public List<Transaction> getHistory()
  {
    List<Transaction> newHistory = Collections.unmodifiableList(history);
    return newHistory;
  }

  public int numberOfHistory()
  {
    int number = history.size();
    return number;
  }

  public boolean hasHistory()
  {
    boolean has = history.size() > 0;
    return has;
  }

  public int indexOfHistory(Transaction aHistory)
  {
    int index = history.indexOf(aHistory);
    return index;
  }
  /* Code from template association_GetOne */
  public McGillMart getMcGillMart()
  {
    return mcGillMart;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHistory()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Transaction addHistory(int aId, double aAmount, Date aDateOfPurchase)
  {
    return new Transaction(aId, aAmount, aDateOfPurchase, this);
  }

  public boolean addHistory(Transaction aHistory)
  {
    boolean wasAdded = false;
    if (history.contains(aHistory)) { return false; }
    User existingUser = aHistory.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aHistory.setUser(this);
    }
    else
    {
      history.add(aHistory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHistory(Transaction aHistory)
  {
    boolean wasRemoved = false;
    //Unable to remove aHistory, as it must always have a user
    if (!this.equals(aHistory.getUser()))
    {
      history.remove(aHistory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHistoryAt(Transaction aHistory, int index)
  {  
    boolean wasAdded = false;
    if(addHistory(aHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHistory()) { index = numberOfHistory() - 1; }
      history.remove(aHistory);
      history.add(index, aHistory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHistoryAt(Transaction aHistory, int index)
  {
    boolean wasAdded = false;
    if(history.contains(aHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHistory()) { index = numberOfHistory() - 1; }
      history.remove(aHistory);
      history.add(index, aHistory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHistoryAt(aHistory, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMcGillMart(McGillMart aMcGillMart)
  {
    boolean wasSet = false;
    if (aMcGillMart == null)
    {
      return wasSet;
    }

    McGillMart existingMcGillMart = mcGillMart;
    mcGillMart = aMcGillMart;
    if (existingMcGillMart != null && !existingMcGillMart.equals(aMcGillMart))
    {
      existingMcGillMart.removeUser(this);
    }
    mcGillMart.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByEmail.remove(getEmail());
    ShoppingCart existingShoppingCart = shoppingCart;
    shoppingCart = null;
    if (existingShoppingCart != null)
    {
      existingShoppingCart.delete();
    }
    while (history.size() > 0)
    {
      Transaction aHistory = history.get(history.size() - 1);
      aHistory.delete();
      history.remove(aHistory);
    }
    
    McGillMart placeholderMcGillMart = mcGillMart;
    this.mcGillMart = null;
    if(placeholderMcGillMart != null)
    {
      placeholderMcGillMart.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shoppingCart = "+(getShoppingCart()!=null?Integer.toHexString(System.identityHashCode(getShoppingCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "mcGillMart = "+(getMcGillMart()!=null?Integer.toHexString(System.identityHashCode(getMcGillMart())):"null");
  }
}