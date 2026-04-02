# Tic-Tac-Toe Detailed Test Cases

## Requirement Reference Index
- REQ-01: Program start option validation (`1` or `2`), invalid option message.
- REQ-02: Board has 9 cells mapped from 1 to 9 (top-to-bottom, left-to-right).
- REQ-03: Startup output order: `Hello!`, initial board with all `0`, then `Player#<n>'s turn`.
- REQ-04: Human input validation and occupied-cell handling.
- REQ-05: Human valid move outcomes: win, draw, or continue.
- REQ-06: Computer move strategy: pick first available cell from 1 to 9.
- REQ-07: Computer move outcomes: win, draw, or continue.
- REQ-08: Board rendering reflects state using `1` (human), `2` (computer), `0` (free).
- REQ-09: OOP paradigm compliance.
- GAP-01: Behavior with extra CLI arguments is not specified.
- GAP-02: Input parsing details for whitespace/blank line are not specified.
- GAP-03: EOF/interrupted input behavior is not specified.
- GAP-04: Output format strictness (spaces/newlines/borders) is not fully specified.
- GAP-05: Message apostrophe style is inconsistent (`'` vs `’`).

## TC-01 Startup Without Argument
**Title**
Startup without command-line argument

**Description**
Validate program behavior when no start-player argument is provided.

**Priority**
P0 - Critical

**Preconditions**
- Java runtime is installed.
- Program is compiled successfully.
- Terminal is open at project directory.

**Test steps (numbered and detailed)**
1. Run the program without any argument.
2. Capture the first terminal output line.
3. Observe whether any game board or turn prompt is shown afterward.

**Expected results**
- Terminal prints `Please, input a valid option [1-2]`.
- Game board is not started.
- Program terminates (or does not proceed to gameplay).

**Requirement reference**
REQ-01

**Automation potential (Yes/No/Partial)**
Yes

## TC-02 Startup With Invalid Argument
**Title**
Startup with invalid argument value

**Description**
Verify rejection of invalid start option values.

**Priority**
P0 - Critical

**Preconditions**
- Java runtime installed.
- Program compiled.

**Test steps (numbered and detailed)**
1. Run program with argument `0`.
2. Record output.
3. Repeat with `3`.
4. Repeat with `-1`.
5. Repeat with `abc`.

**Expected results**
- For each run, output is `Please, input a valid option [1-2]`.
- Program does not start gameplay in any run.

**Requirement reference**
REQ-01

**Automation potential (Yes/No/Partial)**
Yes

## TC-03 Startup With Extra Arguments
**Title**
Startup with extra arguments

**Description**
Evaluate behavior when more than one CLI argument is supplied.

**Priority**
P1 - High

**Preconditions**
- Program compiled.

**Test steps (numbered and detailed)**
1. Run program as `java ... 1 extra`.
2. Observe whether game starts as Player#1 or input is rejected.
3. Run program as `java ... 2 extra`.
4. Capture behavior and compare with step 2.

**Expected results**
- Behavior is consistent and documented.
- If rejected, message should be `Please, input a valid option [1-2]`.
- If accepted, game should start using first valid argument.

**Requirement reference**
GAP-01

**Automation potential (Yes/No/Partial)**
Partial

## TC-04 Startup Message and Order
**Title**
Verify startup output order for valid option

**Description**
Ensure startup messages and initial board display sequence are correct.

**Priority**
P0 - Critical

**Preconditions**
- Program compiled.

**Test steps (numbered and detailed)**
1. Run with argument `1`.
2. Verify first line is `Hello!`.
3. Verify next output is initial board with 9 zeros.
4. Verify next line is `Player#1's turn` (or exact implemented equivalent required by spec).
5. Repeat run with argument `2` and verify final line shows Player#2 turn.

**Expected results**
- Exact order is `Hello!` -> initial board -> starting player turn message.
- Starting player in prompt matches argument.

**Requirement reference**
REQ-03

**Automation potential (Yes/No/Partial)**
Yes

## TC-05 Initial Board Mapping
**Title**
Initial board structure and cell mapping

**Description**
Validate board has 9 positions and logical mapping 1..9 in row-major order.

**Priority**
P1 - High

**Preconditions**
- Program started with valid argument.

**Test steps (numbered and detailed)**
1. At game start, inspect initial board layout.
2. Enter move `1` and verify top-left position is updated.
3. Restart game, enter move `5`, verify center position is updated.
4. Restart game, enter move `9`, verify bottom-right position is updated.

**Expected results**
- Board has 3x3 structure.
- Move-to-cell mapping follows 1..9 top-left to bottom-right.

