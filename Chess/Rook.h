/**
 * @file Rook.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef ROOK_H
#define ROOK_H

#include <iostream>
#include "Piece.h"

/**
 * Rook class for chess
 */
class Rook : public Piece {
  public:
    /**
     * Constructor for rook
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the rook
     */
    Rook(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for rook it is 5
     *
     * @return Returns 5 (value of a rook)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the rook can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Converts the rook to a string, "R"
     *
     * @return String "R" or "r" (depending on color)
     */
    std::string str() const override;
};

#endif
