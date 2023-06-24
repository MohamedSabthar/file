package io.crates;

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BString;

import java.io.FileInputStream;
import java.io.IOException;

public class File {
    public static Object readChunk(BString path, long start, long end) {
        java.io.File file = new java.io.File(path.getValue());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            long fileSize = file.length();
            end = Math.min(fileSize - 1, end);

            if (start < 0 || end < start || end >= fileSize) {
                return ErrorCreator.createError(StringUtils.fromString("Invalid start or end index provided."));
            }

            long bytesToRead = end - start;
            byte[] fileContent = new byte[(int) bytesToRead];

            // Skip to the starting index
            long skipped = fileInputStream.skip(start);
            if (skipped != start) {
                return ErrorCreator.createError(StringUtils.fromString("Failed to skip to the start index."));
            }

            // Read the file content into the byte array
            int bytesRead = fileInputStream.read(fileContent);
            if (bytesRead != bytesToRead) {
                return ErrorCreator.createError(StringUtils.fromString("Failed to read the file content."));
            }
            return ValueCreator.createArrayValue(fileContent);
        } catch (IOException e) {
            return ErrorCreator.createError(StringUtils.fromString(e.getMessage()));
        }
    }
}