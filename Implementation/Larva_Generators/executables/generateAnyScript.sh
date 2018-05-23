moduleDir=".."
regExpLib="$moduleDir/../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar"
generator="$moduleDir/target/Larva_Generators-1.0-jar-with-dependencies.jar"

echo "Please enter the input file or expressions:"
read input
echo "Please enter the approach (1=>TRE or 2=>TA):"
read approach
echo "Please enter the output folder (including a '/' at the end)"
read outFolder
echo "Please enter any additional options (or ENTER to skip):"
echo "	Note: -dot enables .dot file output [only if using approach 2]."
echo "	Note: -png enables .dot file output [only if using approach 2]."
read extraOptions

mkdir $outFolder

java -cp "$generator;$regExpLib" larvaGenerator.GeneratorRunner "$input" -app $approach -out $outFolder $extraOptions