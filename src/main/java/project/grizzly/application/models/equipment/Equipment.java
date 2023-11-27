package project.grizzly.application.models.equipment;

import project.grizzly.application.models.Constraint;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.TableConfig;
import project.grizzly.application.models.interfaces.ITableEntity;
import project.grizzly.application.models.enums.Condition;
import project.grizzly.application.models.enums.RentalStatus;
import project.grizzly.application.models.enums.RentedPer;
import project.grizzly.application.models.enums.FormFieldType;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Equipment base class
 */
@Entity(name = "Equipment")
@Table(name = "Equipment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Equipment implements Serializable, ITableEntity {
    @Id
    @Column(name = "equipmentId")
    protected String equipmentId;

    @Column(name = "name")
    protected String name;

    @Transient
    protected ImageIcon image;

    @Column(name = "image")
    private byte[] imageData;

    @Column(name = "description")
    protected String description;

    @Enumerated(EnumType.STRING)
    protected RentalStatus rentalStatus;

    @Column(name = "category")
    protected String category;

    @Column(name = "price")
    protected Double price;

    @Column(name = "rentedPer")
    @Enumerated(EnumType.STRING)
    protected RentedPer rentedPer;

    @Enumerated(EnumType.STRING)
    @Column(name = "health")
    protected Condition condition;

    @Column(name = "type")
    protected String type;

    @Column(name = "nextAvailableDate")
    protected LocalDateTime nextAvailableDate;

    /**
     * Default constructor
     */
    public Equipment() {
        equipmentId = "";
        name = "";
        description = "";
        category = "";
        price = 0.0;
        type = "";
        nextAvailableDate = LocalDateTime.now();
        rentedPer = RentedPer.DAY;
        rentalStatus = RentalStatus.AVAILABLE;
        condition = Condition.EXCELLENT;
        setDefaultImage();
    }

    /**
     * Creates an equipment data class
     *
     * @param equipmentId
     * @param name
     * @param description
     * @param category
     * @param price
     * @param type
     * @param nextAvailableDate
     * @param rentedPer
     * @param rentalStatus
     */
    public Equipment(ImageIcon image, String equipmentId, String name, String description, Condition condition, String category, Double price,
                     String type, LocalDateTime nextAvailableDate, RentedPer rentedPer, RentalStatus rentalStatus) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.type = type;
        this.nextAvailableDate = nextAvailableDate;
        this.rentedPer = rentedPer;
        this.rentalStatus = rentalStatus;
        this.condition = condition;
        this.image = image;
        setDefaultImage();
    }

    /**
     * COpy constructor
     *
     * @param other equipment to copy
     */
    public Equipment(Equipment other) {
        this.equipmentId = other.equipmentId;
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.price = other.price;
        this.type = other.type;
        this.nextAvailableDate = other.nextAvailableDate;
        this.rentedPer = other.rentedPer;
        this.image = other.image;
        this.rentalStatus = other.rentalStatus;
        this.condition = other.condition;
        setDefaultImage();
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrice(Number price) {
        this.price = price.doubleValue();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getNextAvailableDate() {
        return nextAvailableDate;
    }

    public void setNextAvailableDate(LocalDateTime nextAvailableDate) {
        this.nextAvailableDate = nextAvailableDate;
    }

    public RentedPer getRentedPer() {
        return rentedPer;
    }

    public void setRentedPer(RentedPer rentedPer) {
        this.rentedPer = rentedPer;
    }

    public void setRentedPer(String rentedPer) {
        this.rentedPer = RentedPer.valueOf(rentedPer);
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = RentalStatus.valueOf(rentalStatus);
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
        this.imageData = imageIconToByteArray(image);
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
        image = byteArrayToImageIcon(imageData);

    }

    private void setDefaultImage() {
        if (image != null) return;
        try {
            ClassLoader loader = this.getClass().getClassLoader();
            ImageIcon img = new ImageIcon(ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/equipment-placeholder.png")).toURI().getPath())));
            setImage(img);
        } catch (IOException | URISyntaxException e) {

        }
    }

    // Convert ImageIcon to byte array using serialization
    private byte[] imageIconToByteArray(ImageIcon imageIcon) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(imageIcon);
            oos.close();
            return baos.toByteArray();
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }

    // Convert byte array back to ImageIcon using deserialization
    private ImageIcon byteArrayToImageIcon(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close();

            if (obj instanceof ImageIcon) {
                return (ImageIcon) obj;
            } else {
                throw new IllegalArgumentException("The byte array does not represent an ImageIcon");
            }
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId='" + equipmentId + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", rentalStatus=" + rentalStatus +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", rentedPer=" + rentedPer +
                ", condition=" + condition +
                ", type='" + type + '\'' +
                ", nextAvailableDate=" + nextAvailableDate +
                '}';
    }

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, condition, category, price, type, rentedPer, rentalStatus, nextAvailableDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Condition", "Category", "Price", "Type", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {

        List<FieldConfig> fcs = configFields();

        return new TableConfig(getTableTitles(), null, fcs);
    }

    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig id = new FieldConfig(String.class, "setEquipmentId", "getEquipmentId", "Id", FormFieldType.TEXT, true);
        id.addConstraint(new Constraint(Constraint.NOT_NULL, "Id cannot be empty!"));
        fcs.add(id);

        FieldConfig name = new FieldConfig(String.class, "setName", "getName", "Name:", FormFieldType.TEXT);
        name.addConstraint(new Constraint(Constraint.NOT_NULL, "Name cannot be empty!"));
        fcs.add(name);

        FieldConfig description = new FieldConfig(String.class, "setDescription", "getDescription", "Description:", FormFieldType.LONGTEXT, 350.0, 0.0);
        fcs.add(description);

        FieldConfig cdn = new FieldConfig(Condition.class, "setCondition", "getCondition", "Condition:", FormFieldType.SELECT, Condition.values());
        fcs.add(cdn);

        FieldConfig category = new FieldConfig(String.class, "setCategory", "getCategory", "Category:", FormFieldType.TEXT);
        fcs.add(category);

        FieldConfig price = new FieldConfig(Double.class, "setPrice", "getPrice", "Price:", FormFieldType.NUMBER, null, 0.0);
        price.addConstraint(new Constraint(Constraint.GREATER, 0, "Value must be greater than 0!"));
        fcs.add(price);

        FieldConfig type = new FieldConfig(String.class, "setType", "getType", "Type:", FormFieldType.TEXT);
        type.addConstraint(new Constraint(Constraint.NOT_NULL, "Type cannot be empty!"));
        fcs.add(type);

        FieldConfig rentedPer = new FieldConfig(RentedPer.class, "setRentedPer", "getRentedPer", "Rented Per:", FormFieldType.SELECT, RentedPer.values());
        fcs.add(rentedPer);

        FieldConfig status = new FieldConfig(RentalStatus.class, "setRentalStatus", "getRentalStatus", "Rental Status:", FormFieldType.SELECT, RentalStatus.values());
        fcs.add(status);

        FieldConfig nextAvailable = new FieldConfig(String.class, "setNextAvailableDate", "getNextAvailableDate", "Next Available Date:", FormFieldType.DATE);
        nextAvailable.addConstraint(new Constraint(Constraint.NOT_NULL, "Next available date cannot be empty!"));
        fcs.add(nextAvailable);

        return fcs;
    }
}