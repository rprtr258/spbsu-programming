module Reverse where

rev :: [a] -> [a]
rev s = foldl (\a -> \b -> [b] ++ a) [] s

rev' :: [a] -> [a]
rev' s = [s!!(m - n) | n <- [0..m]]
  where
  m = length s - 1

rev'' :: [a] -> [a]
rev'' [] = []
rev'' [x] = [x]
rev'' (x:xs) = rev'' xs ++ [x]

rev''' :: [a] -> [a]
rev''' = reverse
