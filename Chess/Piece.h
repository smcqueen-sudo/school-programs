/**
 * @file Piece.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef PIECE_H
#define PIECE_H

#include <iostream>

class Square;

typedef unsigned int piece_value_t;

/**
 * Piece class for chess
 */
class Piece {
  public:
    enum struct Color {
        black, white
    };

    /**
     * Constructor for piece
     *
     * @param color Color of piece (black/white)
     * @param location Square of the piece
     */
    Piece(const Piece::Color color, Square& location) : _color(color) {
        this->set_location(&location);
    }

    /**
     * Getter for the value of the piece
     *
     * @return Value of current piece
     */
    virtual piece_value_t value() const = 0;

    /**
     * Getter for the color of the piece
     *
     * @return Color of the piece
     */
    Piece::Color color() const;

    /**
     * Current square that the piece is on
     *
     * @return Square that the piece is on
     */
    Square* location() const;

    /**
     * Sets the location of the piece
     *
     * @param location New location for piece
     */
    void set_location(Square* location);

    /**
     * Checks if the piece actually on a square
     *
     * @return True if the piece is in play
     */
    bool is_on_square() const;

    /**
     * Checks if moving to a certain square is a valid move
     *
     * @param location Potential new location
     * @return True if you can move there
     */
    virtual bool can_move_to(const Square& location) const = 0;

    /**
     * Move the piece to the next square
     *
     * @param location New location for square
     * @return True if the move succeeded
     */
    virtual bool move_to(Square& location);

    /**
     * Capture the piece that is on the current square
     */
    void capture();

    /**
     * To string method of piece
     *
     * @return String of piece
     */
    virtual std::string str() const = 0;

    /**
     * Default deconstructor for piece
     */
    virtual ~Piece() = default;

  private:
    const Piece::Color _color;
    Square* _location = nullptr;
};

/**
 * << Operator override for piece
 *
 * @param os OS to output too
 * @param square Piece to output
 * @return Reference to the os
 */
std::ostream& operator<<(std::ostream& os, const Piece& piece);

#endif
