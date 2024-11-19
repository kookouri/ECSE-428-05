package com.mcgillmart.McGillMart.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;

@Entity
public class Item
{
  public enum Category { Electronics, Clothing, Textbook, Furniture, Stationary, Other }

  @Id
  @GeneratedValue
  private int id;

  private String name;
  private double price;
  private String description;

  @Enumerated(EnumType.ORDINAL)
  private Category category;
  private String url;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();;

  @ManyToOne
  @JoinColumn(name="mcgill_mart_id")
  private McGillMart mcGillMart;


  public Item() { 

  }
  
  public Item(String aName, double aPrice, String aDescription, Category aCategory, String aUrl, McGillMart aMcGillMart)
  {
    name = aName;
    price = aPrice;
    description = aDescription;
    category = aCategory;
    url = aUrl;
    reviews = new ArrayList<>();
    boolean didAddMcGillMart = setMcGillMart(aMcGillMart);
    if (!didAddMcGillMart)
    {
      throw new RuntimeException("Unable to create item due to mcGillMart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setUrl(String aUrl){
    boolean wasSet = false;
    url = aUrl;
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

  public int getId()
  {
    return id;
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

  public String getUrl()
  {
    return url;
  }

  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetOne */
  public McGillMart getMcGillMart()
  {
    return mcGillMart;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(int aId, int aRating, String aComment, LocalDate aDatePosted, String aUsername)
  {
    return new Review(aId, aRating, aComment, aDatePosted, aUsername, this);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Item existingItem = aReview.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aReview.setItem(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a item
    if (!this.equals(aReview.getItem()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
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
      existingMcGillMart.removeItem(this);
    }
    mcGillMart.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (reviews.size() > 0)
    {
      Review aReview = reviews.get(reviews.size() - 1);
      aReview.delete();
      reviews.remove(aReview);
    }
    
    McGillMart placeholderMcGillMart = mcGillMart;
    this.mcGillMart = null;
    if(placeholderMcGillMart != null)
    {
      placeholderMcGillMart.removeItem(this);
    }
  }


  @SuppressWarnings("unlikely-arg-type")
  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this)  ? getCategory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this)  ? getCategory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mcGillMart = "+(getMcGillMart()!=null?Integer.toHexString(System.identityHashCode(getMcGillMart())):"null");
  }
}