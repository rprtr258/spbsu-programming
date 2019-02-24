module Find where

find :: Eq a => a -> [a] -> Integer
find x [] = -1
find x (y:ys)
    | x == y    = 0
    | otherwise = if idx /= -1 then 1 + idx else -1
    where
    idx = find x ys

find' :: Eq a => a -> [a] -> Integer
find' x lst = if count > 0 then snd $ head $ occurences else -1
    where
    occurences = filter (\y -> fst y == x) $ zip lst [0..]
    count = length occurences
