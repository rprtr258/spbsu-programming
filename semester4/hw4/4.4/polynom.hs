import Data.List

-- coeff, degree
data Monom = Monom (Integer, Integer)
data Polynom = Polynom [Monom]

instance Show Monom where
  show (Monom (coeff, 0)) = (show coeff)
  show (Monom (1, 1)) = "x"
  show (Monom (coeff, 1)) = (show coeff) ++ "x"
  show (Monom (1, exp)) = "x^" ++ (show exp)
  show (Monom (coeff, exp)) = (show coeff) ++ "x^" ++ (show exp)

instance Show Polynom where
  show (Polynom []) = "0"
  show (Polynom [x]) = show x
  show (Polynom (x:xs)) = (show x) ++ " + " ++ (show (Polynom xs))

add :: Polynom -> Polynom -> Polynom
add (Polynom a) (Polynom b) = simplify (Polynom (a ++ b))

multm :: Monom -> Monom -> Monom
multm (Monom (coeff1, exp1)) (Monom (coeff2, exp2)) = (Monom (coeff1 * coeff2, exp1 + exp2))

mult :: Polynom -> Polynom -> Polynom
mult (Polynom a) (Polynom b) = simplify (Polynom c) where
  c = do
    x <- a
    y <- b
    return $ multm x y

simplify :: Polynom -> Polynom
simplify (Polynom xs) = (Polynom (helper xs)) where
  helper [] = []
  helper [x] = [x]
  helper xs = map sumP grouped
  sumP ((Monom (a, b)):ys) = (Monom (a + sum(map (\(Monom (x, y)) -> x) ys),b))
  grouped = groupBy (\(Monom (a, b)) -> \(Monom (c, d)) -> (b == d)) sorted
  sorted = sortOn (\(Monom (a, b)) -> b) xs
