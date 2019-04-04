check_brackets = check_brackets_helper [] where
  check_brackets_helper opened [] = opened == []
  check_brackets_helper opened (x:xs)
    | opened == []               = check_brackets_helper (x:opened) xs
    | correspond (head opened) x = check_brackets_helper (tail opened) xs
    | otherwise                  = check_brackets_helper (x:opened) xs
  correspond '{' '}' = True
  correspond '[' ']' = True
  correspond '(' ')' = True
  correspond _ _ = False
