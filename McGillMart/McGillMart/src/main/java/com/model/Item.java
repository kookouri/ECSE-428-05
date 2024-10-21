/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package com.model;

// line 20 "model.ump"
// line 54 "model.ump"
public class Item
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Category { Electronics, Clothing, Textbook, Furniture, Other }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;
  private double price;
  private String description;
  private Category category;

  //Autounique Attributes
  private int id;

  //Item Associations
  private McGillMart mcGillMart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aName, double aPrice, String aDescription, Category aCategory, McGillMart aMcGillMart)
  {
    name = aName;
    price = aPrice;
    description = aDescription;
    category = aCategory;
    id = nextId++;
    boolean didAddMcGillMart = setMcGillMart(aMcGillMart);
    if (!didAddMcGillMart)
    {
      throw new RuntimeException("Unable to create item due to mcGillMart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
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

  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public String getDescription()
  {
    return description;
  }

  public Category getCategory()
  {
    return category;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public McGillMart getMcGillMart()
  {
    return mcGillMart;
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
      existingMcGillMart.removeItem(this);
    }
    mcGillMart.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    McGillMart placeholderMcGillMart = mcGillMart;
    this.mcGillMart = null;
    if(placeholderMcGillMart != null)
    {
      placeholderMcGillMart.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this)  ? getCategory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mcGillMart = "+(getMcGillMart()!=null?Integer.toHexString(System.identityHashCode(getMcGillMart())):"null");
  }
}