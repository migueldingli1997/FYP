declare -a folders=("RE_Basic" "RE_Operators_GoodBeh" "RE_Operators_BadBeh" "RE_Mix" "RE_Params_1" "RE_Params_2" "RE_NonDet"
	              "TA_Basic" "TA_Operators_GoodBeh" "TA_Operators_BadBeh" "TA_Mix" "TA_Params_1" "TA_Params_2" "TA_Params_3" "TA_NonDet_1" "TA_NonDet_2")
declare -a counts=(7 5 5 4 3 2 4 
	               7 5 5 4 3 1 1 2 2)

rm -r generatedLarvaFiles
mkdir generatedLarvaFiles

arrLenMinusOne=$(expr ${#folders[@]} - 1)
for i in `seq 0 $arrLenMinusOne`; do
	if [ -d "generatedScripts/Scripts_${folders[$i]}/" ]; then
		cp -r generatedScripts/Scripts_${folders[$i]}/* .
		for j in `seq 1 ${counts[$i]}`; do
			java -cp out/ compiler.Compiler script_$j.lrv -o "larvaRuntimeOutput" -g "%GRAPHVIZ_DOT%"
		done
		mv larvaRuntimeOutput generatedLarvaFiles/Larva_${folders[$i]}
		rm *.lrv
	else
		echo "generatedScripts/Scripts_${folders[$i]}/ folder not found."
	fi
done