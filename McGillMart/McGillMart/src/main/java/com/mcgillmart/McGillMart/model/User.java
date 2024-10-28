package com.mcgillmart.McGillMart.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Date;

// line 9 "model.ump"
// line 56 "model.ump"
@Entity
// In PostgreSQL, user is a reserved keyword, 
// so using it as a table name without additional handling will lead to conflicts
@Table(name = "\"user\"") 
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByEmail = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  @Id
  @GeneratedValue
  private int id;

  private String email;
  private String name;
  private String password;
  private String phoneNumber;

  //User Associations
  @OneToMany
  private List<Item> shoppingCart;
  @OneToMany
  private List<Transaction> history;

  @ManyToOne
  @JoinColumn(name="mcgill_mart_id")
  private McGillMart mcGillMart;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public User() {
	  
  }
  
  public User(String aEmail, String aName, String aPassword, String aPhoneNumber, McGillMart aMcGillMart)
  {
    name = aName;
    password = aPassword;
    phoneNumber = aPhoneNumber;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    shoppingCart = new ArrayList<Item>();
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
  /* Code from template association_GetMany */
  public Item getShoppingCart(int index)
  {
    Item aShoppingCart = shoppingCart.get(index);
    return aShoppingCart;
  }

  public List<Item> getShoppingCart()
  {
    List<Item> newShoppingCart = Collections.unmodifiableList(shoppingCart);
    return newShoppingCart;
  }

  public int numberOfShoppingCart()
  {
    int number = shoppingCart.size();
    return number;
  }

  public boolean hasShoppingCart()
  {
    boolean has = shoppingCart.size() > 0;
    return has;
  }

  public int indexOfShoppingCart(Item aShoppingCart)
  {
    int index = shoppingCart.indexOf(aShoppingCart);
    return index;
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
  public static int minimumNumberOfShoppingCart()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addShoppingCart(Item aShoppingCart)
  {
    boolean wasAdded = false;
    if (shoppingCart.contains(aShoppingCart)) { return false; }
    shoppingCart.add(aShoppingCart);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShoppingCart(Item aShoppingCart)
  {
    boolean wasRemoved = false;
    if (shoppingCart.contains(aShoppingCart))
    {
      shoppingCart.remove(aShoppingCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShoppingCartAt(Item aShoppingCart, int index)
  {  
    boolean wasAdded = false;
    if(addShoppingCart(aShoppingCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShoppingCart()) { index = numberOfShoppingCart() - 1; }
      shoppingCart.remove(aShoppingCart);
      shoppingCart.add(index, aShoppingCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShoppingCartAt(Item aShoppingCart, int index)
  {
    boolean wasAdded = false;
    if(shoppingCart.contains(aShoppingCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShoppingCart()) { index = numberOfShoppingCart() - 1; }
      shoppingCart.remove(aShoppingCart);
      shoppingCart.add(index, aShoppingCart);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShoppingCartAt(aShoppingCart, index);
    }
    return wasAdded;
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
    shoppingCart.clear();
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
            "  " + "mcGillMart = "+(getMcGillMart()!=null?Integer.toHexString(System.identityHashCode(getMcGillMart())):"null");
  }
}