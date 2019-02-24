module Factorial where

factorial :: Integer -> Integer
factorial n = foldr (*) 1 [1..n]
