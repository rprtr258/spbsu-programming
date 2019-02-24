module Find where

find :: Eq a => a -> [a] -> Integer
find x [] = -1
find x (y:ys)
    | x == y    = 0
    | idx == -1 = -1
    | otherwise = 1 + idx
    where
    idx = find x ys

find' :: Eq a => a -> [a] -> Integer
find' x lst =
    | count > 0 = snd $ head $ occurences
    | otherwise = -1
    where
    occurences = filter (\y -> fst y == x) $ zip lst [0..]
    count = length occurences