**Requirement reference**
REQ-02, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## TC-06 Human Non-Integer Input
**Title**
Human input validation for non-integer values

**Description**
Verify message and turn retention after invalid non-numeric entry.

**Priority**
P0 - Critical

**Preconditions**
- Game started with Player#1 turn.

**Test steps (numbered and detailed)**
1. At Player#1 prompt, type `x` and press Enter.
2. Observe validation message.
3. Confirm next prompt remains Player#1 turn.
4. Repeat with `1.5` and `abc`.

**Expected results**
- Each invalid value triggers `Please, input a valid number [1-9]`.
- Prompt returns to Player#1 turn.
- Board remains unchanged.

**Requirement reference**
REQ-04

**Automation potential (Yes/No/Partial)**
Yes

## TC-07 Human Out-of-Range Integer Input
**Title**
Human input validation for out-of-range integers

**Description**
Ensure integer values outside 1..9 are rejected.

**Priority**
P0 - Critical

**Preconditions**
- Game running at Player#1 turn.

**Test steps (numbered and detailed)**
1. Enter `0`.
2. Enter `10`.
3. Enter `-5`.
4. After each entry, observe output and prompt.

**Expected results**
- For each value, output is `Please, input a valid number [1-9]`.
- Turn remains Player#1.
- Board does not change.

**Requirement reference**
REQ-04

**Automation potential (Yes/No/Partial)**
Yes

## TC-08 Human Chooses Occupied Cell
**Title**
Occupied-cell rejection for human move

**Description**
Ensure selecting an already occupied cell is blocked with correct message.

**Priority**
P0 - Critical

**Preconditions**
- Game running.
- At least one cell is occupied.

**Test steps (numbered and detailed)**
1. Start game and enter `1` as human move.
2. Allow computer move to complete.
3. On next human turn, enter `1` again.
4. Inspect message, turn prompt, and board state.

**Expected results**
- Output includes `The cell is occupied!`.
- Prompt remains Player#1 turn.
- Board state remains unchanged from before invalid attempt.

**Requirement reference**
REQ-04

**Automation potential (Yes/No/Partial)**
Yes

## TC-09 Human Valid Move Updates Board
**Title**
Human valid move updates board correctly

**Description**
Verify board update and flow transition after valid human move.

**Priority**
P0 - Critical

**Preconditions**
- Game started with Player#1 first.

**Test steps (numbered and detailed)**
1. Enter valid free cell value `5`.
2. Observe immediate board print after move.
3. Verify cell 5 now shows `1`.
4. Verify game proceeds to computer turn if no terminal condition is met.

**Expected results**
- Board is updated with `1` at selected position.
- Board is displayed after move.
- Game flow transitions correctly to computer logic.

**Requirement reference**
REQ-05, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## TC-10 Computer Move Strategy First Free Cell
**Title**
Computer uses first-available-cell strategy

**Description**
Validate deterministic computer move selection rule.

**Priority**
P0 - Critical

**Preconditions**
- Game started with Player#1 first.

**Test steps (numbered and detailed)**
1. Human selects cell `5`.
2. Observe computer move in resulting board.
3. Verify computer occupied cell `1` (first free cell).
4. Continue with human move `2` (if free) and re-check computer picks next first free.

**Expected results**
- Computer always chooses smallest-numbered available cell.
- No randomness is observed.

**Requirement reference**
REQ-06

**Automation potential (Yes/No/Partial)**
Yes

## TC-11 Computer Move Updates Board
**Title**
Computer move reflected as value 2 on board

**Description**
Ensure board updates with computer marker and game resumes properly.

**Priority**
P0 - Critical

**Preconditions**
- At least one human move completed successfully.

**Test steps (numbered and detailed)**
1. Execute one valid human move.
2. Wait for computer move.
3. Inspect board output after computer action.
4. Verify newly occupied computer cell displays `2`.
5. Confirm next turn is Player#1 unless game ended.

**Expected results**
- Computer move is visible as value `2`.
- Board is printed after computer move.
- Turn cycles back to human when game is not over.

**Requirement reference**
REQ-07, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## TC-11A Turn Handoff After Human Move
**Title**
After a valid human move, computer takes the next turn

**Description**
Explicitly verify the turn handoff order from human to computer and then back to human when no terminal outcome occurs.

**Priority**
P0 - Critical

**Preconditions**
- Program started with argument `1` (human starts).
- Human first input is a free valid cell.

**Test steps (numbered and detailed)**
1. Start game with argument `1`.
2. Enter human move `5`.
3. Verify board update contains `1` in center cell.
4. Verify next prompt is `Player#2's turn`.
5. Let computer move and verify next prompt is `Player#1's turn` if game has not ended.

