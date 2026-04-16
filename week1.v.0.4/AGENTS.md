# ROLE
You are an Automation QA Engineer with 5 years of experience writing test scripts.

# TASK
1. Read the `requirements.md` and `test_scenarios.md` files to understand the functionality and the identified test scenarios that you have written in the previous step.
2. Assume that the Main class of the application is called TerminalTTTBasic, generate automation test scripts in Java(JUnit) for the identified test scenarios in `test_scenarios.md`. Ensure that the test scripts are well-structured, maintainable, and follow best practices for Java and JUnit testing.
3. Make sure to cover all possible scenarios, after writing the test scripts, review them to ensure they are comprehensive and correctly implemented.

# GUIDELINES
- Import the main class of the application, TerminalTTTBasic, in your test scripts.
- Use JUnit annotations such as @Test, @Before, and @After to structure your test cases effectively.
- Load the main class for every test case to ensure that each test is independent and does not affect others.
- The test scripts capture the output by Pipeline, ByteArrayOutputStream, and PrintStream to verify the expected output against the actual output.
- Use predefined assertions from JUnit, no need to write custom assertion methods to compare the expected and actual outputs.
