moduleDir="../.."
regExpLib="$moduleDir/../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar"
generator="$moduleDir/target/Larva_Generators-1.0-jar-with-dependencies.jar"

declare -a folders=("RE_Basic" "RE_Operators_GoodBeh" "RE_Operators_BadBeh" "RE_Mix" "RE_Params_1" "RE_Params_2" "RE_NonDet"
	              "TA_Basic" "TA_Operators_GoodBeh" "TA_Operators_BadBeh" "TA_Mix" "TA_Params_1" "TA_Params_2" "TA_Params_3" "TA_NonDet_1" "TA_NonDet_2")
declare -a scripts=("fits_basic" "fits_operators_g" "fits_operators_b" "fits_mix" "fits_params_RE_1" "fits_params_RE_2" "fits_nondet_RE"
                  "fits_basic" "fits_operators_g" "fits_operators_b" "fits_mix" "fits_params_TA_1" "fits_params_TA_2" "fits_params_TA_3" "fits_nondet_TA_1" "fits_nondet_TA_2")
declare -a approaches=(1 1 1 1 1 1 1
                       2 2 2 2 2 2 2 2 2)

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
