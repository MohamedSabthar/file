import ballerina/test;

@test:Config
isolated function testRead() returns error? {
    byte[] actual = check read("./tests/resources/file.txt", 10,14);
    test:assertEquals(string:fromBytes(actual), "test");
}