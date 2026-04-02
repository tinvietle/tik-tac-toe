# Tic-Tac-Toe Additional Test Cases (Advanced Coverage)

## Requirement Reference Index
- REQ-01: Program start option validation (`1` or `2`), invalid option message.
- REQ-02: Board has 9 cells mapped from 1 to 9 (top-to-bottom, left-to-right).
- REQ-03: Startup output order: `Hello!`, initial board with all `0`, then `Player#<n>'s turn`.
- REQ-04: Human input validation and occupied-cell handling.
- REQ-05: Human valid move outcomes: win, draw, or continue.
- REQ-06: Computer move strategy: pick first available cell from 1 to 9.
- REQ-07: Computer move outcomes: win, draw, or continue.
- REQ-08: Board rendering reflects state using `1` (human), `2` (computer), `0` (free).
- GAP-01: Behavior with extra CLI arguments is not specified.
- GAP-02: Input parsing details for whitespace/blank line are not specified.
- GAP-03: EOF/interrupted input behavior is not specified.

## Edge Cases and Boundary Conditions

## ATC-01 Lowest Valid Human Cell Boundary
**Title**
Boundary check for lowest valid human move (`1`)

**Description**
Verify move `1` is accepted and mapped correctly to the board.

**Priority**
P0 - Critical

**Preconditions**
- Program started with argument `1`.
- Board is in initial state.

**Test steps (numbered and detailed)**
1. At Player#1 turn, input `1` and press Enter.
2. Observe rendered board.
3. Confirm game continues according to normal flow.

**Expected results**
- Move is accepted with no validation error.
- Top-left cell is updated to `1`.
- Remaining game proceeds normally.

**Requirement reference**
REQ-02, REQ-05, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## ATC-02 Highest Valid Human Cell Boundary
**Title**
Boundary check for highest valid human move (`9`)

**Description**
Verify move `9` is accepted and mapped correctly to the board.

**Priority**
P0 - Critical

**Preconditions**
- Program started with argument `1`.
- Cell `9` is free at input time.

**Test steps (numbered and detailed)**
1. At Player#1 turn, input `9`.
2. Observe board state.
3. Confirm bottom-right cell value and turn progression.

**Expected results**
- Move is accepted.
- Bottom-right cell is updated to `1`.
- Game continues to computer move if no terminal condition is met.

**Requirement reference**
REQ-02, REQ-05, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## ATC-03 Start Option Boundary Pair
**Title**
Boundary check for valid startup options (`1` and `2`)

**Description**
Confirm both accepted startup values produce correct starting turn behavior.

**Priority**
P0 - Critical

**Preconditions**
- Program compiled and runnable.

**Test steps (numbered and detailed)**
1. Run the program with startup argument `1`.
2. Verify startup output and first turn prompt.
3. Run again with startup argument `2`.
4. Verify startup output and first turn prompt.

**Expected results**
- Both `1` and `2` are accepted.
- Prompt correctly reflects the selected starting player.

**Requirement reference**
REQ-01, REQ-03

**Automation potential (Yes/No/Partial)**
Yes

## Negative Test Cases With Invalid Inputs

## ATC-04 Extremely Large Integer Input
**Title**
Invalid oversized integer for human move

**Description**
Verify robust handling of very large numeric input outside range.

**Priority**
P1 - High

**Preconditions**
- Program started and waiting for Player#1 input.

**Test steps (numbered and detailed)**
1. Input `999999999` and press Enter.
2. Observe validation message and current turn.
3. Repeat with `2147483648`.

**Expected results**
- Invalid-number message is shown.
- Player#1 turn remains active.
- Program does not crash or throw unhandled exception.

**Requirement reference**
REQ-04

**Automation potential (Yes/No/Partial)**
Yes

## ATC-05 Special Characters and Mixed Tokens
**Title**
Invalid symbolic and mixed-character inputs

**Description**
Validate rejection of symbols and mixed tokens as human move input.

**Priority**
P1 - High

**Preconditions**
- Program at Player#1 input prompt.

**Test steps (numbered and detailed)**
1. Input `@`.
2. Input `3a`.
3. Input `a3`.
4. Input `5,`.
5. Observe output after each entry.

