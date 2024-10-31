# Creational Design Patterns

Creational design patterns provide solutions to instantiate objects in a way that suits the particular situation. These patterns help make a system independent of how its objects are created, composed, and represented.

---

## 1. Singleton Pattern

### Purpose
The Singleton pattern ensures that a class has only one instance and provides a global point of access to it. This is useful for cases where a single instance is required to coordinate actions across the system.

### Key Concepts
- **Single Instance**: Only one instance is allowed for the entire application.
- **Global Access**: A static method provides global access to the instance.

### Example Use Cases
- **Database Connection**: Ensures that only one database connection instance is used to avoid unnecessary overhead.
- **Logging Service**: Provides a centralized logging instance throughout the application.
- **Configuration Manager**: Manages application configuration settings in a single instance.

### Examples in Java

- **java.lang.Runtime**: The Runtime class provides access to the Java runtime environment, and only one instance exists for each Java application.

```java
Runtime runtime = Runtime.getRuntime();  // Accessing the singleton instance
```

- **java.awt.Desktop**: This class allows Java applications to launch associated applications to open, edit, or print files. Only one instance is needed per application.

```java
Desktop desktop = Desktop.getDesktop();  // Singleton instance
```

### Pseudocode
```plaintext
class Singleton {
    private static instance = null;

    // Private constructor prevents external instantiation
    private constructor() {}

    static getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

// Usage
var singleton = Singleton.getInstance();
```

---

## 2. Builder Pattern

### Purpose
The Builder pattern provides a way to construct a complex object step by step. It separates the construction of an object from its representation, allowing for greater flexibility in object creation.

### Key Concepts
- **Step-by-Step Construction**: Builds an object incrementally.
- **Separation of Concerns**: The Builder focuses on the construction process, while the Director orchestrates it.

### Example Use Cases
- **Complex Objects with Many Options**: Useful when creating objects with multiple optional fields, such as configurations or forms.
- **Immutable Objects**: Useful in building objects with a fixed state after construction.

### Examples in Java

- **java.lang.StringBuilder and java.lang.StringBuffer**: These classes are used to build strings in a flexible, efficient way, allowing you to append, insert, or modify content without creating new instances.

```plaintext
StringBuilder builder = new StringBuilder();
builder.append("Hello").append(" ").append("World");
String result = builder.toString();  // "Hello World"
```

- **java.nio.ByteBuffer**: This class provides methods to manipulate byte arrays, often used in I/O operations where buffer customization is needed.

```plaintext
ByteBuffer buffer = ByteBuffer.allocate(1024).put((byte) 1).put((byte) 2);
```

- **java.util.stream.Stream.Builder:** The Stream API in Java 8+ provides a builder for creating streams.

```plaintext
Stream.Builder<String> builder = Stream.builder();
builder.add("a").add("b").add("c");
Stream<String> stream = builder.build();
```

### Pseudocode
```plaintext
class ProductBuilder {
    private product;

    constructor() {
        this.product = new Product();
    }

    addPartA() {
        product.add("PartA");
        return this;
    }

    addPartB() {
        product.add("PartB");
        return this;
    }

    build() {
        return product;
    }
}

// Usage
var builder = new ProductBuilder();
var product = builder.addPartA().addPartB().build();
```

---

## 3. Factory Pattern

### Purpose
The Factory pattern defines an interface for creating objects but allows subclasses to alter the type of object that will be created. This approach provides flexibility and encapsulation for object instantiation.

### Key Concepts
- **Encapsulation of Object Creation**: Clients don’t need to know the class of the object they’re creating.
- **Interface for Creating Objects**: Provides a uniform interface for object creation, making it easy to add new types.

### Example Use Cases
- **Database Connections**: Creates different database connection objects based on a specified type (e.g., MySQL, PostgreSQL).
- **UI Components**: Generates different UI components based on input or configuration (e.g., buttons, text fields).
- **Loggers**: Produces different logger instances (e.g., file-based, console-based).

### Examples in Java

- **java.util.Calendar.getInstance()** : The Calendar class provides a static factory method getInstance() that returns a subclass instance (e.g., GregorianCalendar) depending on the locale.

```plaintext
Calendar calendar = Calendar.getInstance();  // Returns appropriate Calendar instance
```

- **java.text.NumberFormat.getInstance()**: NumberFormat provides static factory methods that return instances based on locale and style.

```plaintext
NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
```

- **java.util.ResourceBundle.getBundle()**: This method returns an instance of a ResourceBundle subclass based on the specified base name and locale.

```plaintext
ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US);
```

### Pseudocode
```plaintext
class ProductFactory {
    static createProduct(type) {
        if (type == "TypeA") {
            return new ProductTypeA();
        } else if (type == "TypeB") {
            return new ProductTypeB();
        }
        return null;
    }
}

// Usage
var product = ProductFactory.createProduct("TypeA");
```

---

## 4. Prototype Pattern

### Purpose
The Prototype pattern enables creating new objects by copying an existing object, known as the "prototype." This approach allows for the quick creation of objects while preserving their state.

### Key Concepts
- **Cloning Objects**: Copies an existing object rather than creating a new instance.
- **Efficient Object Creation**: Useful when object creation is costly in terms of performance.

### Example Use Cases
- **Object Pooling**: Reduces the cost of creating new objects by reusing and cloning existing ones.
- **Game Character Generation**: Quickly generate similar characters by cloning an existing prototype with shared characteristics.
- **Document Templates**: Duplicate document templates with preset configurations.

### Examples in Java

- **java.util.ArrayList and java.util.HashMap**: Both classes have a copy constructor, which allows creating a new instance with the same elements or entries as the original, effectively implementing a prototype.

```plaintext
ArrayList<String> original = new ArrayList<>(List.of("a", "b", "c"));
ArrayList<String> copy = new ArrayList<>(original);  // Copy constructor
```

### Pseudocode
```plaintext
class Prototype {
    clone() {
        return Object.assign({}, this);
    }
}

// Usage
var prototype = new Prototype();
var clone = prototype.clone();
```

---

## Summary

| Pattern    | Purpose                                               | Use Case Example                           |
|------------|-------------------------------------------------------|--------------------------------------------|
| Singleton  | Ensure a single instance and global access            | Logger, Database Connection, Configuration |
| Builder    | Construct complex objects step-by-step                | Forms, Configurations, Immutable Objects   |
| Factory    | Define an interface for object creation               | Database Connections, UI Components        |
| Prototype  | Clone existing objects for new instances              | Object Pooling, Document Templates         |
