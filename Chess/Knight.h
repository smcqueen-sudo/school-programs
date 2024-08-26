/**
 * @file Knight.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef KNIGHT_H
#define KNIGHT_H

#include <iostream>
#include "Piece.h"

/**
 * Knight class for chess
 */
class Knight : public Piece {
  public:
    /**
     * Constructor for knight
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the knight
     */
    Knight(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for knight it is 3
     *
     * @return Returns 3 (value of a knight)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the knight can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Converts the bishop to a string, "N"
     *
     * @return String "N" or "n" (depending on color)
     */
    std::string str() const override;
};

#endif