**Expected results**
- Each entry triggers `Please, input a valid number [1-9]`.
- Player#1 turn prompt appears again each time.
- Board is unchanged.

**Requirement reference**
REQ-04

**Automation potential (Yes/No/Partial)**
Yes

## ATC-06 Multi-Token Single-Line Input
**Title**
Invalid multi-token input in one line

**Description**
Check handling when user inputs more than one token in a single submit.

**Priority**
P1 - High

**Preconditions**
- Program at Player#1 turn.

**Test steps (numbered and detailed)**
1. Input `5 6` and press Enter.
2. Observe validation message and prompt behavior.
3. Enter valid single value `5` afterward.

**Expected results**
- `5 6` is rejected as invalid input.
- Turn remains Player#1.
- Subsequent valid input is accepted and game recovers.

**Requirement reference**
REQ-04, GAP-02

**Automation potential (Yes/No/Partial)**
Yes

## Race Conditions and Timing Issues

## ATC-07 Rapid Consecutive Inputs During Human Turn
**Title**
Rapid-fire input burst at human prompt

**Description**
Assess stability when several inputs are pasted quickly before output refresh.

**Priority**
P1 - High

**Preconditions**
- Program running interactively in terminal.
- Player#1 turn is active.

**Test steps (numbered and detailed)**
1. Paste a burst such as `5` followed immediately by `6` and `7` on separate lines.
2. Observe which inputs are consumed in current and next turns.
3. Verify board state and turn messages remain consistent.

**Expected results**
- Only one move is applied per turn.
- No turn-skipping or double-application occurs.
- Program remains stable without deadlock or crash.

**Requirement reference**
REQ-04, REQ-05, REQ-07, REQ-08

**Automation potential (Yes/No/Partial)**
Partial

## ATC-08 Fast Invalid-Then-Valid Sequence
**Title**
Timing check with immediate invalid then valid input

**Description**
Validate state consistency when user submits invalid and valid entries rapidly.

**Priority**
P1 - High

**Preconditions**
- Program at Player#1 prompt.

**Test steps (numbered and detailed)**
1. Rapidly enter `x` then `5`.
2. Observe whether invalid input handling completes before valid move processing.
3. Verify board change count.

**Expected results**
- Invalid message is shown for `x`.
- Exactly one valid move (`5`) is applied once.
- No duplicate board updates occur.

**Requirement reference**
REQ-04, REQ-05, REQ-08

**Automation potential (Yes/No/Partial)**
Partial

## ATC-09 Input Near Program Termination
**Title**
Input timing around win/draw completion

**Description**
Evaluate behavior when user submits extra input right as game reaches terminal state.

**Priority**
P2 - Medium

**Preconditions**
- Board is one move away from win or draw.

**Test steps (numbered and detailed)**
1. Prepare near-terminal board state.
2. Enter final deciding move and immediately queue another input line.
3. Observe process lifecycle and consumed inputs.

**Expected results**
- Program prints final result and terminates cleanly.
- Queued extra input is ignored after termination.
- No additional turn prompt is displayed.

**Requirement reference**
REQ-05, REQ-07

**Automation potential (Yes/No/Partial)**
Partial

## Unusual but Valid User Behaviors

## ATC-10 Always Select Highest Free Cell
**Title**
Valid non-strategic user pattern (high-to-low choices)

**Description**
Verify game handles uncommon but valid move pattern consistently.

**Priority**
P2 - Medium

**Preconditions**
- Program started with Player#1.

**Test steps (numbered and detailed)**
1. On each human turn, choose highest currently free cell.
2. Continue until game ends.
3. Track board updates and computer move order.

**Expected results**
- All chosen valid moves are accepted.
- Computer still follows first-free rule.
- End state (win/draw) is resolved correctly.

**Requirement reference**
REQ-05, REQ-06, REQ-07

**Automation potential (Yes/No/Partial)**
Yes

## ATC-11 Start With Computer and Continue Normally
**Title**
Valid gameplay path when computer starts

**Description**
Confirm unusual start mode (`2`) still supports all standard player interactions.

