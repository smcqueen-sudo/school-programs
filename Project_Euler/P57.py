"""
This program is designed to solve problem number 57
https://projecteuler.net/problem=57
author: sim.j.mcqueen@gmail.com
"""
from fractions import Fraction
import sys


def main():
	# Need to change the recursion limit because my function will go to just above 1000 recursion calls
	sys.setrecursionlimit(1020)
	# initialize total
	total = 0
	for i in range(1000):
		# Get the fraction at the specific expansion
		result = calc_expansion(i)
		if len(str(result.numerator)) > len(str(result.denominator)):
			total += 1
	print(total)


def calc_expansion(n: int) -> Fraction:
	"""
	returns 1 + 1/get_denom(n)
	"""
	denom = get_denom(n)
	return Fraction(denom.denominator + denom.numerator, denom.numerator)
	

def get_denom(n: int) -> Fraction:
	"""
	returns 2 + 1/get_denom(n - 1)
	base case: n = 0, returns 2
	"""
	denom = Fraction(2, 1)
	if n != 0:
		old_denom = get_denom(n - 1)
		denom = Fraction(old_denom.denominator + 2 * old_denom.numerator, old_denom.numerator)
	return denom


if __name__ == "__main__":
	main()
