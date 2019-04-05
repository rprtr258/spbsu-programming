import Data.List
--t1.1
lst :: [Integer]
lst = 1:(-1):lst

lst' :: [Integer]
lst' = zipWith (*) [1..] lst

--t1.2
angle_matrix :: (Num a, Enum a, Ord a) => a -> [[a]]
angle_matrix n = [row k | k <- [1..n]] where
  row k = [max i k | i <- [1..n]]

--t1.3
draw_diamond :: Int -> [[Char]]
draw_diamond n = [row k | k <- [1..n]] ++ [row (n - k) | k <- [1..n-1]] where
  row :: Int -> [Char]
  row k = (take (n - k) $ repeat ' ') ++ (take (2 * k - 1) $ repeat 'x')

print_diamond :: Int -> IO()
print_diamond n = putStrLn $ intercalate "\n" $ draw_diamond n

main :: IO ()
main = do
  putStrLn "Enter side size: "
  input <- getLine
  let size = (read input :: Int)
  print_diamond size

--t1.4
supermap :: [a] -> (a -> [b]) -> [b]
supermap lst f = concat $ map f $ lst

--t1.5
check_all :: (a -> Bool) -> [a] -> Bool
check_all p lst = and (map p lst)
