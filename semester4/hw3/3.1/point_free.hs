-- func x lst = map (\y -> y*x) lst
-- func x lst = map (x*) lst
-- func x = map (x*)
-- func x = map $ (*) x
-- func = map . (*)
func = map . (*)
