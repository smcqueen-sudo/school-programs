/**
 * @file Pawn.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef PAWN_H
#define PAWN_H

#include <iostream>
#include "Piece.h"

/**
 * Pawn class for chess
 */
class Pawn : public Piece {
  public:
    /**
     * Constructor for pawn
     *
     * @param color Color of the piece black/white
     * @param location Starting location for the king
     */
    Pawn(Piece::Color color, Square& location) : Piece(color, location) { }

    /**
     * Returns the value of the piece, for pawn it is 200
     *
     * @return Returns 200 (value of a pawn)
     */
    piece_value_t value() const override;

    /**
     * Tests to see if on an empty board the pawn can move to a specific square
     *
     * @param location starting location
     * @return True if the move is valid
     */
    bool can_move_to(const Square& location) const override;

    /**
     * Moves to a certain location
     *
     * @param location Location to move to
     * @return True if the move worked
     */
    bool move_to(Square& location) override;

    /**
     * Converts the king to a string, "P"
     *
     * @return String "P" or "p" (depending on color)
     */
    std::string str() const override;

  private:
    bool _moved = false;
};

#endif
