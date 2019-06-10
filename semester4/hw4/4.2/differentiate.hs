data Tree = Sum Tree Tree | Mult Tree Tree | Value Integer | Variable

instance Show Tree where
  show Variable = "x"
  show (Value n) = show n
  show (Sum a b) = "(" ++ (show a) ++ "+" ++ (show b) ++ ")"
  show (Mult a b) = "(" ++ (show a) ++ "*" ++ (show b) ++ ")"

differentiate :: Tree -> Tree
differentiate = simplify . diff where
  diff Variable = Value 1
  diff (Value n) = Value 0
  diff (Sum a b) = Sum (diff a) (diff b)
  diff (Mult a b) = Sum (Mult (diff a) b) (Mult a (diff b))

simplify :: Tree -> Tree
simplify Variable = Variable
simplify (Value n) = Value n
simplify (Sum a b) = helper (Sum (simplify a) (simplify b)) where
  helper (Sum (Value a) (Value b)) = Value (a + b)
  helper (Sum (Value 0) x) = simplify x
  helper (Sum x (Value 0)) = simplify x
  helper (Sum Variable Variable) = Mult (Value 2) Variable
  helper t = t
simplify (Mult a b) = helper (Mult (simplify a) (simplify b)) where
  helper (Mult (Value a) (Value b)) = Value (a * b)
  helper (Mult (Value 0) _) = Value 0
  helper (Mult _ (Value 0)) = Value 0
  helper (Mult (Value 1) x) = simplify x
  helper (Mult x (Value 1)) = simplify x
  helper (Mult a b) = Mult (simplify a) (simplify b)

parse_prod :: String -> Tree
parse_prod s = helper [] s where
  helper :: String -> String -> Tree
  helper "" "" = Value 1
  helper "x" "" = Variable
  helper s "" = Value (read s)
  helper x (y:ys)
    | y == '*'  = Mult (parse_prod x) (parse_prod ys)
    | otherwise = helper (x ++ [y]) ys

parse :: String -> Tree
parse s = helper "" s where
  helper :: String -> String -> Tree
  helper "" "" = Value 0
  helper s "" = parse_prod s
  helper x (y:ys)
    | y == '+'  = Sum (parse_prod x) (parse ys)
    | otherwise = helper (x ++ [y]) ys
