/**
 * @file Queen.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef QUEEN_H
#define QUEEN_H

#include <iostream>
#include "Piece.h"

/**
 * Queen class for chess
 */
class Queen : public Piece {
  public:
    /**
     * Constructor for queen
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the queen
     */
    Queen(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for queen it is 9
     *
     * @return Returns 9 (value of a queen)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the queen can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Converts the queen to a string, "Q"
     *
     * @return String "Q" or "q" (depending on color)
     */
    std::string str() const override;
};

#endif
