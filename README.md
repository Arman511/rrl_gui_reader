# RRL GUI Reader

RRL GUI Reader is a Java-based GUI application for browsing and reading fiction from Royal Road.

## Features

-   Search for fiction by title, author, or keywords.
-   View search results and select a book.
-   Navigate through chapters of a selected book.
-   Light and dark mode support.

## Requirements

-   Java 8 or higher
-   Maven 3.6 or higher

## Setup and Build

1. Clone the repository:
    ```bash
    git clone https://github.com/Arman511/rrl_gui_reader.git
    cd rrl_gui_reader
    ```

## Compiling to a JAR File

To compile the project into a JAR file, use the following Maven command:

```bash
mvn clean package
```

This will generate a JAR file in the `target/` directory, typically named `rrl_gui_reader-1.jar`.

## Running the Application

To run the application, use the following Maven command:

```bash
mvn exec:java -Dexec.mainClass="com.arman511.App"
```

## Running from the JAR File

To run the application from the compiled JAR file, use the following command:

```bash
java -jar target/rrl_gui_reader-1.jar
```

### Notes

-   If you encounter warnings about restricted methods, you can suppress them by adding the following JVM argument:
    ```bash
    --enable-native-access=ALL-UNNAMED
    ```
    Example:
    ```bash
    mvn exec:java -Dexec.mainClass="com.arman511.App" -Dexec.args="--enable-native-access=ALL-UNNAMED"
    ```
-   Ensure that all dependencies are included in the JAR file or are available in the classpath when running the application.

## License

This project is licensed under the MIT License. See the [`LICENSE`](LICENSE) file for details.
