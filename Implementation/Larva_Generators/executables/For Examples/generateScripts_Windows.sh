moduleDir="../.."
regExpLib="$moduleDir/../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar"
generator="$moduleDir/target/Larva_Generators-1.0-jar-with-dependencies.jar"

declare -a folders=("1_TRE" "2.1_TRE" "2.2_TRE" "3_TRE"
	                "1_TA"  "2.1_TA"  "2.2_TA"  "3_TA")
declare -a scripts=("example_script_1" "example_script_2" "example_script_2_extended" "example_script_3"
	                "example_script_1" "example_script_2" "example_script_2_extended" "example_script_3")
declare -a approaches=(1 1 1 1
	                   2 2 2 2)

rm -r generatedScripts
mkdir generatedScripts
mkdir dummyFolder
mkdir dummyFolder/out

arrLenMinusOne=$(expr ${#folders[@]} - 1)
for i in `seq 0 $arrLenMinusOne`; do
	rm -r generatedScripts/Scripts_${folders[$i]}/
	cp -r dummyFolder inputOutput
	java -cp "$generator;$regExpLib" larvaGenerator.GeneratorRunner inputTREs/${scripts[$i]}.tre -app ${approaches[$i]} -test
	mv inputOutput/out/* inputOutput/
	rm -r inputOutput/out/
	mv inputOutput generatedScripts/Scripts_${folders[$i]}
done

rm -r dummyFolder