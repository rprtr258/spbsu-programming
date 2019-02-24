module Fibonacci where

fib :: Integer -> Integer
fib n
    | n < 2 = n
    | otherwise = fib (n - 1) + fib (n - 2)
