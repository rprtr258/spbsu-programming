module Twos where

twos :: Integer -> [Integer]
twos n = [2 ^ n | n <- [1..n]]
