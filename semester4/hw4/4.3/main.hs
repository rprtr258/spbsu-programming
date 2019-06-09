import System.IO

data Record = Record String String

printHelp :: IO ()
printHelp = do
  putStrLn "Phonebook interactive. Commands:"
  putStrLn "* - Show phonebook"
  putStrLn "0 - Quit"
  putStrLn "1 - Add record"
  putStrLn "2 - Find phones by name"
  putStrLn "3 - Find names by phone"
  putStrLn "4 - Save phonebook"
  putStrLn "5 - Load phonebook"

addRecord ls = do
  putStrLn "Enter name:"
  name <- getLine
  putStrLn "Enter phone:"
  phone <- getLine
  return ((Record name phone) : ls)

printList [] message = do
  putStrLn message
printList (x:xs) _ = do
  putStrLn x
  printList xs ""

findPhones [] = do
  putStrLn "Phonebook is empty"
findPhones ls = do
  putStrLn "Enter name to search:"
  name <- getLine
  let records = filter (\(Record _name _) -> _name == name) ls
  let phones = map (\(Record _ phone) -> phone) records
  putStrLn "Phones of that person:"
  printList phones "Phones not found"

findNames [] = do
  putStrLn "Phonebook is empty"
findNames ls = do
  putStrLn "Enter phone to search:"
  phone <- getLine
  let records = filter (\(Record _ _phone) -> _phone == phone) ls
  let names = map (\(Record name _) -> name) records
  putStrLn "People with that phone:"
  printList names "Names not found"

toString [] = ""
toString ((Record name phone):xs) = name ++ " " ++ phone ++ "\n" ++ toString xs

savePhonebook ls = do
  writeFile "phonebook.db" $ toString ls

loadPhonebook = do
  file <- readFile "phonebook.db"
  let w = words file
  let ls = map (\(x, y) -> Record x y) [(w!!(2*k),w!!(2*k+1)) | k <- [0..length w `div` 2-1]]
  return ls

main = do
  printHelp
  mainLoop []

mainLoop ls = do
  putStr "> "
  hFlush stdout
  cmd <- getLine
  case (cmd) of
    "*" -> do
      printList (map (\(Record name phone) -> name ++ ": " ++ phone) ls) "Phonebook is empty"
      mainLoop ls
    "0" -> return ()
    "1" -> do
      newPhoneBook <- addRecord ls
      mainLoop newPhoneBook
    "2" -> do
      findPhones ls
      mainLoop ls
    "3" -> do
      findNames ls
      mainLoop ls
    "4" -> do
      savePhonebook ls
      putStrLn "Saved"
      mainLoop ls
    "5" -> do
      ls <- loadPhonebook
      putStrLn "Loaded"
      mainLoop ls
    otherwise -> do
        putStrLn "Wrong command"
        mainLoop ls
