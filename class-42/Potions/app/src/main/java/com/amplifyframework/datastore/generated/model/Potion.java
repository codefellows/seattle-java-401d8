package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Potion type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Potions")
public final class Potion implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField COLOR = field("color");
  public static final QueryField SUCCESS = field("success");
  public static final QueryField STUDENT = field("potionStudentId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  public @ModelField(targetType="String") String name;
  public @ModelField(targetType="String") String color;
  public @ModelField(targetType="Boolean") Boolean success;
  public @ModelField(targetType="Student") @BelongsTo(targetName = "potionStudentId", type = Student.class) Student student;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getColor() {
      return color;
  }
  
  public Boolean getSuccess() {
      return success;
  }
  
  public Student getStudent() {
      return student;
  }
  
  private Potion(String id, String name, String color, Boolean success, Student student) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.success = success;
    this.student = student;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Potion potion = (Potion) obj;
      return ObjectsCompat.equals(getId(), potion.getId()) &&
              ObjectsCompat.equals(getName(), potion.getName()) &&
              ObjectsCompat.equals(getColor(), potion.getColor()) &&
              ObjectsCompat.equals(getSuccess(), potion.getSuccess()) &&
              ObjectsCompat.equals(getStudent(), potion.getStudent());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getColor())
      .append(getSuccess())
      .append(getStudent())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Potion {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("color=" + String.valueOf(getColor()) + ", ")
      .append("success=" + String.valueOf(getSuccess()) + ", ")
      .append("student=" + String.valueOf(getStudent()))
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
  public static Potion justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Potion(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      color,
      success,
      student);
  }
  public interface BuildStep {
    Potion build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep color(String color);
    BuildStep success(Boolean success);
    BuildStep student(Student student);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String color;
    private Boolean success;
    private Student student;
    @Override
     public Potion build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Potion(
          id,
          name,
          color,
          success,
          student);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep color(String color) {
        this.color = color;
        return this;
    }
    
    @Override
     public BuildStep success(Boolean success) {
        this.success = success;
        return this;
    }
    
    @Override
     public BuildStep student(Student student) {
        this.student = student;
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
    private CopyOfBuilder(String id, String name, String color, Boolean success, Student student) {
      super.id(id);
      super.name(name)
        .color(color)
        .success(success)
        .student(student);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder color(String color) {
      return (CopyOfBuilder) super.color(color);
    }
    
    @Override
     public CopyOfBuilder success(Boolean success) {
      return (CopyOfBuilder) super.success(success);
    }
    
    @Override
     public CopyOfBuilder student(Student student) {
      return (CopyOfBuilder) super.student(student);
    }
  }
  
}
