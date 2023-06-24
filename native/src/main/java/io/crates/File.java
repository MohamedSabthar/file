package io.crates;

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BString;

import java.io.FileInputStream;
import java.io.IOException;

public class File {
    public static Object readChunk(BString path, long start, long length) {
        java.io.File file = new java.io.File(path.getValue());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            long fileSize = file.length();
            length = Math.min(fileSize - 1, length);

            if (start < 0 || length < 0) {
                return ErrorCreator.createError(StringUtils.fromString("Invalid start index or length provided."));
            }

            byte[] fileContent = new byte[(int) length];

            // Skip to the starting index
            long skipped = fileInputStream.skip(start);
            if (skipped != start) {
                return ErrorCreator.createError(StringUtils.fromString("Failed to skip to the start index."));
            }

            fileInputStream.read(fileContent);
            return ValueCreator.createArrayValue(fileContent);
        } catch (IOException e) {
            return ErrorCreator.createError(StringUtils.fromString(e.getMessage()));
        }
    }
}