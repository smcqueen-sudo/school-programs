"""
This program is designed to solve problem number 64
https://projecteuler.net/problem=64
author: sim.j.mcqueen@gmail.com
"""
import math


def main():
	odds = 0
	for i in range(2, 10001):
		if math.floor(math.sqrt(i)) == math.sqrt(i):
			continue
		seq = []
		m = 0
		d = 1.0
		a = math.floor(math.sqrt(i))
		a0 = a
		seq.append(a)
		for x in range(2000):
			m = calc_m(d, a, m)
			d = calc_d(i, m, d)
			a = calc_a(a0, m, d)
			seq.append(a)
		len_pattern = calc_seq_len(seq)
		print(f'{i}: {len_pattern} : {seq}')
		if len_pattern % 2 == 1:
			odds += 1
	print(odds)


def calc_m(d, a, m):
	return d * a - m


def calc_d(S, m, d):
	return (S - m * m) / d


def calc_a(a0, m, d):
	return math.floor((a0 + m) / d)


def calc_seq_len(seq: list):
	new_seq = seq[1:len(seq)]
	seq_len = len(new_seq)
	for i in range(1, len(new_seq) // 2 + 1):
		pattern = new_seq[:i]
		repeated_pattern = pattern * ((seq_len // len(pattern)) + 1)
		repeated_pattern = repeated_pattern[:seq_len]
		if repeated_pattern == new_seq:
			return i
	return seq_len


if __name__ == "__main__":
	main()
