find_max_index lst = fst $ foldl (\(x, y) -> \(a, b) -> if y > b then (x, y) else (a, b)) (-1, -1) $ zip [1..] $ zipWith (+) (init lst) $ tail lst
