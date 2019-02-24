module Fibonacci where

fib :: Integer -> Integer
fib n
    | n < 2 = n
    | otherwise = fib (n - 1) + fib (n - 2)

fibs :: [Integer]
fibs = 0 : 1 : zipWith (+) f (tail f)

fib' :: Int -> Integer
fib' n = fibs!!n
