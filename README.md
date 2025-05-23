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
    git clone <repository-url>
    cd rrl_gui_reader
    ```

## Running the Application

To run the application, use the following Maven command:

```bash
mvn exec:java -Dexec.mainClass="com.arman511.App"
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

## License

This project is licensed under the MIT License. See the [`LICENSE`](LICENSE) file for details.
