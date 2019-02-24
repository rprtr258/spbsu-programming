module Twos where

twos :: Integer -> [Integer]
twos n = [2 ^ n | n <- [1..n]]

twos' :: Integer -> [Integer]
twos' n = map (2^) [1..n]
