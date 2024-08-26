/**
 * @file King.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef KING_H
#define KING_H

#include <iostream>
#include "Piece.h"

/**
 * King class for chess
 */
class King : public Piece {
  public:
    /**
     * Constructor for king
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the king
     */
    explicit King(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for king it is 200
     *
     * @return Returns 200 (value of a king)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the king can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Converts the king to a string, "K"
     *
     * @return String "K" or "k" (depending on color)
     */
    std::string str() const override;
};

#endif
