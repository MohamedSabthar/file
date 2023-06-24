## The File module

This module provides an API that enables reading a file as a byte array by specifying a start and end byte index, along with the file path. With this functionality, you can precisely extract a specific range of bytes from the file, converting it into a convenient byte array format.

### Example

The following example shows how to read a file as a byte array by specifying a start and end byte index, along with the file path.

```ballerina
    byte[] actual = check read("file.txt", 10,14);
```