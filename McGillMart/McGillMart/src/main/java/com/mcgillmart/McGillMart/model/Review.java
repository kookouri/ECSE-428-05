package com.mcgillmart.McGillMart.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review
{
  @Id
  @GeneratedValue
  private int id;

  private int rating;
  private String comment;
  private Date datePosted;
  private String username;

  @ManyToOne
  private Item item;

  public Review(int aId, int aRating, String aComment, Date aDatePosted, String aUsername, Item aItem)
  {
    id = aId;
    rating = aRating;
    comment = aComment;
    datePosted = aDatePosted;
    username = aUsername;
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create review due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setRating(int aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setComment(String aComment)
  {
    boolean wasSet = false;
    comment = aComment;
    wasSet = true;
    return wasSet;
  }

  public boolean setDatePosted(Date aDatePosted)
  {
    boolean wasSet = false;
    datePosted = aDatePosted;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getRating()
  {
    return rating;
  }

  public String getComment()
  {
    return comment;
  }

  public Date getDatePosted()
  {
    return datePosted;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template association_GetOne */
  public Item getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToMany */
  public boolean setItem(Item aItem)
  {
    boolean wasSet = false;
    if (aItem == null)
    {
      return wasSet;
    }

    Item existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeReview(this);
    }
    item.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Item placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "rating" + ":" + getRating()+ "," +
            "comment" + ":" + getComment()+ "," +
            "username" + ":" + getUsername()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "datePosted" + "=" + (getDatePosted() != null ? !getDatePosted().equals(this)  ? getDatePosted().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}