**Expected results**
- Turn immediately switches to computer after valid human move.
- After computer move, turn returns to human when no win/draw occurs.
- No duplicated or skipped turn prompt appears.

**Requirement reference**
REQ-05, REQ-06, REQ-07

**Automation potential (Yes/No/Partial)**
Yes

## TC-11B Turn Handoff After Computer Move
**Title**
When computer starts, human takes the next turn

**Description**
Verify the opposite direction handoff (computer to human) and subsequent return to computer in a non-terminal path.

**Priority**
P0 - Critical

**Preconditions**
- Program started with argument `2` (computer starts).
- Human provided input is valid and free.

**Test steps (numbered and detailed)**
1. Start game with argument `2`.
2. Verify first prompt is `Player#2's turn`.
3. After computer move, verify prompt is `Player#1's turn`.
4. Enter valid human move `5`.
5. Verify prompt returns to `Player#2's turn` if no terminal condition is reached.

**Expected results**
- Turn immediately switches to human after computer move.
- Turn returns to computer after valid human move.
- Alternation remains strict until win/draw.

**Requirement reference**
REQ-03, REQ-05, REQ-06, REQ-07

**Automation potential (Yes/No/Partial)**
Yes

## TC-12 Human Win Detection
**Title**
Human win condition and termination

**Description**
Confirm system detects human winning line and ends game correctly.

**Priority**
P0 - Critical

**Preconditions**
- Controlled sequence is available to let human complete a line.

**Test steps (numbered and detailed)**
1. Start with Player#1.
2. Execute moves to create a human winning row/column/diagonal (for example a row where computer does not block due to first-free logic).
3. Enter final winning move.
4. Observe board output and terminal message.
5. Verify no additional turn prompt appears.

**Expected results**
- Updated board is shown.
- Message `Player#1 won!` is printed.
- Program terminates immediately after result.

**Requirement reference**
REQ-05

**Automation potential (Yes/No/Partial)**
Partial

## TC-13 Computer Win Detection
**Title**
Computer win condition and termination

**Description**
Confirm system detects computer winning line and ends game correctly.

**Priority**
P0 - Critical

**Preconditions**
- Controlled move sequence where computer can complete winning line.

**Test steps (numbered and detailed)**
1. Start game and provide human moves that allow computer to form a winning line.
2. Observe each computer move.
3. When computer completes line, verify immediate outcome handling.

**Expected results**
- Updated board is displayed.
- Message `Player#2 won!` appears.
- Program ends with no further input prompt.

**Requirement reference**
REQ-07

**Automation potential (Yes/No/Partial)**
Partial

## TC-14 Draw After Human Move
**Title**
Draw detection when human fills last free cell

**Description**
Verify draw is announced when board becomes full with no winner after human move.

**Priority**
P0 - Critical

**Preconditions**
- Board state prepared so one free cell remains and no winning line exists.

**Test steps (numbered and detailed)**
1. Play or preload a near-endgame state with one free cell.
2. Human selects final free cell.
3. Observe board and result message.

**Expected results**
- Board displays final full state.
- Message `It is a draw!` appears.
- Program terminates.

**Requirement reference**
REQ-05

**Automation potential (Yes/No/Partial)**
Partial

## TC-15 Draw After Computer Move
**Title**
Draw detection when computer fills last free cell

**Description**
Verify draw is announced when board becomes full with no winner after computer move.

**Priority**
P0 - Critical

**Preconditions**
- Near-endgame state where computer takes final free cell without creating win.

**Test steps (numbered and detailed)**
1. Provide moves until one free cell remains and it is computer turn.
2. Allow computer to play final free cell.
3. Observe terminal output.

**Expected results**
- Final board is printed.
- Message `It is a draw!` appears.
- Program terminates.

**Requirement reference**
REQ-07

**Automation potential (Yes/No/Partial)**
Partial

## TC-16 Win vs Draw Precedence On Last Cell
**Title**
Winner precedence when last move also fills board

**Description**
Validate that win is prioritized over draw if both conditions occur at final move.

**Priority**
P1 - High

**Preconditions**
- Board state can be reached where last empty cell completes a winning line.

**Test steps (numbered and detailed)**
1. Prepare state with one empty cell remaining.
2. Ensure that placing marker in that cell creates winning line.
3. Execute final move.
4. Capture result message.

**Expected results**
- Winner message is displayed (`Player#1 won!` or `Player#2 won!` as applicable).
- Draw message is not shown.

**Requirement reference**
REQ-05, REQ-07

**Automation potential (Yes/No/Partial)**
Partial

## TC-17 Board State Consistency Through Full Game
**Title**
Board state consistency across all turns

