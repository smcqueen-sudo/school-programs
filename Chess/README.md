# Chess Game in C++ (OOP)

## Overview
This project is a simple implementation of the game of Chess, developed using **C++** with an emphasis on **Object-Oriented Programming (OOP)** principles. It includes basic functionalities of a chess game, such as piece movement, capturing, and score calculation. The program is designed with extensibility in mind, allowing for further features to be added, such as checkmate and stalemate detection or graphical interfaces.

## Features
- Basic chess game with a text-based interface
- Piece movement rules implemented according to chess rules
- Turn-based system for two players
- Move validation to prevent illegal moves
- Object-Oriented structure to allow easy extension

## Technologies Used
- **C++**
- **OOP Principles** (Encapsulation, Inheritance, Polymorphism)
  
## How It Works
The game is built using a set of classes that represent different elements of a chess game:
- **Piece**: An abstract base class for all chess pieces (e.g., Pawn, Rook, Knight).
- **Board**: Manages the game board and handles move validation and piece placement.
- **Game**: Controls the flow of the game, manages player turns, and enforces rules.

Each chess piece (Pawn, Rook, Knight, Bishop, Queen, King) inherits from the base class `Piece`, and each class has its own specific movement behavior.

### OOP Concepts
- **Encapsulation**: The state and behavior of each chess piece is encapsulated in its respective class.
- **Inheritance**: All chess pieces inherit common functionality from the `Piece` base class.
- **Polymorphism**: The game dynamically handles different types of pieces using polymorphism.


## Future Enhancements
- Add check, checkmate, and stalemate detection
- Implement graphical user interface (GUI)
- Add a feature for saving and loading games
- Enhance AI for single-player mode
