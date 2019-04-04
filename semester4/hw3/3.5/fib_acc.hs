fib = fib_helper (0, 1) where
  fib_helper (a, b) n
    | n == 0 = a
    | n > 0  = fib_helper (b, a + b) (n - 1)
    | n < 0  = fib_helper (b - a, a) (n + 1)
