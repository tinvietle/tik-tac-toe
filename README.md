# Tic Tac Toe with AI-Generated Code

This repository documents an experiment in building Tic Tac Toe with structured prompts and LLM-assisted coding.

The project is part of a school subject focused on understanding both the strengths and limitations of AI-driven development.

## Tech Stack

- Models: GPT-5.3-Codex, Claude Sonnet 4.6
- Editor workflow: VS Code chat
- Prompting method: Anthropic Prompt Engineer template

## Project Timeline

### Week 1 - Java CLI Version

Built a command-line Tic Tac Toe game in Java.

Process:
- Prepare prompt
- Send prompt to model
- Wait for completion
- Fully vibe code in about 5 minutes

Prompt:
- [week1/AGENTS.md](week1/AGENTS.md)

Run:

```bash
cd week1
java -cp target/tik-tac-toe-1.0.0.jar com.progex.tictactoe.Main 1
```

### Week 2 - Java Swing Version (Fresh Build)

Built a Java Swing UI version without reusing the Week 1 implementation.

Process:
- Prepare prompt
- Send prompt to model
- Wait for completion
- Fully vibe code in about 5 minutes

Prompt:
- [week2/.github/agents/tictactoe-java-swing.agent.md](week2/.github/agents/tictactoe-java-swing.agent.md)

Run:

```bash
cd week2
java -cp target/tik-tac-toe-1.0.0.jar com.progex.tictactoe.Main 1
```

### Week 2.2 - Java Swing Version (Reuse Week 1)

Built another Swing UI version by reusing and adapting the Week 1 codebase.

Process:
- Prepare prompt
- Send prompt to model
- Wait for completion
- Fix compile errors and tests
- Fully vibe code in about 30 minutes (with help from Claude Sonnet 4.6 on the website)

Claude Sonnet support chat:
- https://claude.ai/share/5f009bf7-7ae4-4106-a5a0-538eae000948

Prompt:
- [week2.2/AGENTS.md](week2.2/AGENTS.md)

Run:

```bash
cd week2.2
java -cp target/tik-tac-toe-1.0.0.jar com.progex.tictactoe.Main 1
```
