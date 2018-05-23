read -p "Enter script file: " F
read -p "Enter destination directory: " O

java -cp out/ compiler.Compiler $F -o $O -v