/**
 * @file Player.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef PLAYER_H
#define PLAYER_H

#include <vector>
#include "Board.h"
#include "Piece.h"
#include "King.h"

/**
 * Player class for the chess game
 */
class Player {
  public:
    /**
     * Constructor for the player
     *
     * @param color Color of the player's pieces
     * @param board Board that the player will interact with
     */
    Player(Piece::Color color, const Board& board);

    /**
     * Getter for what color the player's pieces are
     *
     * @return Color for the player's pieces
     */
    Piece::Color color() const;

    /**
     * Attempts to make a move to a square
     *
     * @param from The square to start from
     * @param to The square to end at
     * @return True if move succeeded, false if it did not
     */
    bool make_move(const std::string& from, const std::string& to);

    /**
     * Getter for the value of all the current pieces that are on the board
     *
     * @return Total value of pieces
     */
    piece_value_t piece_value() const;

    /**
     * Destructor for player
     */
    ~Player();

  private:
    const Piece::Color _color;
    const Board& _board;
    std::vector<Piece*> _pieces;
    King* _king;
};

#endif

