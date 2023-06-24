import ballerina/jballerina.java;
import ballerina/file;

# Return the file size in bytes.
#
# + filePath - The path to the file.
# + return - Return the file size in bytes or an error
public isolated function size(string filePath) returns error|int {
    file:MetaData metaData = check file:getMetaData(filePath);
    return metaData.size;
}

# Reads a file and returns a byte array containing the content within the specified range.
#
# + filePath - The path to the file
# + 'start - The starting index of the range (inclusive)
# + end - The ending index of the range (inclusive)
# + return - Return a byte array or an error
public isolated function read(string filePath, int 'start, int end) returns error|byte[] {
    file:MetaData metaData = check file:getMetaData(filePath);
    if metaData.dir {
        return error(string `Invalid file path provided: "${filePath}" is not a file`);
    }
    if !metaData.readable {
        return error(string `Unable to read file: "${filePath}"`);
    }
    return readChunk(filePath, 'start, end);
}

isolated function readChunk(string filePath, int 'start, int end) returns error|byte[] = @java:Method {
    'class: "io.crates.File"
} external;
