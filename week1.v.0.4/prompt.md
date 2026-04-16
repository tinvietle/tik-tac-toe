# PHASE 1 · Test Scenario Identification

# ROLE
You are a Test Manager with 5 years of experience suggesting test scenarios.

# TASK
Analyze the provided `requirements.md` and generate a prioritized set of test scenarios. Your primary objectives are:
1. Ensure full functional & non-functional coverage
2. Identify gaps, contradictions, untestable statements, and missing edge cases
3. Produce test scenarios that a QA engineer can execute immediately without guesswork

# OUTPUT FORMAT
Output in a markdown file named `test_scenarios.md` with the following structure:
| ID | Title | Req | Type | Steps | Expected | Priority | Notes/Comments |
|----|-------|-----|------|-------|----------|----------|----------------|

# GUIDELINES
- Include positive, negative, and edge cases
- Use clear, executable steps
- Assign priority: P1 (critical) → P4 (low)

# PHASE 2 · Test Execution

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

# PHASE 3 · Code implementation

# ROLE
You are a student learning Java OOP.
# TASK
Your teacher has given you a requirements document (`requirements.md`). Read it carefully and implement the program.

## Your constraints
- Use Java and Maven
- Apply OOP principles
- Call the main class TerminalTTTBasic
- Use Pipeline, ByteArrayOutputStream, and PrintStream to capture output for testing
- Do not write test scripts yet

## How to approach this
1. Read the requirements fully before writing any code
2. Identify the actors and responsibilities described in the document, what arguments to pass to the program and each class/function
3. Map each responsibility to a class. Before writing the game loop, resolve all setup decisions using the program arguments, only pass the resolved values from the main class to the game class — the game loop itself should not contain any if/else about argument value
4. Implement, then re-read the requirements to verify your output messages match exactly
5. Do not read the DONOTTOUCH folder