**Description**
Ensure each board print is cumulative and consistent with move history.

**Priority**
P0 - Critical

**Preconditions**
- New game started.

**Test steps (numbered and detailed)**
1. Play complete game from start to termination.
2. After each move, log board output.
3. Validate that previously occupied cells never revert to `0`.
4. Validate human cells remain `1` and computer cells remain `2`.

**Expected results**
- Every printed board accurately reflects all previous valid moves.
- No overwritten or reset cell values appear.

**Requirement reference**
REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## TC-18 No Input Accepted After Game End
**Title**
Input handling after terminal game state

**Description**
Confirm program stops accepting input after win or draw.

**Priority**
P1 - High

**Preconditions**
- Game can be completed to win or draw.

**Test steps (numbered and detailed)**
1. Play game until result is printed.
2. Attempt to type additional input and press Enter.
3. Observe terminal process status.

**Expected results**
- Program is already terminated.
- Additional input is not processed by game.

**Requirement reference**
REQ-05, REQ-07

**Automation potential (Yes/No/Partial)**
Yes

## TC-19 Input With Leading/Trailing Spaces
**Title**
Whitespace handling in human input

**Description**
Assess behavior for inputs with spaces and blank lines.

**Priority**
P2 - Medium

**Preconditions**
- Game waiting for Player#1 input.

**Test steps (numbered and detailed)**
1. Enter ` 5`.
2. Enter `5 ` in a new run or next applicable turn.
3. Enter blank line (press Enter only).
4. Record parser behavior and messages for each case.

**Expected results**
- Behavior is consistent and documented.
- Invalid forms should trigger `Please, input a valid number [1-9]` if strict parsing is used.

**Requirement reference**
GAP-02

**Automation potential (Yes/No/Partial)**
Partial

## TC-20 EOF or Interrupted Input
**Title**
EOF/interruption handling during input

**Description**
Validate robustness when input stream is terminated unexpectedly.

**Priority**
P2 - Medium

**Preconditions**
- Game running at input prompt.

**Test steps (numbered and detailed)**
1. During Player#1 input, send EOF (Ctrl+Z then Enter on Windows).
2. Observe whether exception stack trace appears.
3. Observe whether process exits cleanly.

**Expected results**
- Program handles interruption gracefully (clean exit or controlled message).
- No unhandled exception is printed.

**Requirement reference**
GAP-03

**Automation potential (Yes/No/Partial)**
No

## TC-21 Board Rendering Format Strictness
**Title**
Board output format strictness validation

**Description**
Check strict compliance of board rendering (line breaks, spacing, separators).

**Priority**
P1 - High

**Preconditions**
- Program running with deterministic sequence.

**Test steps (numbered and detailed)**
1. Capture terminal output from startup through several moves.
2. Compare board output with expected formatting baseline.
3. Verify count of lines per board render is stable.
4. Verify no extra leading/trailing spaces if strict mode required.

**Expected results**
- Board format is consistent on every render.
- Any deviations are documented as formatting defects.

**Requirement reference**
REQ-03, REQ-08, GAP-04

**Automation potential (Yes/No/Partial)**
Yes

## TC-22 OOP Compliance Review
**Title**
Static review of OOP design compliance

**Description**
Review source structure against OOP principles requirement.

**Priority**
P2 - Medium

**Preconditions**
- Source code is available.

**Test steps (numbered and detailed)**
1. Inspect classes and responsibilities.
2. Verify board state management is encapsulated.
3. Verify move validation and game flow are separated appropriately.
4. Check for clear abstraction boundaries between player types.
5. Record findings with examples.

**Expected results**
- Design demonstrates basic OOP principles (encapsulation, clear class responsibilities).
- Any procedural-only implementation is flagged for review.

**Requirement reference**
REQ-09

**Automation potential (Yes/No/Partial)**
No

## TC-23 Output String Consistency
**Title**
Consistency of message text and apostrophe style

**Description**
Ensure all user-facing messages are consistent and align with expected spec wording.

**Priority**
P1 - High

**Preconditions**
- Multiple gameplay paths executed (invalid input, occupied, win, draw).

**Test steps (numbered and detailed)**
1. Trigger each distinct message path.
2. Capture exact output text for each message.
3. Compare against expected message catalog.
4. Check apostrophe style consistency in `Player#1's turn` text.

**Expected results**
- Message text is consistent across all occurrences.
- Any mismatch between `'` and `’` is logged and clarified with stakeholders.

**Requirement reference**
REQ-03, REQ-04, REQ-05, REQ-07, GAP-05

**Automation potential (Yes/No/Partial)**
Yes
