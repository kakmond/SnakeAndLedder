# Snake and Ladder game :game_die:

This project is the part of Software Specification and Desgin course (01219243) at Kasetsart University.
<p align="center">
  <img src="https://github.com/kakmond/SnakeAndLedder/blob/master/src/resources/newBoard.jpeg">
</p>

## Domain Model :page_with_curl:
![Domain](https://github.com/kakmond/SnakeAndLedder/blob/master/Domain%20Model.jpg)

## UML Class Diagram :page_facing_up:
![UML](https://github.com/kakmond/SnakeAndLedder/blob/master/UML%20Class%20Diagram.jpg)


## Rules :warning:

### Normal square 

- This is normal square, nothing special.

### Ladder square 

- If the players roll the die and stop at buttom of the ladder, they will move to top of the ladder. 

- But if they stop at top of the ladder, nothing happened.

### Snake square 

- If the players stop at the head of snake, they will move down to the square that snake's tail is located. 

- But if they stop at the tail of snake, nothing happened.

### Freeze square 

- If player stop at this square, player must wait for the train passing and player cannot move in the next turn.

### Backward square 

- If the player stop at this square, player will gets drunk and player will move backward in the next turn.

### Goal square

- If the players stop at this square, they win! 

- If player's position more than goal, player will move backward as the value that over the goal.

## How to play :video_game:
 
Download the SnakesAndLadders.jar file or click this [link](SnakesAndLadders.jar).

## Design Patterns :pencil2:

- Observer design pattern

- State and Strategy design pattern

- Iterator design pattern 

- Memento design pattern

## Members :mortar_board:

- Wongsathorn Panichkurkul 5910545817
- Sathira Kittisukmongkol 5910545868
- Varit Assavavisidchai 5910545833
