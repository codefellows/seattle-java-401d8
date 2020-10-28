package com.ncarignan.buycheapthings.deletethis;


import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the CheapItem type in your schema. */
@SuppressWarnings("all")
@Entity// class 32 update for room
@ModelConfig(pluralName = "CheapItems")
public final class CheapItem implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField THING_NAME = field("thingName");
  public static final QueryField QUANTITY = field("quantity");
  public static final QueryField PRICE = field("price");

  @NonNull
  @PrimaryKey // class 32 update for room
  public final @ModelField(targetType="ID", isRequired = true) String id;
  public final @ModelField(targetType="String") String thingName;
  public final @ModelField(targetType="Int") Integer quantity;
  public final @ModelField(targetType="Float") Float price;
  public String getId() {
      return id;
  }
  
  public String getThingName() {
      return thingName;
  }
  
  public Integer getQuantity() {
      return quantity;
  }
  
  public Float getPrice() {
      return price;
  }
  
  public CheapItem(String id, String thingName, Integer quantity, Float price) {
    this.id = id;
    this.thingName = thingName;
    this.quantity = quantity;
    this.price = price;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CheapItem cheapItem = (CheapItem) obj;
      return ObjectsCompat.equals(getId(), cheapItem.getId()) &&
              ObjectsCompat.equals(getThingName(), cheapItem.getThingName()) &&
              ObjectsCompat.equals(getQuantity(), cheapItem.getQuantity()) &&
              ObjectsCompat.equals(getPrice(), cheapItem.getPrice());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getThingName())
      .append(getQuantity())
      .append(getPrice())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CheapItem {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("thingName=" + String.valueOf(getThingName()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("price=" + String.valueOf(getPrice()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static CheapItem justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new CheapItem(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      thingName,
      quantity,
      price);
  }
  public interface BuildStep {
    CheapItem build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep thingName(String thingName);
    BuildStep quantity(Integer quantity);
    BuildStep price(Float price);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String thingName;
    private Integer quantity;
    private Float price;
    @Override
     public CheapItem build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CheapItem(
          id,
          thingName,
          quantity,
          price);
    }
    
    @Override
     public BuildStep thingName(String thingName) {
        this.thingName = thingName;
        return this;
    }
    
    @Override
     public BuildStep quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep price(Float price) {
        this.price = price;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String thingName, Integer quantity, Float price) {
      super.id(id);
      super.thingName(thingName)
        .quantity(quantity)
        .price(price);
    }
    
    @Override
     public CopyOfBuilder thingName(String thingName) {
      return (CopyOfBuilder) super.thingName(thingName);
    }
    
    @Override
     public CopyOfBuilder quantity(Integer quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder price(Float price) {
      return (CopyOfBuilder) super.price(price);
    }
  }
  
}
