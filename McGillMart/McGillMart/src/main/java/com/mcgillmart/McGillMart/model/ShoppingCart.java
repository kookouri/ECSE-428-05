package com.mcgillmart.McGillMart.model;

import java.util.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class ShoppingCart
{

  @Id
  @GeneratedValue
  private int id;

  @OneToMany
  private List<Item> items;

  @OneToOne
  private User user;

  public ShoppingCart(int aId, User aUser)
  {
    id = aId;
    items = new ArrayList<Item>();
    if (aUser == null || aUser.getShoppingCart() != null)
    {
      throw new RuntimeException("Unable to create ShoppingCart due to aUser. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    user = aUser;
  }

  public ShoppingCart(int aId, int aIdForUser, String aEmailForUser, String aNameForUser, String aPasswordForUser, String aPhoneNumberForUser, McGillMart aMcGillMartForUser)
  {
    id = aId;
    items = new ArrayList<Item>();
    user = new User(aEmailForUser, aNameForUser, aPasswordForUser, aPhoneNumberForUser, this, aMcGillMartForUser);
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    items.add(aItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    if (items.contains(aItem))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    items.clear();
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}