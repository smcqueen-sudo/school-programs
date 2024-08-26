/**
 * @file Square.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef SQUARE_H
#define SQUARE_H

#include <iostream>
#include "Piece.h"

/**
 * Class for a square on a chess board
 */
class Square {
  public:
    /**
     * Constructor for Square
     *
     * @param rank
     * @param file
     */
    Square(size_t rank, size_t file) : _rank(rank), _file(file) { }

    /**
     * Getter for rank
     *
     * @return Rank of square
     */
    size_t rank() const;

    /**
     * Getter for file
     *
     * @return File of square
     */
    size_t file() const;

    /**
     * Checks if there is a piece of the square
     *
     * @return True if there is a piece in the square
     */
    bool is_occupied() const;

    /**
     * Getter for the current occupant of the square
     *
     * @return Piece that is on the square
     */
    Piece* occupant() const;

  private:
    void set_occupant(Piece* occupant);
    const size_t _rank;
    const size_t _file;
    Piece* _occupant = nullptr;
    friend void Piece::set_location(Square* location);
    friend void Piece::capture();
};

/**
 * << Operator override for square
 *
 * @param os OS to output too
 * @param square Square to output
 * @return Reference to the os
 */
std::ostream& operator<<(std::ostream& os, const Square& square);

#endif
