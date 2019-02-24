module Pal where

pal :: [Char] -> Bool
pal s = (s == reverse s)
