data Tree = Sum Tree Tree | Mult Tree Tree | Value Integer | Variable

instance Show Tree where
  show Variable = "x"
  show (Value n) = show n
  show (Sum a b) = "(" ++ (show a) ++ "+" ++ (show b) ++ ")"
  show (Mult a b) = "(" ++ (show a) ++ "*" ++ (show b) ++ ")"

add :: Tree -> Tree -> Tree
add a b = Sum a b

mult :: Tree -> Tree -> Tree
mult a b = Mult a b
