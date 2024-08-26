/**
 * @file Bishop.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef BISHOP_H
#define BISHOP_H

#include <iostream>
#include "Piece.h"

/**
 * Bishop class for chess
 */
class Bishop : public Piece {
  public:
    /**
     * Constructor for Bishop
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the Bishop
     */
    Bishop(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for bishop it is 3
     *
     * @return Returns 3 (value of a bishop)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the bishop can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Converts the bishop to a string, "B"
     *
     * @return String "B" or "b" (depending on color)
     */
    std::string str() const override;
};

#endif
