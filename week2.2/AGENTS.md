# AGENTS.md

This repository provides guidance for agents when working on the repository.

## Project Overview

Tik tac toe, a turn based game. Have 2 player, use CLI command to input the next move

## Algortihm

Setup map to fill with 0
Passing parameter when compile the program: java -cp ... <parameter>, so that user can choose if they want to go first or second, with 1 being the user first and 2 is the computer first 
First player: Simple, just enter from 1->9, fill user move with 1
Second player (Computer): fill from 1 to 9 if the place is free, else move to the next place, fill computer move with 2

## Map

|1|2|3|
|4|5|6|
|7|8|9|

## Examples:
User input: 1 -> fill the row 1 column 1 with 1
|1|0|0|
|0|0|0|
|0|0|0|
Computer see position 1 is already filled, move to number 2, fill row 2 column 1 with 2
|1|2|0|
|0|0|0|
|0|0|0|
--continue onwards--

# Requirements

Use java, OOP structure, maven
OOP structure: Game, Player, Board
mvn clean package and java <path> --cp <class> 1/2