**Priority**
P1 - High

**Preconditions**
- Program started with argument `2`.

**Test steps (numbered and detailed)**
1. Verify first turn belongs to Player#2.
2. Wait for computer move and board render.
3. Enter valid human move.
4. Continue several turns and monitor consistency.

**Expected results**
- Computer makes first move following first-free strategy.
- Human can continue normal interaction afterward.
- Board values remain accurate across turns.

**Requirement reference**
REQ-01, REQ-03, REQ-06, REQ-08

**Automation potential (Yes/No/Partial)**
Yes

## ATC-12 Valid Input After Multiple Retries
**Title**
Eventually valid input after several invalid attempts

**Description**
Validate usability when user repeatedly corrects input before a valid move.

**Priority**
P1 - High

**Preconditions**
- Program at Player#1 turn.

**Test steps (numbered and detailed)**
1. Enter `a`.
2. Enter `0`.
3. Enter occupied cell value.
4. Enter valid free cell value.
5. Observe board and turn flow.

**Expected results**
- Each invalid attempt returns appropriate message.
- Turn stays Player#1 until valid move.
- Valid move is accepted and play continues.

**Requirement reference**
REQ-04, REQ-05

**Automation potential (Yes/No/Partial)**
Yes

## Error Recovery Scenarios

## ATC-13 Recovery After Non-Integer Input
**Title**
Recovery path from non-integer error

**Description**
Confirm program returns to a stable state immediately after invalid non-integer input.

**Priority**
P0 - Critical

**Preconditions**
- Program at Player#1 prompt.

**Test steps (numbered and detailed)**
1. Enter `hello`.
2. Verify error message and same-turn prompt.
3. Enter valid move `4`.
4. Verify board update and game continuation.

**Expected results**
- Error is handled without restart.
- Next valid move is accepted.
- No state corruption occurs.

**Requirement reference**
REQ-04, REQ-05

**Automation potential (Yes/No/Partial)**
Yes

## ATC-14 Recovery After Occupied Cell Error
**Title**
Recovery path from occupied-cell rejection

**Description**
Validate correct continuation after selecting an occupied cell.

**Priority**
P0 - Critical

**Preconditions**
- At least one occupied cell exists.
- Player#1 turn is active.

**Test steps (numbered and detailed)**
1. Enter value of an already occupied cell.
2. Confirm occupied-cell message appears.
3. Enter a different free valid cell.
4. Observe board update.

**Expected results**
- Occupied attempt is rejected.
- Free-cell retry is accepted in same turn cycle.
- Game continues normally with no duplicated moves.

**Requirement reference**
REQ-04, REQ-05

**Automation potential (Yes/No/Partial)**
Yes

## ATC-15 Recovery After Long Invalid Input Streak
**Title**
Stability after repeated invalid attempts

**Description**
Ensure repeated invalid entries do not degrade runtime behavior.

**Priority**
P1 - High

**Preconditions**
- Program waiting for Player#1 input.

**Test steps (numbered and detailed)**
1. Submit 20 consecutive invalid inputs (mixed text and out-of-range integers).
2. Confirm each invalid input yields expected error handling.
3. Submit one valid free-cell input.
4. Verify game resumes correctly.

**Expected results**
- Program remains responsive throughout invalid streak.
- No memory or performance degradation is observed.
- Valid input after streak is processed correctly.

**Requirement reference**
REQ-04, REQ-05

**Automation potential (Yes/No/Partial)**
Partial

## ATC-16 Recovery Behavior for Interrupted Input Stream
**Title**
Graceful handling of EOF/interrupted input stream

**Description**
Evaluate controlled failure mode when standard input is no longer available.

**Priority**
P2 - Medium

**Preconditions**
- Program running and waiting at human input prompt.

**Test steps (numbered and detailed)**
1. Send EOF (Ctrl+Z, then Enter on Windows).
2. Observe terminal output and process state.
3. If process exits, restart program and verify normal operation is possible.

**Expected results**
- Program exits cleanly or reports controlled error without stack trace.
- Application can be restarted and used normally.

**Requirement reference**
GAP-03

**Automation potential (Yes/No/Partial)**
No
