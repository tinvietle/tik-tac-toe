# Write a Java program to play Tic-Tac-Toe against the computer. Ensure that you follow the principles of  the Object-Oriented Programming (OOP) paradigm.

## The game master (a human) decides which player will start by passing either 1 (he/she starts) or 2 (the computer starts) as an argument when executing the Java program. The program should base its behavior on the argument passed by the game master to decide the first player and second player.

- If the game master does not pass an argument when executing the Java program, the terminal will display the message “Please, input a valid option [1-2]”.
- If the game master does not pass either 1 or 2 as an argument when executing the Java program, the terminal will display the message “Please, input a valid option [1-2]”.

## When required, the board will be displayed as a table, consisting of 9 cells. Each cell is uniquely identified by a number from 1 to 9. The cells are numbered from top to bottom, and from left to right, as follows:

| 1 | 2 | 3 |
| 4 | 5 | 6 |
| 7 | 8 | 9 |

## At the beginning of the game, the welcoming message “Hello!” will be displayed in the terminal.

After the welcoming message (in the next line), the initial board will be displayed in the terminal. In the initial board, all the cells in the table will display 0.

After the initial board is displayed in the terminal, the message “Player#<player-number>'s turn” will be displayed in the terminal, where <player-number> is the number (either 1 or 2) of the player who will start when playing the game.

## The human-player inputs its moves by typing the number of the chosen cell [1-9] followed by ENTER (end-of-line).

- If the string typed by the human-player does not correspond to an integer, then the message “Please, input a valid number [1-9]” will be displayed in the terminal, followed by  (in the next line) the message “Player#1’s turn”. The game will not continue until the human types a move again.
- If the string typed by the human-player does not correspond to an integer in the range [1-9], then the message “Please, input a valid number [1-9]” will be displayed in the terminal, followed by  (in the next line) the message “Player#1’s turn”. The game will not continue until the human types a move again.
- If the string typed by the human-player corresponds to an integer in the range [1-9], but the corresponding cell is already occupied, then the message “The cell is occupied!” will be displayed in the terminal, followed by (in the next line) the message “Player#1’s turn”. The game will not continue until the human types a move again.
- If the string typed by the human-player corresponds to an integer in the range [1-9], and the corresponding cell is not occupied, then the board will be updated with the choice of the human-player. After updating the board, one of the following three cases can occur:

  - The human-player has won. In this case, the current board will be displayed in the terminal, followed by (in the next line) the message “Player#1 won!” and the game will end (the Java program will stop).
  - There are no more free cells on the board. In this case, the current board will be displayed in the terminal, followed by (in the next line) the message “It is a draw!” and the game will end (the Java program will stop).
  - The human-player has not won yet, and there are still free cells on the board. In this case, the current board will be displayed in the terminal, and the game will continue with the computer-player’s turn.

## The computer-player follows a basic strategy: it will choose the first available cell  from 1 to 9. After the computer-player has chosen a cell, the board will be updated. At this point,  one of the following three cases can occur:

- The computer-player has won. In this case, the current board will be displayed in the terminal, followed by (in the next line) the message “Player#2 won!” and the game will end (the Java program will stop).
- There are no more free cells on the board. In this case, the current board will be displayed in the terminal followed by (in the next line) the message “It is a draw!” and the game will end (the Java program will stop).
- The computer-player has not won yet, and there are still free cells on the board. In this case, the current board will be displayed in the terminal, and the game will continue with the human-player’s turn.

## Current board that displayed in the terminal after each move should reflect the current state of the game, with the cells occupied by the human-player and computer-player updated accordingly. Display 1 for Player#1’s moves and 2 for Player#2’s moves that occupy the cells. The cells that are not occupied should display 0.