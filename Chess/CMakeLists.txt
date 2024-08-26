cmake_minimum_required(VERSION 3.27)
project(chess)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(chess
        Bishop.cpp
        Bishop.h
        Board.cpp
        Board.h
        Chess.cpp
        Chess.h
        King.cpp
        King.h
        Knight.cpp
        Knight.h
        Pawn.cpp
        Pawn.h
        Piece.cpp
        Piece.h
        Player.cpp
        Player.h
        Queen.cpp
        Queen.h
        Rook.cpp
        Rook.h
        Square.cpp
        Square.h
)
