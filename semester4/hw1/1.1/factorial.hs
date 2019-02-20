module Factorial where

factorial n = foldr (*) 1 [1..n]
