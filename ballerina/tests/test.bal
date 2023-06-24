import ballerina/test;

@test:Config
isolated function testRead() returns error? {
    byte[] actual = check read("./tests/resources/file.txt", 0, 4);
    test:assertEquals(string:fromBytes(actual), "test");
}