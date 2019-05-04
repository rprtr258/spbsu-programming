lst :: Num a => [a]
lst = 1:7:9:concat (map next lst) where
  next x = [x * 10 + k | k <- [1, 7, 9]]
