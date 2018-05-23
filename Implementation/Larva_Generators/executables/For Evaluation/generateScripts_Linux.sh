moduleDir="../.."
regExpLib="$moduleDir/../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar"
generator="$moduleDir/target/Larva_Generators-1.0-jar-with-dependencies.jar"

declare -a folders=(Scripts_RE Scripts_TA)
declare -a approaches=(1 2)

rm -r generatedScripts
mkdir generatedScripts
mkdir dummyFolder
mkdir dummyFolder/out

arrLenMinusOne=$(expr ${#folders[@]} - 1)
for i in `seq 0 $arrLenMinusOne`; do
	rm -r generatedScripts/${folders[$i]}/
	cp -r dummyFolder inputOutput
	java -cp "$generator:$regExpLib" larvaGenerator.GeneratorRunner inputTREs/ftp_test_cases.tre -app ${approaches[$i]}
	mv inputOutput/out/* inputOutput/
	rm -r inputOutput/out/
	mv inputOutput generatedScripts/${folders[$i]}
done

rm -r dummyFolder
