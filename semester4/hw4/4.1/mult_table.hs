mult_table n = do
  x <- [1..n]
  y <- [1..n]
  return (x * y)

-- same without do notation
mult_table' n = [1..n] >>= (\x -> (map (\y -> x * y)  [1..n]))
