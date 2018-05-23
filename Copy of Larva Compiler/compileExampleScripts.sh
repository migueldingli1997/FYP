declare -a folders=("1_TRE" "2.1_TRE" "2.2_TRE" "3_TRE"
	                "1_TA"  "2.1_TA"  "2.2_TA"  "3_TA")
declare -a counts=(14 5 5 7
	               14 5 5 7)

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