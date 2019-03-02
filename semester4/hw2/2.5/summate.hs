module Summate where

threeSummate :: [Integer] -> [Integer] -> [Integer] -> [Integer]
threeSummate xs ys zs = summate [xs, ys, zs]

threeSummate' :: [Integer] -> [Integer] -> [Integer] -> [Integer]
threeSummate' xs ys zs = summate' [xs, ys, zs]

summate :: [[Integer]] -> [Integer]
summate lst = foldl (zipWith (+)) (addPadding []) $ map addPadding lst
    where
    maxLen = foldl max 0 $ map length lst
    addPadding = format maxLen
    format :: Int -> [Integer] -> [Integer]
    format n xs = xs ++ (take (n - (length xs)) $ repeat 0)

summate' :: [[Integer]] -> [Integer]
summate' lst = foldl add [] lst
    where
    add :: [Integer] -> [Integer] -> [Integer]
    add [] lst = lst
    add lst [] = lst
    add (x:xs) (y:ys) = x + y : add xs ys
