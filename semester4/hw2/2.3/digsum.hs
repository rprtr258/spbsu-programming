module DigSum where

import Data.Char

digsum :: Integer -> Integer
digsum n = foldl (+) 0 $ map (\x -> toInteger $ ord x - ord '0') $ show n
